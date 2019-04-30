package org.apache.oltu.oauth2.provider.demo.endpoints;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.provider.demo.OIDC;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;


public class MetadataEndpoint extends HttpServlet {

    /**
     * Yang Hong: the metadata endpoint for describing OpenID Provider configuration
     * @param request {@link HttpServletRequest}
     * @return Response {@link Response}
     */
    // public Response createMetadata(HttpServletRequest request) throws ServletException, IOException {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // Respond the OpenID Provider Configuration
            JSONObject json = new JSONObject();
            json.put("issuer", request.getScheme() + "://" + request.getServerName() + ":"  + request.getServerPort() + "/auth/oauth2/");
            json.put("authorization_endpoint", request.getScheme() + "://" + request.getServerName() + ":"  + request.getServerPort() + "/auth/oauth2/auth");
            json.put("token_endpoint", request.getScheme() + "://" + request.getServerName() + ":"  + request.getServerPort() + "/auth/oauth2/token");
            json.put("userinfo_endpoint", request.getScheme() + "://" + request.getServerName() + ":"  + request.getServerPort() + "/auth/oauth2/resource_server/resource_query");
            json.put("revocation_endpoint", request.getScheme() + "://" + request.getServerName() + ":"  + request.getServerPort() + "/auth/oauth2/auth");
            json.put("jwks_uri", request.getScheme() + "://" + request.getServerName() + ":"  + request.getServerPort() + "/auth/oauth2/certs");
            JSONArray response_types_supported = new JSONArray();
            response_types_supported.put("code");
            json.put("response_types_supported", response_types_supported);
            JSONArray subject_types_supported = new JSONArray();
            subject_types_supported.put("public");
            json.put("subject_types_supported", subject_types_supported);
            JSONArray id_token_signing_alg_values_supported = new JSONArray();
            id_token_signing_alg_values_supported.put(OIDC.id_token_alg1);
            json.put("id_token_signing_alg_values_supported", id_token_signing_alg_values_supported);
            JSONArray scopes_supported = new JSONArray();
            scopes_supported.put("openid");
            scopes_supported.put("email");
            scopes_supported.put("profile");
            json.put("scopes_supported", scopes_supported);
            JSONArray token_endpoint_auth_methods_supported = new JSONArray();
            token_endpoint_auth_methods_supported.put("client_secret_post");
            token_endpoint_auth_methods_supported.put("client_secret_basic");
            json.put("token_endpoint_auth_methods_supported", token_endpoint_auth_methods_supported);
            JSONArray claims_supported = new JSONArray();
            claims_supported.put(OIDC.IdToken.AUD);
            claims_supported.put(OIDC.IdToken.EMAIL);
            claims_supported.put(OIDC.IdToken.EMAIL_VERIFIED);
            claims_supported.put(OIDC.IdToken.EXP);
            claims_supported.put(OIDC.IdToken.IAT);
            claims_supported.put(OIDC.IdToken.ISS);
            claims_supported.put(OIDC.IdToken.SUB);
            json.put("claims_supported", claims_supported);
            JSONArray code_challenge_methods_supported = new JSONArray();
            code_challenge_methods_supported.put("plain");
            code_challenge_methods_supported.put("S256");
            json.put("code_challenge_methods_supported", code_challenge_methods_supported);

            response.setStatus(200);
            response.setContentType("application/json; charset=UTF-8");
            json.write(response.getWriter());

            return;

        }catch(JSONException e){
            System.out.println("System Error while creating JSON data for OpenID provider configuration: "+e);
            return;
        }
    }

}
