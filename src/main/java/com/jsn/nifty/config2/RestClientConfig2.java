package com.jsn.nifty.config2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.Base64;

@Configuration
public class RestClientConfig2 {

    @Value("${proxy.host}")
    private String proxyHost;

    @Value("${proxy.port}")
    private int proxyPort;

    @Value("${proxy.username}")
    private String proxyUsername;

    @Value("${proxy.password}")
    private String proxyPassword;

    @Value("${ssl.trust-store}")
    private String trustStorePath;

    @Value("${ssl.trust-store-password}")
    private String trustStorePassword;

    @Bean
    public RestClient restClient() throws Exception {
        return RestClient.builder()
                .requestFactory(sslProxyRequestFactory())
                .build();
    }

    @Bean
    public ClientHttpRequestFactory sslProxyRequestFactory() throws Exception {
        // Configure SSL context with custom truststore
        SSLContext sslContext = SSLContext.getInstance("TLS");
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

        // Load truststore from classpath
        KeyStore trustStore = KeyStore.getInstance("JKS");
        try (InputStream trustStoreStream = getClass().getResourceAsStream(trustStorePath)) {
            trustStore.load(trustStoreStream, trustStorePassword.toCharArray());
        }

        trustManagerFactory.init(trustStore);
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        // Configure proxy with authentication
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException, IOException {
                super.prepareConnection(connection, httpMethod);

                // Set proxy authentication
                if (proxyUsername != null && !proxyUsername.isEmpty()) {
                    String auth = proxyUsername + ":" + proxyPassword;
                    String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
                    connection.setRequestProperty("Proxy-Authorization", "Basic " + encodedAuth);
                }

                // Set SSL context
                if (connection instanceof HttpsURLConnection) {
                    ((HttpsURLConnection) connection).setSSLSocketFactory(sslContext.getSocketFactory());
                }
            }
        };

        requestFactory.setProxy(proxy);
        return requestFactory;
    }

    // Optional: For creating HTTP interface proxies
    @Bean
    public MyApiService2 myApiService(RestClient restClient) {
        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build()
                .createClient(MyApiService2.class);
    }
}