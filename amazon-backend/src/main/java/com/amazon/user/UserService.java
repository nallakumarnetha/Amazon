package com.amazon.user;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
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

import com.amazon.common.Constants;
import com.amazon.common.Response;
import com.amazon.file.FileService;
import com.amazon.id.IdService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private FileService fileService;

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
				Map<String, String> base64FilesData = fileService.getBase64Files(fileIds);
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
			Map<String, String> base64FilesData = fileService.getBase64Files(user.getFiles());
			user.setBase64Files(base64FilesData);
		}
		return user;
	}

	public User addUser(User request, HttpServletResponse response) {
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

	public User updateUser(String id, User request) {
		User entity = repository.findById(id).orElse(null);
		if (entity == null) return null;

		if (request.getName() != null && !request.getName().isEmpty()) {
			entity.setName(request.getName());
		}

		if (request.getFirstName() != null && !request.getFirstName().isEmpty()) {
			entity.setFirstName(request.getFirstName());
		}

		if (request.getLastName() != null && !request.getLastName().isEmpty()) {
			entity.setLastName(request.getLastName());
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

		if (request.getPassword() != null && !request.getPassword().isEmpty()) {
			entity.setPassword(request.getPassword());
		}

		if (request.getAccessToken() != null && !request.getAccessToken().isEmpty()) {
			entity.setAccessToken(request.getAccessToken());
		}

		return repository.save(entity);
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
			user.setMessage("User not found");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(user);
		}
		boolean isValid = validatePassword(user.getPassword(), entity.getPassword());
		if(!isValid) {
			user.setMessage("Incorrect password");
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
		try {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(Constants.SECRET.getBytes(StandardCharsets.UTF_8)))
					.build()
					.parseClaimsJws(token)
					.getBody();
			user.setId(claims.getSubject());
			user.setUserName(claims.get("user_name", String.class));
		} catch (Exception e) {
			return null;
		}
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
		User entity = repository.findByGoogleUserId(id);
		String email = (String) userInfo.get("email");
		String name = (String) userInfo.get("name");	// full name
		String givenName = (String) userInfo.get("given_name");	// first name
		String familyName = (String) userInfo.get("family_name");	// last name
		String picture = (String) userInfo.get("picture");
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
		// user picture
			entity.setFiles(null);
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
	
	// auth - end

}
