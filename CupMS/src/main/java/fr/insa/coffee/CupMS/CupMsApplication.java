package fr.insa.coffee.CupMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import fr.insa.coffee.CupMS.config.DataSourceConfig;

@SpringBootApplication
@EnableConfigurationProperties(DataSourceConfig.class)
public class CupMsApplication {
	public static void main(String[] args) {
		SpringApplication.run(CupMsApplication.class, args);
	}
}
