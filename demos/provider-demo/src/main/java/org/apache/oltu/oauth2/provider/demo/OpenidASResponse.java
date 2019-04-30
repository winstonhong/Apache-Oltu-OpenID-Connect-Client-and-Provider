package org.apache.oltu.oauth2.provider.demo;

import org.apache.oltu.oauth2.as.response.OAuthASResponse;

public class OpenidASResponse extends OAuthASResponse {

    protected OpenidASResponse(String uri, int responseStatus) {
        super(uri, responseStatus);
    }
	
    public static OpenidTokenResponseBuilder tokenResponse(int code) {
        return new OpenidTokenResponseBuilder(code);
    }
    
    public static class OpenidTokenResponseBuilder extends OAuthTokenResponseBuilder {
	    public OpenidTokenResponseBuilder(int responseCode) {
            super(responseCode);
        }        
        public OpenidTokenResponseBuilder setIDToken(String token) {
            this.parameters.put(OIDC.Response.ID_TOKEN, token);
            return this;
        }
    }

}
