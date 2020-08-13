package com.sackprom.priceEstimator.message.response;

/*
 * JwtResponse.java is returned by SpringBoot server after successful authentication, 
 * it contains 2 parts:
 * 
 *  - JWT Token
 *  - Schema Type of Token
 */
public class JwtResponse {

	private String typeToken = "Bearer";
	private String accessToken;
    
    public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the typeToken
	 */
	public String getTypeToken() {
		return typeToken;
	}

	/**
	 * @param typeToken the typeToken to set
	 */
	public void setTypeToken(String typeToken) {
		this.typeToken = typeToken;
	}
}
