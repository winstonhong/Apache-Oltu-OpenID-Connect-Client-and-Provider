/**
 *       Copyright 2010 Newcastle University
 *
 *          http://research.ncl.ac.uk/smart/
 *
 *		 Author : Yang Hong
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

public class JwkEndpoint extends HttpServlet {

    /**
     * JSON Web Key endpoint for describing OpenID Provider (OP)'s public key to verify signature
     * @param request {@link HttpServletRequest}
     * @return response {@link HttpServletResponse}
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            /**
             * Respond the OpenID Provider (OP)'s public JSON Web Key (JWK).
             * This contains the signing key(s) the RP uses to validate signatures from the OP.
             */
            JSONObject json_key = new JSONObject();
            json_key.put(OIDC.Response.KTY,OIDC.jwk_1_kty);
            json_key.put(OIDC.Response.ALG,OIDC.jwk_1_alg);
            json_key.put(OIDC.Response.USE,OIDC.jwk_1_use);
            json_key.put(OIDC.Response.KID,OIDC.jwk_1_kid);
            json_key.put("n",OIDC.jwk_1_n);
            json_key.put("e",OIDC.jwk_1_e);
            JSONArray json_web_keys = new JSONArray();
            json_web_keys.put(json_key);
            JSONObject json = new JSONObject();
            json.put("keys", json_web_keys);

            response.setStatus(200);
            response.setContentType("application/json; charset=UTF-8");
            json.write(response.getWriter());

            return;

        } catch (JSONException  e) {
            System.out.println("System Error while creating JSON data for OpenID's signature public key: " + e);
            return;
        }
    }
}
