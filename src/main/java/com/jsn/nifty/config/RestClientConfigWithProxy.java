/*
package com.jsn.nifty.config;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.Credentials;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.util.Timeout;
import org.apache.hc.core5.util.TimeValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.security.KeyStore;

@Configuration
public class RestClientConfigWithProxy {

    @Value("${javax.net.ssl.trustStore}")
    private String truststorePath;

    @Value("${javax.net.ssl.trustStorePassword}")
    private String truststorePassword;

    @Value("${http.proxyHost}")
    private String proxyHost;

    @Value("${http.proxyPort}")
    private int proxyPort;

    @Value("${http.proxyUser}")
    private String proxyUsername;

    @Value("${http.proxyPassword}")
    private String proxyPassword;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) throws Exception {
        // Set up credentials for proxy authentication
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        Credentials credentials = new UsernamePasswordCredentials(proxyUsername, proxyPassword.toCharArray());
        credentialsProvider.setCredentials(new AuthScope(proxyHost, proxyPort), credentials);

        // Load the truststore
        KeyStore truststore = KeyStore.getInstance(KeyStore.getDefaultType());
        truststore.load(new File(truststorePath).toURI().toURL().openStream(), truststorePassword.toCharArray());

        // Create SSLContext with the truststore
        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(truststore, null)
                .build();

        // Create a custom SSLConnectionSocketFactory
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext);

        // Create a Registry with the custom SSLConnectionSocketFactory
        Registry registry = RegistryBuilder.create()
                .register("https", sslSocketFactory)
                .build();

        // Configure connection manager with the custom Registry
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setDefaultSocketConfig(SocketConfig.custom()
                .setSoTimeout(Timeout.ofSeconds(30))
                .build());
        connectionManager.setValidateAfterInactivity(TimeValue.ofSeconds(5));

        // Configure the proxy
        HttpHost proxy = new HttpHost(proxyHost, proxyPort);

        // Create HttpClient with proxy, credentials, and connection manager
        CloseableHttpClient httpClient = HttpClients.custom()
                .setProxy(proxy)
                .setDefaultCredentialsProvider(credentialsProvider)
                .setConnectionManager(connectionManager)
                .build();

        // Create a RestTemplate using the HttpClient
        return restTemplateBuilder
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
                .build();
    }
}*/
