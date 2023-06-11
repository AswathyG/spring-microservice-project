package com.project.springmicroserviceproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringMicroserviceProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMicroserviceProjectApplication.class, args);
	}

	@Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> 
							webServerFactoryCustomizer(@Value("${server.port}") int serverPort) {
        return factory -> {
            if (factory instanceof TomcatServletWebServerFactory) {
                TomcatServletWebServerFactory tomcatFactory = (TomcatServletWebServerFactory) factory;
                tomcatFactory.addConnectorCustomizers(connector -> {
                    connector.setScheme("https");
                    connector.setSecure(true);
                    connector.setPort(serverPort);
                });
            }
        };
    }
}
