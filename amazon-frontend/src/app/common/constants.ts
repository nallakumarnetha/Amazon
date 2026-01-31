export class Constants {

    public static readonly CLIENT_ID_GOOGLE = '853320511518-v0s4mpgoqivhcs8ui04ekmjts4e4qf9v.apps.googleusercontent.com';

    public static readonly CLIENT_SECRET_GOOGLE = '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';

    public static readonly PROJECT_ID_GOOGLE = 'my-project-01-479011';

    public static readonly AUTH_URI_GOOGLE = 'https://accounts.google.com/o/oauth2/auth';

    public static readonly TOKEN_URI_GOOGLE = 'https://oauth2.googleapis.com/token';

    // public static readonly REDIRECT_URI_GOOGLE = 'http://localhost:8080/amazon/users/oauth2/callback'; // monolithic
    public static readonly REDIRECT_URI_GOOGLE = 'http://localhost:8088/user/users/oauth2/callback'; // microservices

    public static readonly SCOPE_GOOGLE = 'openid profile email';

    public static readonly RESPONSE_TYPE_GOOGLE = 'code';

    public static readonly ACCESS_TYPE_GOOGLE = 'offline';

    public static readonly PROMPT_GOOGLE = 'consent';

}



