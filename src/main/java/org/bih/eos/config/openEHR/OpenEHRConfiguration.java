package org.bih.eos.config.openEHR;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.ehrbase.client.openehrclient.OpenEhrClientConfig;
import org.ehrbase.client.openehrclient.defaultrestclient.DefaultRestClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * {@link Configuration} for openEHR.
 *
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(OpenEHRProperties.class)
@SuppressWarnings("java:S6212")
public class OpenEHRConfiguration {

    private final OpenEHRProperties properties;

    public OpenEHRConfiguration(OpenEHRProperties properties) {
        this.properties = properties;
    }

    @Bean
    public OpenEhrClientConfig restClientConfig() throws URISyntaxException {
        return new OpenEhrClientConfig(new URI(properties.getBaseUrl()));
    }

    @Bean
    public DefaultRestClient openEhrClient(OpenEhrClientConfig restClientConfig,
                                           HttpClient ehrbaseHttpClient) {
        return new DefaultRestClient(restClientConfig, null, ehrbaseHttpClient);
    }

    @Bean
    public HttpClient ehrbaseHttpClient(ObjectProvider<AccessTokenService> accessTokenService) {
        HttpClientBuilder builder = HttpClientBuilder.create();

        OpenEHRProperties.Security security = properties.getSecurity();
        if (security.getType() == OpenEHRProperties.AuthorizationType.BASIC) {
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            OpenEHRProperties.User user = security.getUser();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user.getName(), user.getPassword()));
            builder.setDefaultCredentialsProvider(credentialsProvider);
        } else if (security.getType() == OpenEHRProperties.AuthorizationType.OAUTH2) {
            builder.addInterceptorFirst(new TokenAuthenticationInterceptor(accessTokenService.getIfAvailable()));
        }

        return builder.build();
    }

    @Bean
    @ConditionalOnProperty(name = "omop-bridge.openehr.security.type", havingValue = "oauth2")
    public AccessTokenService accessTokenService() {
        OpenEHRProperties.OAuth2 oauth2 = properties.getSecurity().getOAuth2();
        return new AccessTokenService(oauth2.getTokenUrl(), oauth2.getClientId(), oauth2.getClientSecret());
    }

}

