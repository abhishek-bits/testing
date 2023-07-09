package mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


// To avoid the use of deprecated
// MockitoAnnotations.initMocks(this)
// to initialize our mocks, we make use of
// the Junit5 annotation @ExtendWith
@ExtendWith(MockitoExtension.class)
public class MockitoTestV2 {

    @Mock
    List<String> mockList;

    // The initialization of a spy object should be specific
    @Spy
    List<String> spyList = new ArrayList<>(); // or LinkedList

    @Mock
    MyMock myMock;

    // Used to capture method argument values for further assertions
    @Captor
    ArgumentCaptor<String> captor;

    @Test
    void mock_list_returns_correct_element() {
//        MockitoAnnotations.initMocks(this);
        when(mockList.get(0)).thenReturn("Test");
        assertEquals("Test", mockList.get(0));
    }

    @Test
    void spy_list_should_add_element() {
        spyList.add("Test");

        // When we need not bother about the
        // parameters passed to the method argument
        // We can use mockito's argument matchers
        verify(spyList).add(anyString());

        assertEquals(1, spyList.size());
    }

    @Test
    void spy_list_adds_element_to_it_correctly() {
        spyList.add("Test");

        // verifies that an invocation
        // of method add() has happened
        // with exactly the same argument.
        verify(spyList).add("Test");

        // In case of a spy, the element will be actually added
        // However, in case of mock, the test will fail.
        assertEquals(1, spyList.size());
    }

    @Test
    void spy_list_adds_the_correct_element_to_it() {
        // on invoking the add() method,
        // captor already captured the given argument
        spyList.add("dummy");
        verify(spyList).add(captor.capture());
        assertEquals("dummy", captor.getValue());
    }

    @Test
    void mock_list_calls_correct_method_exactly_2_times() {
        mockList.add("first");
        mockList.add("second");
        // We can verify the # times,
        // a particular method is expected to be invoked.
        verify(mockList, times(2)).add(any());
    }

    @Test
    void mock_list_calls_correct_method_at_least_2_times() {
        mockList.add("first");
        mockList.add("second");
        verify(mockList, atLeast(2)).add(any());
    }

    @Test
    void mock_list_calls_correct_method_at_most_2_times() {
        verify(mockList, atMost(2)).add(any());
    }

    @Test
    void mock_list_calls_correct_method() {
        // We can verify that a particular method
        // is never expected to be called.
        verify(mockList, never()).add(any());
    }

    @Test
    void verify_no_interactions_with_mockList() {
        // We can verify that no interactions
        // were made with the mocked object.
        verifyNoInteractions(mockList);
    }

    @Test
    void verify_noMoreInteractions_after_add_method() {
        mockList.add("test");
//        mockList.size();

        // Verify that add method was called
        verify(mockList).add(any());
        // Verify that after the add method was invoked
        // no other interactions have happened on the mocked object
        verifyNoMoreInteractions(mockList);
    }

    @Test
    void verify_methods_called_in_specified_order() {
        mockList.add("test");
        mockList.get(0);
        mockList.size();
        InOrder inOrder = inOrder(mockList);
        inOrder.verify(mockList).add(anyString());
        inOrder.verify(mockList).get(0);
        inOrder.verify(mockList).size();
    }

    @Test
    void verify_add_method_is_called_with_doNothing() {
        // We may want to ignore an exception to be thrown
        // during our unit-test
        doNothing().when(spyList).add(anyInt(), anyString());
        spyList.add(5, "test");
        verify(spyList).add(5, "test");

        // However, we don't require such an
        // ignorance in case of mocked object
        mockList.add(5, "test");
        verify(mockList).add(5, "test");
    }

    @Test
    void verify_add_method_is_called_with_correct_argument() {
        // An important usage of doNothing()
        // is to capture arguments
        doNothing().when(mockList).add(anyInt(), captor.capture());
        mockList.add(0, "test");
        assertEquals("test", captor.getValue());
    }

    @Test
    void assert_throws_npe_when_provided_with_null() {
        // similar to .thenThrow(), doThrow() is
        // reserved only for void methods.
        doThrow(NullPointerException.class)
                .when(mockList).add(anyInt(), isNull());
        assertThrows(
                NullPointerException.class,
                () -> mockList.add(5, null));
    }

    @Test
    void assert_arguments_passed_to_method_are_correct() {
        doAnswer(method -> {
            Object arg0 = method.getArgument(0);
            Object arg1 = method.getArgument(1);
            assertEquals(100, arg0);
            assertEquals("test", arg1);
            return null;
        }).when(mockList).add(anyInt(), anyString());

        mockList.add(100, "test");
    }

    @Test
    public void assert_real_method_is_called() {
        doCallRealMethod().when(myMock).someMethod(anyString());
        myMock.someMethod("cats");
        verify(myMock).someMethod("cats");
    }
}

class MyMock {
    public void someMethod(String animal) {
        System.out.println("I love " + animal + "!");
    }
}
