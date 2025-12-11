package fr.insa.coffee.PresenceMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import fr.insa.coffee.PresenceMS.config.DataSourceConfig;

@SpringBootApplication
@EnableConfigurationProperties(DataSourceConfig.class)
public class PresenceMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PresenceMsApplication.class, args);
	}

}
