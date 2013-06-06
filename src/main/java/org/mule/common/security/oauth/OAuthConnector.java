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

import java.io.Serializable;

public interface OAuthConnector extends Serializable
{

    public String getAccessTokenUrl();

    public void setAccessTokenUrl(String url);
    
    public String getConsumerKey();
    
    public void setConsumerKey(String consumerKey);

    public String getConsumerSecret();
    
    public void setConsumerSecret(String consumerSecret);

    public String getAccessToken();
    
    public void setAccessToken(String accessToken);

    public String getScope();

    public void postAuth();
    
    public String getAuthorizationUrl();
    
    public void setAuthorizationUrl(String authorizationUrl);

    
    @Deprecated
    public String getAccessTokenId();

}
