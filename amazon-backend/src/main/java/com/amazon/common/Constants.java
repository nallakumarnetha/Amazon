package com.amazon.common;

import java.util.Base64;

public class Constants {

	// token based - basic user name and password
	public static final String SECRET = "";
	
	
	// Oauth 2.0 - Authorization Code Flow | Google
	public static final String CLIENT_ID_GOOGLE = "";

	public static final String CLIENT_SECRET_GOOGLE = "";

	public static final String PROJECT_ID_GOOGLE = "";

	public static final String AUTH_URI_GOOGLE = "https://accounts.google.com/o/oauth2/auth";

	public static final String TOKEN_URI_GOOGLE = "https://oauth2.googleapis.com/token";

	public static final String REDIRECT_URI_GOOGLE = "http://localhost:8080/amazon/users/oauth2/callback";
	
	public static final String USER_DETAILS_URI_GOOGLE = "https://www.googleapis.com/oauth2/v2/userinfo";

}
