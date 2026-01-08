//package com.common_service.security;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.amazon.user.AuthType;
//import com.amazon.user.User;
//import com.amazon.user.UserService;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import static com.amazon.common.Logger.log;
//
//@Component
//public class MyJwtFilter extends OncePerRequestFilter {
//
//	@Autowired
//	private UserService userService;
//
//	@Override
//	protected void doFilterInternal(
//			HttpServletRequest request,
//			HttpServletResponse response,
//			FilterChain filterChain)
//					throws ServletException, IOException {
//		// no authorization required for these apis
//		List<String> noLoginApis = new ArrayList<>();
//		noLoginApis.add("/amazon/users/login");
//		noLoginApis.add("/amazon/users/register");
//		noLoginApis.add("/amazon/users/oauth2/callback");
//		noLoginApis.add("/amazon/files");
//		for (String path : noLoginApis) { 
//			if (request.getRequestURI().startsWith(path)) { 
//				filterChain.doFilter(request, response); 
//				return; 
//			}
//		}
//		String token = extractTokenFromCookies(request);
//		boolean isValid = userService.validateAccessToken(token);
//		
//		// get access token using refresh token
//		if (token != null && !isValid) {
//			User userDetails = userService.getUserDetailsByIdWithAccessToken(token);
//			if(userDetails != null && userDetails.getAuthType() == AuthType.OAUTH2_AUTHORIZATION_CODE) {
//				token =	userService.getAccessTokenUsingRefreshToken(userDetails.getGoogleRefreshToken());
//				if(token != null) {
//					token =	userService.generateAccessToken(userDetails);
//					userService.addAccessTokenToCookie(token, response);
//				}
//				isValid = userService.validateAccessToken(token);
//			}
//		}
//		
//		// If token invalid → block request
//		if (!isValid) {
//			log.info("API: {} | Authenticated: false", request.getRequestURI());
//			if (!response.isCommitted()) {
//	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//	            response.setContentType("application/json");
//	            response.getWriter().write("{\"error\":\"Unauthorized\"}");
//	            response.getWriter().flush();
//	        }
//			return;
//		}
//		log.info("API: {} | Authenticated: true", request.getRequestURI());
//		
//		// set authentication for Spring Security
//		UsernamePasswordAuthenticationToken auth =
//		        new UsernamePasswordAuthenticationToken(
//		            userService.getDetailsFromToken(token),	// principal → the user identifier (e.g., username or userId)
//		            token,	// credentials → can be null because token is already validated
//		            List.of() // authorities → list of roles/permissions (optional, empty if not used)
//		        );
//		SecurityContextHolder.getContext().setAuthentication(auth);
//		    
//		// Token is valid → allow request
//		filterChain.doFilter(request, response);
//	}
//
//	private String extractTokenFromCookies(HttpServletRequest request) {
//		// 1. Try cookie first
//		Cookie[] cookies = request.getCookies();
//		if(cookies != null) {
//			for (Cookie c : cookies) {
//				if (c.getName().equals("_wsid")) {	   // JWT in cookie
//					return c.getValue();
//				}
//			}
//		}
//		// 2. If not in cookie → try Authorization header
//		String authHeader = request.getHeader("Authorization");
//	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
//	        return authHeader.substring(7);
//	    }
//		return null;
//	}
//}
