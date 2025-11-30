package com.amazon.common;

import java.util.Base64;

public class Constants {

	// token based - basic user name and password
	public static final String SECRET = "P3s9A!kL@q8ZxB#tF$w7Gh2Np6DvQ1Uy";
	
	
	// Oauth 2.0 - Authorization Code Flow | Google
	public static final String CLIENT_ID_GOOGLE = "853320511518-v0s4mpgoqivhcs8ui04ekmjts4e4qf9v.apps.googleusercontent.com";

	public static final String CLIENT_SECRET_GOOGLE = "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";

	public static final String PROJECT_ID_GOOGLE = "my-project-01-479011";

	public static final String AUTH_URI_GOOGLE = "https://accounts.google.com/o/oauth2/auth";

	public static final String TOKEN_URI_GOOGLE = "https://oauth2.googleapis.com/token";

	public static final String REDIRECT_URI_GOOGLE = "http://localhost:8080/amazon/users/oauth2/callback";
	
	public static final String USER_DETAILS_URI_GOOGLE = "https://www.googleapis.com/oauth2/v2/userinfo";

	// email
	public static final String MAIL_HOST = "smtp.gmail.com";
	public static final int MAIL_PORT = 587;

	public static final String MAIL_USERNAME = "kumarwavity@gmail.com";
	public static final String MAIL_PASSWORD = "eyqi qusm iuap vepe"; // app password

	public static final String MAIL_PROTOCOL = "smtp";
	public static final String MAIL_SMTP_AUTH = "true";
	public static final String MAIL_SMTP_STARTTLS = "true";

	
}
