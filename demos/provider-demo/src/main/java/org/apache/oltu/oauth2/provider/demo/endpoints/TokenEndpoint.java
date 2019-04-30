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
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import java.util.UUID;

import org.apache.oltu.commons.encodedtoken.TokenDecoder;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.jwt.JWT;
import org.apache.oltu.oauth2.jwt.io.JWTWriter;
import org.apache.oltu.oauth2.jwt.io.JWTClaimsSetWriter;
import org.apache.oltu.oauth2.jwt.io.JWTHeaderWriter;
import org.apache.oltu.jose.jws.signature.impl.SignatureMethodRSAImpl;
import org.apache.oltu.jose.jws.signature.impl.PrivateKey;
import org.apache.oltu.oauth2.provider.demo.Common;
import org.apache.oltu.oauth2.provider.demo.OIDC;
import org.apache.oltu.oauth2.provider.demo.OpenidASResponse;

/**
 *
 *
 *
 */

public class TokenEndpoint extends HttpServlet {
	public static final String INVALID_CLIENT_DESCRIPTION = "Client authentication failed (e.g., unknown client, no client authentication included, or unsupported authentication method).";

	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response) throws ServletException, IOException {
		try {
			OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);

			// check if clientid is valid
			if (!Common.CLIENT_ID.equals(oauthRequest.getClientId())) {
				OAuthResponse resp = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_CLIENT).setErrorDescription(INVALID_CLIENT_DESCRIPTION)
						.buildJSONMessage();
				response.sendError(401);
				return;
			}

			// check if client_secret is valid
			if (!Common.CLIENT_SECRET.equals(oauthRequest.getClientSecret())) {
				OAuthResponse resp = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT).setErrorDescription(INVALID_CLIENT_DESCRIPTION)
						.buildJSONMessage();
				response.sendError(401);
				return;
			}

			// do checking for different grant types
			if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
					.equals(GrantType.AUTHORIZATION_CODE.toString())) {
				if (!Common.AUTHORIZATION_CODE.equals(oauthRequest.getParam(OAuth.OAUTH_CODE))) {
					OAuthResponse resp = OAuthASResponse
							.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
							.setError(OAuthError.TokenResponse.INVALID_GRANT)
							.setErrorDescription("invalid authorization code")
							.buildJSONMessage();
					response.sendError(401);
					return;
				}
			} else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
					.equals(GrantType.PASSWORD.toString())) {
				if (!Common.PASSWORD.equals(oauthRequest.getPassword())
						|| !Common.USERNAME.equals(oauthRequest.getUsername())) {
					OAuthResponse resp = OAuthASResponse
							.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
							.setError(OAuthError.TokenResponse.INVALID_GRANT)
							.setErrorDescription("invalid username or password")
							.buildJSONMessage();
					response.sendError(401);
					return;
				}
			} else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
					.equals(GrantType.REFRESH_TOKEN.toString())) {
				// refresh token is not supported in this implementation
				OAuthResponse resp = OAuthASResponse
						.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_GRANT)
						.setErrorDescription("invalid username or password")
						.buildJSONMessage();
				response.sendError(401);
				return;
			}

			OAuthResponse resp = null;

			if (oauthRequest.getParam(OAuth.OAUTH_SCOPE).contains(Common.OPENID)) {
				resp =  OpenidASResponse
						.tokenResponse(HttpServletResponse.SC_OK)
						.setIDToken(createIdToken(request))
						.setAccessToken(Common.ACCESS_TOKEN_VALID)
						.setTokenType(OAuth.DEFAULT_TOKEN_TYPE.toString())
						.setExpiresIn("3600")
						.buildJSONMessage();
			} else {
				resp = OAuthASResponse
						.tokenResponse(HttpServletResponse.SC_OK)
						.setAccessToken(Common.ACCESS_TOKEN_VALID)
						.setTokenType(OAuth.DEFAULT_TOKEN_TYPE.toString())
						.setExpiresIn("3600")
						.buildJSONMessage();
			}

			response.setStatus(resp.getResponseStatus());
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.println(resp.getBody().toString());
			pw.flush();
			pw.close();
			return;

			//if something goes wrong


		} catch (OAuthSystemException e) {
			System.out.println("Caught OAuthSystemException: " + e.getMessage());
			return;
		} catch (OAuthProblemException e) {
			System.out.println("Caught OAuthProblemException: " + e.getMessage());
			return;
		}
	}

	/**
	 * Create ID token
	 * @param request {@link HttpServletRequest}
	 * @return idToken {@link idToken}
	 */
	private String createIdToken(HttpServletRequest request) {

		String openid_username = Common.USERNAME;
		String openid_email =  Common.EMAIL;
		String client_id = Common.CLIENT_ID;
		String nonce = request.getHeader(OIDC.AuthZRequest.NONCE);
		if (nonce == null) {
			nonce = UUID.randomUUID().toString().replace("-", "");
		}

		long id_token_generation_time = System.currentTimeMillis()/1000;
		long id_token_expiration_time = id_token_generation_time + 3600;

		JWT idJwt = new JWT.Builder()
				// header
				.setHeaderAlgorithm(OIDC.id_token_alg1)
				.setHeaderCustomField(OIDC.Response.KID, OIDC.jwk_1_kid)
				// claimset
				.setClaimsSetExpirationTime(id_token_expiration_time)
				.setClaimsSetIssuedAt(id_token_generation_time)
				.setClaimsSetCustomField(OIDC.IdToken.ISS, request.getScheme() + "://" + request.getServerName() + ":"
						+ request.getServerPort())
				.setClaimsSetCustomField(OIDC.IdToken.SUB, openid_username)
				.setClaimsSetCustomField(OIDC.IdToken.AUD, client_id)
				.setClaimsSetCustomField(OIDC.IdToken.EMAIL, openid_email)
				.setClaimsSetCustomField("verified_email", "true")
				.setClaimsSetCustomField(OIDC.IdToken.EMAIL_VERIFIED, "true")
				.setClaimsSetCustomField(OIDC.Response.KID, OIDC.jwk_1_kid)
				.setClaimsSetCustomField(OIDC.AuthZRequest.NONCE, nonce)
				// signature
				.setSignature(null)
				.build();

		String idJwt_signature = null;
		try{
			final byte[] n = TokenDecoder.base64DecodeToByte(OIDC.jwk_1_n);
			final byte[] d = TokenDecoder.base64DecodeToByte(OIDC.jwk_1_d);

			BigInteger N = new BigInteger(1, n);
			BigInteger D = new BigInteger(1, d);

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(N, D);
			RSAPrivateKey rsaPrivKey = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);

			String idJwt_header = new JWTHeaderWriter().write(idJwt.getHeader());
			String idJwt_payload = new JWTClaimsSetWriter().write(idJwt.getClaimsSet());
			SignatureMethodRSAImpl sRsaImpl = new SignatureMethodRSAImpl(OIDC.id_token_alg1);

			idJwt_signature = sRsaImpl.calculate(TokenDecoder.base64Encode(idJwt_header),
					TokenDecoder.base64Encode(idJwt_payload), new PrivateKey(rsaPrivKey));

		}catch(Exception e){
			throw new RuntimeException(e);
		}

		idJwt = new JWT.Builder()
				// header
				.setHeaderAlgorithm(OIDC.id_token_alg1)
				.setHeaderCustomField(OIDC.Response.KID, OIDC.jwk_1_kid)
				// claimset
				.setClaimsSetExpirationTime(id_token_expiration_time)
				.setClaimsSetIssuedAt(id_token_generation_time)
				.setClaimsSetCustomField(OIDC.IdToken.ISS, request.getScheme() + "://" + request.getServerName() + ":"
						+ request.getServerPort())
				.setClaimsSetCustomField(OIDC.IdToken.SUB, openid_username)
				.setClaimsSetCustomField(OIDC.IdToken.AUD, client_id)
				.setClaimsSetCustomField(OIDC.IdToken.EMAIL, openid_email)
				.setClaimsSetCustomField("verified_email", "true")
				.setClaimsSetCustomField(OIDC.IdToken.EMAIL_VERIFIED, "true")
				.setClaimsSetCustomField(OIDC.Response.KID, OIDC.jwk_1_kid)
				.setClaimsSetCustomField(OIDC.AuthZRequest.NONCE, nonce)
				// signature
				.setSignature(idJwt_signature)
				.build();

		String idToken = new JWTWriter().write(idJwt);
		return idToken;
	}

}
