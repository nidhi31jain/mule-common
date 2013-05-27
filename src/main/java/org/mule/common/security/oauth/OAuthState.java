/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.common.security.oauth;

import java.util.HashMap;
import java.util.Map;

public class OAuthState
{

    private String accessToken;
    private String authorizationUrl;
    private String accessTokenUrl;
    private String refreshToken;
    private Map<String, String> customProperties = new HashMap<String, String>();

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String getAuthorizationUrl()
    {
        return authorizationUrl;
    }

    public void setAuthorizationUrl(String authorizationUrl)
    {
        this.authorizationUrl = authorizationUrl;
    }

    public String getAccessTokenUrl()
    {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl)
    {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken)
    {
        this.refreshToken = refreshToken;
    }

    public Map<String, String> getCustomProperties()
    {
        return customProperties;
    }

    public void setCustomProperties(Map<String, String> customProperties)
    {
        this.customProperties = customProperties;
    }

}
