package simpledependecymanager;

import javax.inject.Inject;

/**
 * Created by Gustavo Augusto Hennig on 21/11/2019.
 */
public class ServiceExample {

    /**
     * Example using @Inject annotation
     */
    @Inject
    private ServiceToInject serviceToInject;

    /**
     * Example using field (name + type) rule
     */
    private String injectByRule;

    /**
     * Example using method (name + type + 1 argument) rule
     */
    private StringBuilder injectByRuleUsingMethod;

    private Integer injectNotWorkWithoutNoArgsConstructor;

    public int callFoo() {
        return serviceToInject.foo();
    }

    /**
     * The method to receive the injection must have the expected name, type, and one (unique) parameter.
     *
     * @param value just an example
     */
    public void setInjectByRuleUsingMethod(StringBuilder value) {
        injectByRuleUsingMethod = value;
    }

    public String getInjectByRule() {
        return injectByRule;
    }

    public StringBuilder getInjectByRuleUsingMethod() {
        return injectByRuleUsingMethod;
    }

    public ServiceToInject getServiceToInject() {
        return serviceToInject;
    }

    public Integer getInjectNotWorkWithoutNoArgsConstructor() {
        return injectNotWorkWithoutNoArgsConstructor;
    }
}
