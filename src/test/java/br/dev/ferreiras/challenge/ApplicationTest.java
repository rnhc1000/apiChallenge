package br.dev.ferreiras.challenge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.*;
/*
@SpringBootTest(properties = "spring.main.web-application-type=reactive")

 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings()
class ApplicationTest {

    @InjectMocks
    private Application application;

    @Autowired
    private Environment environment;


    @Test
    @DisplayName("Checking java version and cores")
    void run() {
        assertThat(System.getProperty("java.version")).isEqualTo("17.0.12");
        assertThat(String.valueOf(Runtime.getRuntime().availableProcessors())).isEqualTo("4");
    }
}

