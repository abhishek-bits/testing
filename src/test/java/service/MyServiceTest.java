package service;

import com.sun.javaws.exceptions.InvalidArgumentException;
import converter.MyConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import repository.MyRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MyServiceTest {

    @Mock
    private MyRepository repository;

    @Mock
    private MyConverter converter;

    // To inject the mocks we just created
    // into the instance of the service,
    // we use @InjectMocks annotation
    @InjectMocks
    private MyService service;

    // Example of thenReturn()
    @Test
    void myService_calls_myConverter_correctly() {
        Object object = new Object();
        when(repository.getById(any())).thenReturn(object);
        service.getAsJsonString(UUID.randomUUID());
        verify(converter).toJson(object);
    }

    // Example of thenThrow()
    @Test
    void myService_throws_Exception() {
        when(converter.toJson(any())).thenThrow(new IllegalArgumentException());
        assertThrows(
                IllegalArgumentException.class,
                () -> service.getAsJsonString(UUID.randomUUID()));
    }

    // Example of thenAnswer()
    @Test
    public void test_thenAnswer() {
        String jsonResponse = "{key1: value1, key2: value2}";
        Answer<String> answer = invocationOnMock -> {
            // Grab the argument
            Object object = invocationOnMock.getArgument(0);
            // Do something with the argument
            // ...
            // Prepare the response
            return jsonResponse;
        };
        when(converter.toJson(any())).thenAnswer(answer);
        assertEquals(jsonResponse, service.getAsJsonString(UUID.randomUUID()));
    }
}
