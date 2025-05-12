/*


package com.jsn.nifty.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    // Inject MyRestTemplateCustomizer
    MyRestTemplateCustomizer myRestTemplateCustomizer;

    public RestTemplateConfig(MyRestTemplateCustomizer myRestTemplateCustomizer) {
        this.myRestTemplateCustomizer = myRestTemplateCustomizer;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        // Use the customizer to modify RestTemplate configuration
        //return restTemplateBuilder.customizers(myRestTemplateCustomizer).build();
        RestTemplate restTemplate = new RestTemplate();
        myRestTemplateCustomizer.customize(restTemplate);
        return restTemplate;
    }
}

*/
