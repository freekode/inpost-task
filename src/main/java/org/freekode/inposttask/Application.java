package org.freekode.inposttask;

import org.freekode.inposttask.infrastructure.DiscountsConfiguration;
import org.freekode.inposttask.infrastructure.ProductsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({DiscountsConfiguration.class, ProductsConfiguration.class})
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
