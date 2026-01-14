package fr.insa.coffee.LEDMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import fr.insa.coffee.LEDMS.config.DataSourceConfig;

@SpringBootApplication
@EnableConfigurationProperties(DataSourceConfig.class)
public class LEDMsApplication {
	public static void main(String[] args) {
		SpringApplication.run(LEDMsApplication.class, args);
	}
}
