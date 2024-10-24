package br.dev.ferreiras.challenge;

import br.dev.ferreiras.challenge.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ApplicationTest {

    @InjectMocks
    private Application application;

    @Mock
    private UserService userService;

    @Test
    void run() {
        when(this.userService.checkSystem()).thenReturn(Collections.singletonList("17"));
        assertEquals("17.0.12", System.getProperty("java.version"));
        assertEquals(4, Runtime.getRuntime().availableProcessors());
    }


}

