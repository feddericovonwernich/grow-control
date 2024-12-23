package com.fg.grow_control;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@SpringBootTest(classes = GrowControlApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {BasicApplicationintegrationTest.Initializer.class})
public class  BasicApplicationintegrationTest {

	@LocalServerPort
	protected int port;

	protected TestRestTemplate restTemplate = new TestRestTemplate();

	// Will be shared between test methods
	private static final MySQLContainer MY_SQL_CONTAINER = (MySQLContainer) new MySQLContainer()
			.withReuse(true);

	@BeforeAll
	public static void beforeAll() {
		MY_SQL_CONTAINER.start();
	}

	static class Initializer
			implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
					"spring.datasource.url=" + MY_SQL_CONTAINER.getJdbcUrl(),
					"spring.datasource.username=" + MY_SQL_CONTAINER.getUsername(),
					"spring.datasource.password=" + MY_SQL_CONTAINER.getPassword(),
					"spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver",
					"spring.jpa.show-sql=true",
					"spring.jpa.hibernate.ddl-auto=update",
					"spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect",
					"assistant.enabled=false",
					"telegram.bot.enabled=false"
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}

	protected String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	//this method are here because is necessary in another tests
	protected String appendCurrentDateTime(String input) {
		// Definir el formato de la fecha y hora
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		String timestamp = LocalDateTime.now().format(formatter);

		// Concatenar el input con la fecha y hora
		return input + "_" + timestamp;
	}
}
