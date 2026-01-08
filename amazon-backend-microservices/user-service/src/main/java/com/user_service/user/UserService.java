package com.user_service.user;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.shared_contract.original.CustomException;
import com.shared_contract.original.CustomMultipartFile;
import com.shared_contract.original.user_service.AuthType;
import com.user_service.client.FileClient;
import com.user_service.id.IdService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import com.user_service.common.Constants;

import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private FileClient fileClient;

	@Autowired
	private IdService idService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RestTemplate restTemplate;

	public Response findAllUsers() {
		List<User> users = repository.findAll();
		for (User user : users) {
			List<String> fileIds = user.getFiles(); // assuming User has List<String> files
			if (fileIds != null && !fileIds.isEmpty()) {
				Map<String, String> base64FilesData = fileClient.getBase64Files(fileIds);
				user.setBase64Files(base64FilesData);
			}
		}
		Response response = new Response();
		response.setUsers(users);
		response.setTotal(users.size());
		return response;
	}

	public User findUserById(String id) {
		User user = repository.findById(id).orElse(null);
		if (user != null && user.getFiles() != null && !user.getFiles().isEmpty()) {
			Map<String, String> base64FilesData = fileClient.getBase64Files(user.getFiles());
			user.setBase64Files(base64FilesData);
		}
		return user;
	}

	public User addUser(User request, HttpServletResponse response) {
		User user = isUserExists(request);
		if(user != null) {
			response.setStatus(400);
			return user;
		}
		request.setUserId(idService.getUserID());
		updateFullName(request);
		String hashedPassword = hashPassword(request.getPassword());
		request.setPassword(hashedPassword);
		User entity = repository.save(request);		// create
		String accessToken = generateAccessToken(entity);
		request.setAccessToken(accessToken);
		repository.save(entity);	// update
		addAccessTokenToCookie(accessToken, response);
		return entity;
	}

	public User updateUser(String id, User request, HttpServletResponse response) throws CustomException {
		User entity = repository.findById(id).orElse(null);
		if (entity == null) {
			return null;
		}

		// check username, password uniqueness
		User user = isUserExists(request);
		if(user != null && user.getId() != id) {
			response.setStatus(400);
			return user;
		}

		if (request.getName() != null && !request.getName().isEmpty()) {
			entity.setName(request.getName());
		}

		if (request.getFirstName() != null && !request.getFirstName().isEmpty()) {
			entity.setFirstName(request.getFirstName());
		}

		if (request.getLastName() != null && !request.getLastName().isEmpty()) {
			entity.setLastName(request.getLastName());
		}

		if (request.getEmail() != null && !request.getEmail().isEmpty()) {
			/* just to test custom exception
			if(!request.getEmail().contains(".")) {
				throw new CustomException(400, "Email must contain dot(.)");
			}
			*/
			entity.setEmail(request.getEmail());
		}
		
		if (request.getPhoneNumber() != null && !request.getPhoneNumber().isEmpty()) {
			entity.setPhoneNumber(request.getPhoneNumber());
		}
		
		if (request.getGender() != null) {
			entity.setGender(request.getGender());
		}
		
		if (request.getLanguage() != null) {
			entity.setLanguage(request.getLanguage());
		}
		
		if (request.getRole() != null) {
			entity.setRole(request.getRole());
		}
		
		if (request.getFiles() != null && !request.getFiles().isEmpty()) {
			entity.setFiles(request.getFiles());
		}

		if (request.getUserName() != null && !request.getUserName().isEmpty()) {
			entity.setUserName(request.getUserName());
		}

		if (request.getPassword() != null && !request.getPassword().isEmpty()
				&& !request.getPassword().matches("\\*+")) {
			String hashedPassword = hashPassword(request.getPassword());
			entity.setPassword(hashedPassword);
		}

		if (request.getAccessToken() != null && !request.getAccessToken().isEmpty()) {
			entity.setAccessToken(request.getAccessToken());
		}
		
		if (request.getDob() != null) {
			entity.setDob(request.getDob());
		}
		
		if (request.getAddress() != null) {
			if(request.getAddress().getStreet() != null && !request.getAddress().getStreet().isEmpty()) {
				entity.getAddress().setStreet(request.getAddress().getStreet());
			}
			if(request.getAddress().getCity() != null && !request.getAddress().getCity().isEmpty()) {
				entity.getAddress().setCity(request.getAddress().getCity());
			}
			if(request.getAddress().getPincode() != null && !request.getAddress().getPincode().isEmpty()) {
				entity.getAddress().setPincode(request.getAddress().getPincode());
			}
		}
		entity = repository.save(entity);
		entity.setPassword("**********");
		return entity;
	}

	public Response removeUser(String id) {
		repository.deleteById(id);
		Response response = new Response();
		response.setMessage("user deleted");
		return response;
	}

	private void updateFullName(User user) {
		String name = null;
		if (user.getFirstName() != null && user.getLastName() != null) {
			name = user.getFirstName() + " " + user.getLastName();
		} else if (user.getFirstName() != null) {
			name = user.getFirstName();
		} else if (user.getLastName() != null) {
			name = user.getLastName();
		} else if(user.getName() != null) {
			name = user.getName();
		}
		user.setName(name);
	}
	
	public User getCurrentUser() {
		User response = null;
		User user = null;
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() instanceof User) {
			user = (User) auth.getPrincipal();
		}
		if(user != null && user.getId() != null) {
			response = findUserById(user.getId());
		}
		return response;
	}
	
	// auth - start
	public String hashPassword(String rawPassword) {
		if(rawPassword == null) {
			return null;
		}
		// Encrypt using BCrypt
		String encryptedPassword = passwordEncoder.encode(rawPassword);
		return encryptedPassword;
	}

	public ResponseEntity<User> login(User user, HttpServletResponse response) {
		User entity = repository.findByUserName(user.getUserName());
		if(entity == null) {
			entity = repository.findByEmail(user.getUserName());
		}
		if(entity == null) {
			user.setMessage("User not found !");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(user);
		}
		boolean isValid = validatePassword(user.getPassword(), entity.getPassword());
		if(!isValid) {
			user.setMessage("Incorrect password !");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(user);
		}
		String accessToken = generateAccessToken(entity);
		entity.setAccessToken(accessToken);
		repository.save(entity);
		addAccessTokenToCookie(entity.getAccessToken(), response);
		return ResponseEntity.ok(user);
	}

	public ResponseEntity<User> logout(HttpServletResponse response) {
		Cookie cookie = new Cookie("_wsid", null);
		// cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setMaxAge(0); // delete cookie
		response.addCookie(cookie);
		User user = new User();
		user.setMessage("Logout successfully!");
		return ResponseEntity.ok(user);
	}

	public String generateAccessToken(User user) {
		String jwt = Jwts.builder()
				.setSubject(user.getId())
				.claim("user_name", user.getUserName())	// custom claim
				// .claim("expires_in", 3600) // custom claim
				// .claim("auth_type", user.getAuthType())	// custom claim
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
				.signWith(Keys.hmacShaKeyFor(Constants.SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
				.compact();
		return jwt;
	}

	public boolean validateAccessToken(String token) {
		// Confirms JWT is signed with our secret key.
		// Ensures token is not tampered
		try {
			Jwts.parser()
			.setSigningKey(Keys.hmacShaKeyFor(Constants.SECRET.getBytes(StandardCharsets.UTF_8)))
			.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			System.out.println("Invalid JWT: " + e.getMessage());
			return false;
		}
	}

	public boolean validatePassword(String requestPassword, String savedPassword) {
		// Compare raw password with encoded stored password
		boolean passwordMatch = passwordEncoder.matches(requestPassword, savedPassword);
		if(!passwordMatch) {
			return false;
		}
		return true;
	}

	public User getDetailsFromToken(String token) {
		User user = new User();
		Claims claims = null;
		try {
			claims = Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(Constants.SECRET.getBytes(StandardCharsets.UTF_8)))
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException ex) {
			// the claims still exist inside exception, even token expired
	        claims = ex.getClaims();
		}
		user.setId(claims.getSubject());
		user.setUserName(claims.get("user_name", String.class));
		return user;
	}

	public void googleCallback(String authCode, HttpServletResponse response) {
		// 1. Exchange code for access token
		User tokens = exchangeAuthorizationCodeForAccessToken(authCode);

		// 2. Get user info from Google
		Map<String, Object> userInfo = getUserInfoFromGoogle(tokens.getAccessToken());

		// 3. Register / login user in our system
		registerOrLoginGoogleUser(userInfo, tokens, response);
	}

	@SuppressWarnings("unchecked")
	public User exchangeAuthorizationCodeForAccessToken(String authCode) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); // MultiValueMap - spring class
		params.add("code", authCode);
		params.add("client_id", Constants.CLIENT_ID_GOOGLE);
		params.add("client_secret", Constants.CLIENT_SECRET_GOOGLE);
		params.add("redirect_uri", Constants.REDIRECT_URI_GOOGLE);
		params.add("grant_type", "authorization_code");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded");

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

		Map<String, String> tokenResponse = restTemplate.postForObject(
				Constants.TOKEN_URI_GOOGLE,
				requestEntity,
				Map.class
				);

		User tokens = new User();
		// access token (valid for 1 hour)
		String accessToken = tokenResponse.get("access_token");
		String refreshToken = tokenResponse.get("refresh_token");
		String idToken = tokenResponse.get("id_token");
		tokens.setAccessToken(accessToken);
		tokens.setGoogleRefreshToken(refreshToken);
		return tokens;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getUserInfoFromGoogle(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		HttpEntity<String> entity = new HttpEntity<>(headers);

		Map<String, Object> userInfo = restTemplate.exchange(
				Constants.USER_DETAILS_URI_GOOGLE,
				HttpMethod.GET,
				entity,
				Map.class
				).getBody();

		return userInfo;
	}

	public void registerOrLoginGoogleUser(Map<String, Object> userInfo, User tokens, HttpServletResponse response) {
		String id = (String) userInfo.get("id");
		String email = (String) userInfo.get("email");
		String name = (String) userInfo.get("name");	// full name
		String givenName = (String) userInfo.get("given_name");	// first name
		String familyName = (String) userInfo.get("family_name");	// last name
		String picture = (String) userInfo.get("picture");
		User entity = repository.findByGoogleUserId(id);
		if(entity == null) {
			entity = repository.findByEmail(email);
		}
		if(entity == null) {
			entity = new User();
		}
		entity.setGoogleUserId(id);
		entity.setEmail(email);
		entity.setName(name);
		entity.setFirstName(givenName);
		entity.setLastName(familyName);
		entity.setAuthType(AuthType.OAUTH2_AUTHORIZATION_CODE);
		entity.setGoogleRefreshToken(tokens.getGoogleRefreshToken());
		List<String> fileIds = downloadAndSetImages(List.of(picture));
		entity.setFiles(fileIds);
		entity = repository.save(entity);	// create/update
		String accessToken = generateAccessToken(entity);
		entity.setAccessToken(accessToken);
		repository.save(entity);	// update
		addAccessTokenToCookie(accessToken, response);
	}

	@SuppressWarnings("unchecked")
	public String getAccessTokenUsingRefreshToken(String refreshToken) {

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("client_id", Constants.CLIENT_ID_GOOGLE);
		params.add("client_secret", Constants.CLIENT_SECRET_GOOGLE);
		params.add("refresh_token", refreshToken);
		params.add("grant_type", "refresh_token");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded");

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

		Map<String, Object> response = restTemplate.postForObject(
				Constants.TOKEN_URI_GOOGLE,
				requestEntity,
				Map.class
				);
		// new access token (valid for 1 hour)
		String accessToken = (String) response.get("access_token");
		return accessToken;
	}

	public void addAccessTokenToCookie(String accessToken, HttpServletResponse response) {
		Cookie accessCookie = new Cookie("_wsid", accessToken);	// Web Session ID - no official abbreviation
		// accessCookie.setHttpOnly(true);	// now cookies cannot be accessed by JavaScript
		// accessCookie.setSecure(true);	// Cookie is sent only over HTTPS, Not sent over HTTP
		accessCookie.setPath("/");
		accessCookie.setMaxAge(24 * 60 * 60); // 1 day
		response.addCookie(accessCookie);
	}
	
	public User getUserDetailsByIdWithAccessToken(String token) {
		User response = null;
		User user = getDetailsFromToken(token);
		// check with our user id
		response = repository.findById(user.getId()).orElse(null);
		return response;
	}
	
	private List<String> downloadAndSetImages(List<String> imagePaths) {
		List<String> fileIds = null;
		List<MultipartFile> multipartFiles = new ArrayList<MultipartFile>();
		String imgName = "image_" + System.currentTimeMillis() + ".png";
		for(String path: imagePaths) {
			byte[] imageBytes = restTemplate.getForObject(path, byte[].class);
			MultipartFile multipartFile = new CustomMultipartFile("files", imgName, "image/png", imageBytes);
			multipartFiles.add(multipartFile);
		}
		fileIds = fileClient.uploadFile(multipartFiles);
		return fileIds;
	}
	
	// auth - end

	public User isUserExists(User request) {
		User user = null;
		if(user == null && request.getUserName() != null) {
			user = repository.findByUserName(request.getUserName());
			if(user != null) {
				user.setMessage("User already exists with given user name!");
				return user;
			}
		}
		if(user == null && request.getEmail() != null) {
			user = repository.findByEmail(request.getEmail());
			if(user != null) {
				user.setMessage("User already exists with given email!");
				return user;}
		}
		return user;
	}
	}
