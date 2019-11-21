package simpledependecymanager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class DependencyManagerReflectionTest {
    @Test
    public void testDependencyCreatedAndReused() {

        DependencyManagerReflection dependencyManager = new DependencyManagerReflection();

        ServiceExample class1 = dependencyManager.get(ServiceExample.class);
        ServiceExample class2 = dependencyManager.get(ServiceExample.class);

        assertNotNull("get should not return null", class1);
        assertSame("get should return same instance", class1, class2);
        assertEquals("must call the method", 2, class1.callFoo());
        assertNull("no rule configured", class1.getInjectByRule());
        assertNull("no rule configured", class1.getInjectByRuleUsingMethod());
    }

    @Test
    public void testWithDependencyCreatedAndReused() {

        DependencyManagerReflection dependencyManager1 = new DependencyManagerReflection();

        ServiceExample class1 = dependencyManager1.get(ServiceExample.class);

        DependencyManagerReflection dependencyManager2 = new DependencyManagerReflection();

        ServiceExample class2 = dependencyManager2.get(ServiceExample.class);

        assertNotNull("get should not return null", class1);
        assertNotSame("different dependency managers should return different instances", class1, class2);
        assertEquals("must call the method", 2, class2.callFoo());
        assertNull("no rule configured", class1.getInjectByRule());
        assertNull("no rule configured", class1.getInjectByRuleUsingMethod());

    }

    @Test
    public void testPreCreatedInstance() {
        // This example is common in unit tests

        DependencyManagerReflection dependencyManager = new DependencyManagerReflection();

        // Simple creation
        ServiceExample class1 = new ServiceExample();

        // Set existing instance to reuse
        dependencyManager.set(class1);

        // Get
        ServiceExample class2 = dependencyManager.get(ServiceExample.class);

        assertNotNull("get should not return null", class1);
        assertSame("get should return same instance", class1, class2);
    }

    @Test
    public void testDependencyInjectionWithTwoLevelsPrecreating() {

        DependencyManagerReflection dependencyManager = new DependencyManagerReflection();

        ServiceExample class1 = dependencyManager.get(ServiceExample.class);
        ServiceToInject class2 = dependencyManager.get(ServiceToInject.class);

        assertNotNull("get should not return null", class1);
        assertEquals("get should return same instance", class2, class1.getServiceToInject());
    }

    @Test
    public void testDependencyInjectionUsingRuleByFieldName() {

        DependencyManagerReflection dependencyManager = new DependencyManagerReflection();

        String existingInstance = "existingInstance";

        dependencyManager.addRule("injectByRule", String.class, existingInstance);

        ServiceExample class1 = dependencyManager.get(ServiceExample.class);

        assertSame("must be same instance", existingInstance, class1.getInjectByRule());
        assertNull("no rule configured", class1.getInjectByRuleUsingMethod());
    }

    @Test
    public void testDependencyInjectionUsingRuleByMethodName() {

        DependencyManagerReflection dependencyManager = new DependencyManagerReflection();

        StringBuilder existingInstance = new StringBuilder("existingInstance");

        dependencyManager.addRule("setInjectByRuleUsingMethod", StringBuilder.class, existingInstance);

        ServiceExample class1 = dependencyManager.get(ServiceExample.class);

        assertSame("must be same instance", existingInstance, class1.getInjectByRuleUsingMethod());
        assertNull("no rule configured", class1.getInjectByRule());
    }

    @Test
    public void testDependencyInjectionWithoutNoArgsConstructor() {

        DependencyManagerReflection dependencyManager = new DependencyManagerReflection();

        dependencyManager.addRule("injectNotWorkWithoutNoArgsConstructor", StringBuilder.class, 1);

        ServiceExample class1 = dependencyManager.get(ServiceExample.class);

        assertNull("Inject Should Not Work Without No Args Constructor", class1.getInjectNotWorkWithoutNoArgsConstructor());
    }
}
