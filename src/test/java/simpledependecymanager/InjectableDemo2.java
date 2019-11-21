package simpledependecymanager;


public class InjectableDemo2 extends Injectable {

    private InjectableDemo injectableDemo;

    public InjectableDemo2(DependencyManagerTyped dependencyManager) {
        super(dependencyManager);
        injectableDemo = dependencyManager.get(InjectableDemo.class);
    }

    public InjectableDemo getInjectableDemo() {
        return injectableDemo;
    }
}