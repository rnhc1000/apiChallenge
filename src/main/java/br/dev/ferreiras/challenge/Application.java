package br.dev.ferreiras.challenge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private Environment environment;
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public Application(Environment environment) {
        this.environment = environment;
    }

    /**
	 * Application entry point
	 * @param args no args
	 */

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	/**
	 * Indicates start time, zone and Java version
	 * @param args no args
	 * @throws Exception UnsupportedOperationException
	 */
	@Override
	public void run(String... args) throws Exception {
		final LocalDateTime localDateTime = LocalDateTime.now();
		final ZonedDateTime zonedDateTime = ZonedDateTime.now(ZonedDateTime.now().getZone());
		final String javaVersion = System.getProperty("java.version");
		final String version = environment.getProperty("spring.boot.version");

		if (Application.logger.isInfoEnabled() ) {
			Application.logger.info("Application startup at {}, zone {}, running java {}, and Spring Boot {}",
			localDateTime, zonedDateTime, javaVersion, version);
		}

	}
}
