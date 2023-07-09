# Testing

Reference: [YouTube Tutorial](https://www.youtube.com/watch?v=a8QvABt1l4M&list=PLlsmxlJgn1HJHqKQW7kdkpzQlmWXQxkUs)

## Unit Testing

- Testing the individual units of our code, like **classes** and **methods**.
- Proving that if we pass these **specific values** into that **particular method**, the **actual result** should match with the **expected result**.
- Testing code as we built it.
- Having automated unit tests allows us to **easily validate** any change that we make.

### Goal

- The aim is to test **pieces** of code in **isolation**.
- We are trying to end up with hundreds of unit tests each are testing a **single small part** of the system, **independently** of the others.

### Process

<ol>
    <li>Write Normal Application Code</li>
    <li>Write automated unit-tests</li>
</ol>

## Test-Driven Development (TDD)

Unit Testing + More additional steps.

- Write tests that **attempt to instantiate** an object which **doesn't have a class** yet.
- Write tests that call a **method that doesn't exist**, and we will **run those tests**.

### Process

<ol>
    <li>First, write automated unit-tests</li>
    <li>Then write normal Application Code</li>
</ol>

With this approach, either our test fails or the code doesn't even compile.
But, this is What we Want!

- The **first time** we run our unit-test **we want it to fail**.
- Then we write the **minimum necessary code** just to pass that specific test.

### Benefits of TDD

- Allows for **early bug detection**, resulting in **better design decisions**.
- Allows writing **smaller code** having **single responsibility**.
- Gives us more **confidence while refactoring**.
- Forces **design-for-testability**, which means, the **code will be testable up-front**, because the tests were already written.

## Unit-Testing Framework: JUnit v5

Keyword:
- `assert`

Methods (void type):
- `assertEquals(expectedValue, actualValue);`
- `assertArrayEquals(expectedArray, actualArray);`
- `assertNotNull(actualObject);`
- `assertNull(actualObject);`
- `assertSame(expectedObject, actualObject);`
- `assertNotSame(expectedObject, actualObject);`
- `assertTrue(condition);`
- `assertFalse(condition);`

## Red-Green-Refactor Cycle

### Red: make the test fail

### Green: make the test pass

### Refactor: make the code right (provide logic)

## How many tests do we need?

That many do you need so you can **relax** and be **confident** that your code works.

**Every significant path**, whether that's an if-else, or switch, or a loop **should have a different test**.

## What if the code is more **complex** than that?

**Code coverage** is the answer!

## Naming conventions

Test class name should have `Test` appended after the actual class name

For method names, we can follow any of the two naming conventions:

### Camel-Casing format

```java
public class MyClassTest {
    @Test
    public void testMethodNameExtraInfoIfNeeded() {
        // ...
    }
}
```

### Underscores format

```java
public class MyClassTest {
    @Test
    public void test_methodName_extraInfoIfNeeded() {
        // ...
    }
}
```

## Triple A approach

<ol>
    <li>Arrange</li>
    <li>Act</li>
    <li>Assert</li>
</ol>

## `@BeforeEach` and `@AfterEach`

Usually, **multiple** test **methods** in the same unit-test class,
end up **sharing** a lot of **similarities** in their **arrange** part.

```java
public class BankAccountTest {
    @BeforeEach
    public void setUp() {
        bankAccount = new BankAccount(500.0);
    }

    @AfterEach
    public void tearDown() {

    }
}
```

The method:
- `setUp()` will be called before each test method.
- `tearDown()` will be called after each test method.

## `@BeforeAll` and `@AfterAll`

These annotation work at the class level as a whole.

```java
public class BankAccountTest {
    @BeforeAll
    public void setUpClass() {
        bankAccount = new BankAccount(500.0);
    }

    @AfterAll
    public void tearDownClass() {

    }
}
```

The method:
- `setUpClass()` will run only once and before any of the tests runs.
- `tearDownClass()` will run only once and after all the tests have run.

## Points to Remember

- Common `getters` and `setters` do not need any testing. Only logic driven code needs testing.
- `private` methods do not need any testing but all `public` methods should have multiple tests depending on their inputs.

## Mocking

### Mocking is a Code Smell!

- If we have to do **a lot of mocking** to create a proper unit-test, then maybe that code **doesn't need to be tested**.
- If we're tempted to do all the testing with **end-to-end tests**, then we should probably **rethink** the testing strategy.
    - This will lead to **inadequate test coverage** and a tightly-coupled code which will become **harder to maintain over time**.

### When use Mock object ?

- If the real object hasn't been written.
- If what we're calling needs a **human intervention** (UI).
- If that system is **slow or difficult to set up**.

### Why even Mock object ?

- return **expected** values and do it **quickly** and **repeatedly**.
- verify that the **way** we are **calling** these objects is **correct**.

### Example

Do not apply **un-deterministic** logic to the test methods.
We are not sure whether the `FacebookService` would even return the data that we want.
What if we get HTTP Errors or Server does not respond ?

```java
public class FacebookServiceTest {
    @Test
    public void test_action_on_facebook_profiles() {
        FacebookService facebookService = new FacebookService();
        List<Profile> profiles = facebookService.getProfiles();
        UnitUnderTest unit = new UnitUnderTest();
        boolean result = unit.performAction(profiles);
        assertTrue(result);
    }
}
```

We want to take it as a fact that this Facebook API **will just work** and
**will return** the desired results, allowing us to assert some behavior
**after** they are retrieved.

To do that we can create a new class called `MockFacebookService`. We now know **exactly** what the `getProfiles` call will return for us.

```java
public class FacebookServiceTest {
  @Test
  public void test_action_on_facebook_profiles() {
    FacebookService facebookService = new MockFacebookService();
    List<Profile> profiles = facebookService.getProfiles();
    UnitUnderTest unit = new UnitUnderTest();
    boolean result = unit.performAction(profiles);
    assertTrue(result);
  }
}
```

The **mock object** itself can **assert** that what was passed-in was **correct** and **fails** if it wasn't.

```java
public class FacebookServiceTest { 
  @Test
  public void test_action_on_facebook_profile() {
      FacebookService facebookService = new MockFacebookService();
      // assert delegated to the mock object itself.
      facebookService.expectProfile(123456);
      Profile profile = facebookService.getProfile(profileId);
      UnitUnderTest unit = new UnitUnderTest();
      boolean result = unit.performAction(Collections.singletonList(profile));
      assertTrue(result);
  }
}
```

## FAKES vs STUBS vs MOCKS

All belong to one family called **"Test Doubles"**.
- A Test double is any object used in a test.
- Other kinds of test doubles include:
    - dummy values
    - spies

### FAKES

- object that behave **"naturally"** but is **not "real"**.
- have a **pre-written** implementation of the object that they are **supposed** to **represent**.
    - they have the same method signatures that return pre-written (or arranged) responses.

**Purpose**:
- the purpose of a fake is not to affect the behavior of a system under test but rather to **simplify** the implementation of the test by **removing unnecessary** or heavy **dependencies**.

Example: Creating a Fake Database Service.

```java
public class DatabaseService {
    public Entity getEntityById(int id) {
        // go to the database and fetch the entity
        // return the entity  
    }
}
```

```java
public class FakeDatabaseService extends DatabaseService {
    // basically uses a map as pre-arranged data
    private Map<Integer, Entity> entities = new HashMap<>();
    
    @Override
    public Entity getEntityById(int id) {
        return entities.get(id);
    }
}
```

Now, the data we access, is actually fake, but makes the operation
- fast
- concise
- accurate
  for our unit tests.

### STUBS

- an **implementation** that behaves **"unnaturally"**.
- it is **preconfigured** to respond to **specific inputs** with **specific outputs**.

**Purpose**:
- the purpose of a stub is to get our unit under test into a specific state.

Example:
Code that interacts with a certain API, we could **stub that API** with another that always returns the same response.
This way, we can write tests that make assertions about how the system reacts to that particular state.

```java
public class FacebookService {
    public Profile getProfile(int profileId) throws Exception {
        // calls Facebook's API
        // retrieves the profile details
        // returns the profile object
    }
}
```

By **modifying** the **normal behavior** of our call, we can create a **stub** as shown:

```java
public class StubFacebookService extends FacebookService {
    
    @Override
    public Profile getProfile(int profileId) throws Exception {
        throw new ProfileNotFoundException();
    }
}
```

### MOCKS

- **similar to stubs**, but with **verification** added in.

**Purpose**:
- the purpose of a mock is to make **assertions** about how the unit under test **interacted** with the **dependency**.

```java
public enum FileType {
    XML, CSV, TXT
}
```

```java
public class UploadService {
    public boolean uploadFile(File file) {
        // tries to connect to the server, returns false if failed.
        // connection established
        // tries to upload file to the server
        // returns true if file successfully uploaded
    }
}
```

Modifying the `uploadFile` method to return the desired result only if the assertions pass.

```java
public class MockUploadService extends UploadService {
    
    @Override
    public boolean uploadFile(File file)  {
        assert null != file;
        assert FileType.XML.equals(file.getFileType());
        return true;
    }
}
```

## Mock Frameworks

Just like we have unit testing frameworks, there are several mock frameworks that we can use to help in the mocking process.

- **different** that testing frameworks (JUnit)
- make it **easier to define mock objects** and provide **structure** to our tests.

### Limitation

They introduce **some level of risk**, we may have mocked a service because the real one wasn't written yet,
which is later written in a different behavior than we expected, we may run into a problem.

### Available Mock Frameworks in Java

- JMock
- easy-mock
- **Mockito**: allows us to **dynamically generate mock objects** rather than explicitly writing classes for them.

## Code Coverage

- a **percentage** of how much of our **application code** is being **hit** by our **unit tests**.
- it's **not a guarantee** of perfection but will certainly **let us know** what we're missing.
- it's **not a replacement** for good code review and **good programming practices**.

### Code Coverage Report

- Which **classes** are being tested.
- How many **methods** are being tested.
- What **lines of code** are being hit by a test.

### Code Coverage Tool

Built-in IntelliJ Plugin: **Code Coverage for Java**.

### Code Coverage Types

#### Function Coverage

- How many **methods** (or functions) defined in our application have been called ?
- Every method should be called at least once in the test class no matter what arguments we pass to it.

```java
public class SomeClass {
    public void someMethod(int x, int y) {
        if(x < 0 && y < 0) {
            System.out.println("Adding Negative Numbers!");
        }
        System.out.println("Addition: " + (x + y));
    }
}
```

```java
public class SomeClassTest {
    
    @Test
    public void test_someMethod() {
        SomeClass someClass = new SomeClass();
        someClass.someMethod(any(), any());
    }
}
```

#### Statement Coverage

How many
- paths
- lines
- statements
  are covered ?

In our example, we will get 100% test coverage if we pass two negative numbers by our test method.

```java
public class SomeClassTest {

  // ...

  @Test
  public void test_someMethod_all_lines() {
      SomeClass someClass = new SomeClass();
      someClass.someMethod(anyNegativeNbr(), anyNegativeNbr());
  }
}
```

#### Branch Coverage

- Ensures that each **decision** from every branch is **executed at least once**.
- Measures fractions of **independent code segments** and **finds out sections** having no branches.

```java
public class SomeClassTest {

  // ...

  @Test
  public void test_someMethod_true_branch() {
    SomeClass someClass = new SomeClass();
    someClass.someMethod(anyNegativeNbr(), anyNegativeNbr());
  }

  @Test
  public void test_someMethod_false_branch() {
    SomeClass someClass = new SomeClass();
    someClass.someMethod(anyNegativeNbr(), anyPositiveNbr());
  }
}
```

#### Condition Coverage

Makes sure that **each boolean sub-expression** was evaluated to **both true and false**.

```java
public class SomeClassTest {

  // ...

  // From this test,
  // the first condition (x < 0) is evaluated to true
  // the second condition (y < 0) is evaluated to false
  @Test
  public void test_someMethod_true_false() {
    SomeClass someClass = new SomeClass();
    someClass.someMethod(anyNegativeNbr(), anyPositiveNbr());
  }

  // From this test,
  // the first condition (x < 0) is evaluated to false
  // the second condition (y < 0) is evaluated to true
  @Test
  public void test_someMethod_false_branch() {
    SomeClass someClass = new SomeClass();
    someClass.someMethod(anyPositiveNbr(), anyNegativeNbr());
  }
}
```

### Code Coverage Policies

- Must **not decrease** throughout the lifecycle of the project.
    - Code coverage **ratio** should always **go up** as the application code scales.
- Test files **should have owners** and **logic changes** should be approved.

## Mockito

It is an **open-source test automation framework** that internally uses Java Reflection's API to **create mock objects**.

**Purpose**: to **allow our tests to focus on a single unit** by mocking the external dependencies of this unit.

Mockito also allows us to manipulate behaviors in a way that makes them **throw exceptions**.

**When?**
Mocking should be used when the real object has a **non-deterministic** behavior or
is a **callback function** or is **yet to be implemented**.

### `@Mock` (or `mock()`)

- Use `@Mock` annotation to declare the properties to be mocked.
- Use `@ExtendWith` annotation over the class to initialize the listed mocks. 

```java
@ExtendWith(MockitoExtension.class)
public class MockitoTestV2 {
    @Mock
    List<String> mockList;
    
    // More properties to mock ...
    
    // Test methods follow ...
}
```

### Argument Matchers

Based on argument type, we can use:
- `eq()`
- `any()` (also stores `null`)
- `anyInt()`
- `anyString()`
- `anyList()`, etc. 

**NOTE**: Argument matchers
- cannot be used as **return** values.
- cannot be used outside of **verification** or **stubbing**.
- cannot be used alongside **exact (or raw) values**.

Example: Below code snippet is invalid.

```java
public class Test {
    @Test
    void argument_matchers_with_exact_values() {
        mockList.add(5, "test");

        // INVALID
        verify(mockList).add(5, anyString());
        
        // VALID
        verify(mockList).add(eq(5), anyString());
    }
}
```

### Implementing our own Argument Matcher

Ref: [Baeldung: Custom Argument Matcher](https://www.baeldung.com/mockito-argument-matchers#custom-argument-matcher)

Sometimes, we may need to write our own custom argument matcher.
This will help to validate the data inside the passed object.
Thereby, allowing us more control over the unit test we create.
In such a case, we'll use `argThat()` instead of simple `any()`.

Limitation of `any()` is that it only declares the **type** of the object to be matched. Example: `any(Message.class)`

Example Scenario:
`MesageController` takes as argument `MessageDTO` that is passed on to `MessageService` that'll deliver the `Message`.
Now, we want to verify that we called `MessageService` exactly once with the expected `Message`.

```java
public class MessageControllerTest {
    @Test
    void test_messageService_deliverMessage_called_exactly_once() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setFrom("me");
        messageDTO.setTo("you");
        messageDTO.setText("Hello, You!");
        
        messageController.createMessage(messageDTO);
        
        verify(messageService, times(1)).deliverMessage(any(Message.class));
    }
}
```

Since, the message is **constructed inside the method under test** (i.e. within `createMessage`), we must use `any` as the matcher.
But, this approach **doesn't let us validate the data inside the `Message`**.
For this reason, we should implement a custom argument matcher.

- Such a class is kept only within the **test directory**.
- We need to implement the `ArgumentMatcher` interface as shown below.

```java
public class MessageMatcher implements ArgumentMatcher<Message> {
    
    private Message left;
    
    // constructors
  
    @Override
    public boolean matches(Message right) {
        return left.getFrom().equals(right.getFrom())
                && left.getTo().equals(right.getTo())
                && left.getText().equals(right.getText())
                && right.getDate() != null
                && right.getId() != null;
    }
}
```

To use our custom argument matcher, we need to modify our test and replace `any` by `argThat`:

```java
public class MessageControllerTest {
  @Test
  void test_messageService_deliverMessage_called_exactly_once() {
    MessageDTO messageDTO = new MessageDTO();
    messageDTO.setFrom("me");
    messageDTO.setTo("you");
    messageDTO.setText("Hello, You!");

    messageController.createMessage(messageDTO);

    Message message = new Message();
    message.setFrom("me");
    message.setTo("you");
    message.setText("Hello, You!");
    
    verify(messageService, times(1))
            .deliverMessage(
                    argThat(new MessageMatcher(message)));
  }
}
```

### Custom Argument Matchers vs. Argument Captors

Both techniques can be used to **make sure certain arguments are passed** to mocks.

Argument Captors may be a better fit if:
- **we need to assert on argument values**, or
- our custom argument matcher isn't likely to be reused.

Custom Argument Matchers are usually:
- **better for stubbing**,
- will allow us to **select the best possible approach** for a given scenario, and
- produce high quality tests that are clean and maintainable.

### `@Spy` (or `spy()`)

- a spy is used to create a **real object** and spy on it.
- allows particular objects **behave normally**.
- allows us to **mock certain behaviors** and let others behave normally.

In Mockito Framework, it is a **partial mock**, 
when we are not stubbing a method, 
then the real behavior will be called.

```java
@ExtendWith(MockitoExtension.class)
public class MockitoTestV2 {
  // ...

  // The initialization of a spy object should be specific
  @Spy
  List<String> spyList = new ArrayList<>(); // or LinkedLis
  
  // ...
  
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
}
```

### `@Captor`

Captor is mainly used when the passed argument is a **complex object**, and **deep** assertions need to be done on it.

```java
public class MockTestV2 {
  @Test
  void spy_list_adds_the_correct_element_to_it() {
    // on invoking the add() method,
    // captor already captured the given argument
    spyList.add("dummy");
    verify(spyList).add(captor.capture());
    assertEquals("dummy", captor.getValue());
  }
}
```

### `@InjectMocks`

To inject the mocks to the target service class under test.

### `thenReturn()` vs. `thenAnswer()` vs. `thenThrow()`

| Method         | Usage                                                                                                                                                                |
|----------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `thenReturn()` | when we **know and want a specific return value** at the time we mock a method call.                                                                                 |
| `thenAnswer()` | when we **need to additionally actions when a mocked method is invoked**, e.g. when we need to compute the return value based on the parameters of this method call. |
| `thenThrow()`  | when we know the **expected error** a service method call may throw like HTTP 5** error.                                                                             |

### `thenAnswer()` Example

```java
public class MyServiceTest {

  @Mock
  MyConverter converter;

  @InjectMocks
  MyService service;
  
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
```

### More uses of `verify()`

| Operation                                                            | Usage                                                                                                      |
|----------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------|
| `verify(mockList, times(2)).add(any());`                             | Verifies that the `add` method has been invoked on the `mockList` object exactly 2 times.                  |
| `verify(mockList, atLeast(2)).add(any());`                           | Verify that the `add` method has been invoked on the `mockList` object at least 2 times.                   |
| `verify(mockList, atMost(2)).add(any());`                            | Verify that the `add` method has been invoked on the `mockList` object at most 2 times.                    |
| `verify(mockList, never()).add(any());`                              | Verify that the `add` method has never been invoked on the `mockList` object.                              |
| `verifyNoInteractions(mockList);`                                    | Verify that no interactions have been made on the `mockList` object.                                       |
| `verify(mockList).add(any());` `verifyNoMoreInteractions(mockList);` | Verify that after the `add` method was invoked, no more interactions were made with the `mockList` object. |

#### `Inorder`

Mockito's Inorder class is used to verify the order of interactions (methods invoked in that order) with the mocked object.

### Special functions reserved for `void` methods

| Method               | Usage                                                                                                                                              |
|----------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| `doNothing()`        | Used to either ignore the effect of a **void** method, or to ignore an expected exception (useful in case of spy objects), or to capture arguments |
| `doThrow()`          | Used to explicitly throw the required exception, similar to `.thenThrow()` but specially reserved for `void` methods.                              |
| `doAnswer()`         | Used to perform complex logic whenever a **void** method is invoked.                                                                               |                                                                                 
| `doCallRealMethod()` | Used to execute real-code within a method for any given **mocked object** (which would otherwise run for any **spy** object).                      |

