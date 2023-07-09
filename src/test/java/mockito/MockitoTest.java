package mockito;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MockitoTest {

    @Test
    void mock_list_returns_correct_element() {
        List<String> mockList = mock(ArrayList.class);
        when(mockList.get(0)).thenReturn("Test");
        assertEquals("Test", mockList.get(0));
    }

    @Test
    void spy_list_adds_element_to_it_correctly() {
        List<String> spyList = spy(ArrayList.class);

        spyList.add("Test");

        // verifies that an invocation
        // of method add() has happened
        // with exactly the same argument.
        verify(spyList).add("Test");

        // In case of a spy, the element will be actually added
        // However, in case of mock, the test will fail.
        assertEquals(1, spyList.size());
    }


}
