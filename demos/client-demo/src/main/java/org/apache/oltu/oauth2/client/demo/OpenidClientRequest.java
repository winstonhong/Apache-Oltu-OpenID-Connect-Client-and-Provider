package org.apache.oltu.oauth2.client.demo;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;

public class OpenidClientRequest extends OAuthClientRequest {

    protected OpenidClientRequest(String uri) {
        super(uri);
    }

    public static OpenidRequestBuilder authorizationLocation(String url) {
        return new OpenidRequestBuilder(url);
    }


    public  static class OpenidRequestBuilder extends AuthenticationRequestBuilder {

        public OpenidRequestBuilder(String url) {
            super(url);
        }

        public OpenidRequestBuilder setNonce(String nonce)

        {
            this.parameters.put(Utils.Request.NONCE, nonce);
            return this;
        }

    }

    /*static class OpenidRequestBuilder extends AuthenticationRequestBuilder {

        public OpenidRequestBuilder(String url) {
        super(url);
    }

        public OpenidRequestBuilder setNonce(String nonce)

        {
            this.parameters.put(Utils.Request.NONCE, nonce);
            return this;
        }
    }



    static class OpenidRequestBuilder extends AuthenticationRequestBuilder {

        public OpenidRequestBuilder(String url) {
            super(url);
        }

        public OpenidRequestBuilder setNonce(String nonce) {
            this.parameters.put(Utils.Request.NONCE, nonce);
            return this;
        }

    }*/

}