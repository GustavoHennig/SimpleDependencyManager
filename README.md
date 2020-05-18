
### Simply Dependency Manager


This project contains two different implementations of Dependency Injection. 



##### DependencyManagerTyped

This implementation is more like a service locator. It is more performative because it is typed, without the use of reflection.

Works passing the `Manager` to the constructor of every Business Logic Service.

Example:
https://github.com/GustavoHennig/SimpleDependencyManager/blob/master/src/test/java/simpledependecymanager/DependencyManagerTypedTest.java

I found a post that explains the concept better:
https://proandroiddev.com/why-service-locator-is-so-unpopular-bbe8678be72c


##### DependencyManagerReflection


It uses the well known `@Inject` annotation,
 the injector will try to create automatically on every attribute 
 with this annotation when the type has constructor without arguments.
 
This solution was created for simple integration testes, but can be used in large project.
 
 
#### Examples

Examples of how to use (Reflection version).


```java
public class ServiceExample {

    @Inject
    private ServiceToInject serviceToInject;
    private String injectByRule;
}
```


```java
public class ServiceToInject {
    public int foo() {
        return 1 + 1;
    }
}
```

Creates the instances automatically

```java
public class DependencyManagerReflectionTest {
    @Test
    public void test() {
        DependencyManagerReflection dependencyManager = new DependencyManagerReflection();

        // Configure an injection rule exception
        String existingInstance = "existingInstance";
        dependencyManager.addRule("injectByRule", String.class, existingInstance);
        
        ServiceExample serviceExample = dependencyManager.get(ServiceExample.class);
        
        assertEquals("must call the method of injected class", 2, serviceExample.callFoo());
        assertSame("must be same instance", existingInstance, serviceExample.getInjectByRule());
    }    
}
```


Please, see the unit tests of this project for more examples:

https://github.com/GustavoHennig/SimpleDependencyManager/tree/master/src/test/java/simpledependecymanager

