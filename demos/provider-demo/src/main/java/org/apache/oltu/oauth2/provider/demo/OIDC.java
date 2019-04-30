package org.apache.oltu.oauth2.provider.demo;

public final class OIDC {
    
    public static final class AuthZRequest {
		public static final String NONCE = "nonce";
		public static final String DISPLAY = "display";
		public static final String PROMPT = "prompt";
		public static final String REQUEST = "request";
		public static final String REQUEST_URI = "request_uri";
		public static final String ID_TOKEN_HINT = "id_token_hint";
		public static final String LOGIN_HINT = "login_hint";
    }

    public static final class Response {
        public static final String ALG = "alg";
		public static final String ID_TOKEN = "id_token";
        public static final String KID = "kid";
        public static final String KTY = "kty";
        public static final String USE = "use";
    }
	
    public static final class Error {
		public static final String LOGIN_REQUIRED = "login_required";
		public static final String CONSENT_REQUIRED = "consent_required";
    }
	
    public static final class Prompt {
		public static final String NONE = "none";
		public static final String LOGIN = "login";
		public static final String CONSENT = "consent";
		public static final String SELECT_PROFILE = "select_profile";
    }
    
    public static final class IdToken {
		// REQUIRED
		public static final String ISS = "iss";
		public static final String SUB = "sub";
		public static final String AUD = "aud";
		public static final String EXP = "exp";
		public static final String IAT = "iat";
		// REQUIRED with Implicit flow, OPTIONAL with code flow
		public static final String AT_HASH = "at_hash";
		public static final String C_HASH = "c_hash";
		public static final String NONCE = "nonce";
		// OPTIONAL
		public static final String AZP = "azp";
		public static final String ACR = "acr";
		public static final String AUTH_TIME = "auth_time";
		public static final String EMAIL = "email";
		public static final String EMAIL_VERIFIED = "email_verified";
    }    

    // OpenID Connect parameters
    public static final String OPENID = "openid";    
    public static final String id_token_alg1 = "RS256";    
    
    // OpenID Provider (OP)'s JSON Web Key (JWK) pair #1
	// Warning: Never use the key pair below on your production environment.
    public static final String jwk_1_kty = "RSA";
    public static final String jwk_1_alg = id_token_alg1;
    public static final String jwk_1_use = "sig";
    public static final String jwk_1_kid = "idq";
    public static final String jwk_1_d = "aysMxTjwMJOlYJGZt9uBWrts0wgIZ9ypqM9r1Y9eFgM4I91FgY78nTZ5ra3P-SI-9kEs9uC45Rmg5BpT1Uk1JAK1CbANEGH7VAZn61D9ja4YChK3s9O6u2vVpeVL3kNG62iTHxzP9dipXsuSEOHJHmP-TSVwceZ3CmAV0sRIPX-rmguF3x71BlWzsFdv8-CSCMlwqnMESMh8rr3cexjDMx4I7KMWSwKJno6JHr0u7tgMh7E2zFNVamb7LRHG_S6NSGo6EEm5Sq4MF6diMhu4XU8_Na-Md-6Aw1_lEWJSkiZU4wwY-R08HSbJM8DIBHQz-ihN-WKhWs2n5_sZBS4igQ";
    public static final String jwk_1_n = "jPc-kA9mpWzLKTkGIdCcziohSXAcMoCqXa7iGfQZLwfM5vQ1wkip6EHilyOqs82lshDzoLUbKLTkM5pZB-B8ND5Ha-KFgqA0O9t6ayojA61Tph5DHdGLVyz5WZUN_m8CYJfqVhKoIcAJfg1nkMpeYJTIL4lG_AHaw3LD62rYto0gU9M1XziGS8vHnSzQ7iwXJr9ufd2HwUnBpBBQz4fXiWGdhbB-5jvxaeVBB4UTIkKZE6_N9F2S2i0IXJw_mrANkWp7pzJmvCBZ0keRoNFo33nQghUm2JRGW6ZN_R7zSkRG8VbKjzBSHdiQ1f7SbyOexg8-aL9ErgkFvW7-F8l8qQ";
    public static final String jwk_1_e = "AQAB";                             
    
}
