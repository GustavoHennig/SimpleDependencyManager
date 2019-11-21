package simpledependecymanager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class DependencyManagerTypedTest {
    @Test
    public void testDependencyCreatedAndReused() {

        DependencyManagerTyped dependencyManager = new DependencyManagerTyped(null);

        InjectableDemo class1 = dependencyManager.get(InjectableDemo.class);
        InjectableDemo class2 = dependencyManager.get(InjectableDemo.class);

        assertNotNull("get should not return null", class1);
        assertEquals("get should return same instance", class1, class2);
    }

    @Test
    public void testWithDependencyCreatedAndReused() {

        DependencyManagerTyped dependencyManager1 = new DependencyManagerTyped(null);

        InjectableDemo class1 = dependencyManager1.get(InjectableDemo.class);

        DependencyManagerTyped dependencyManager2 = new DependencyManagerTyped(null);

        InjectableDemo class2 = dependencyManager2.get(InjectableDemo.class);

        assertNotNull("get should not return null", class1);

        assertNotEquals("differente dependency managers should return different instances", class1, class2);
    }

    @Test
    public void testPreCreatedInstance() {
        // This example is common in unit tests

        DependencyManagerTyped dependencyManager = new DependencyManagerTyped(null);

        // Simple creation
        InjectableDemo class1 = new InjectableDemo(dependencyManager);

        // Set
        dependencyManager.set(class1);

        // Get
        InjectableDemo class2 = dependencyManager.get(InjectableDemo.class);

        assertNotNull("get should not return null", class1);
        assertEquals("get should return same instance", class1, class2);
    }

    @Test
    public void testDependencyInjectionWithTwoLevelsPrecreating() {

        DependencyManagerTyped dependencyManager = new DependencyManagerTyped(null);

        InjectableDemo class1 = dependencyManager.get(InjectableDemo.class);
        InjectableDemo2 class2 = dependencyManager.get(InjectableDemo2.class);

        assertNotNull("get should not return null", class1);
        assertEquals("get should return same instance", class1, class2.getInjectableDemo());
    }

    @Test
    public void testDependencyInjectionWithTwoLevelsNoPrecreating() {

        DependencyManagerTyped dependencyManager = new DependencyManagerTyped(null);

        InjectableDemo2 class2 = dependencyManager.get(InjectableDemo2.class);

        assertNotNull("get should not return null", class2.getInjectableDemo());
    }

}
