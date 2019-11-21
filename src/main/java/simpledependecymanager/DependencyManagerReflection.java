package simpledependecymanager;

/*
 * Copyright 2019 Gustavo Augusto Hennig
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Hashtable;

import javax.inject.Inject;

/**
 * Rudimentary Dependency Injection using @Inject annotation and reflecion
 * <p>
 * Created for unit tests, it is not optimized for performance
 * <p>
 * Created by Gustavo Augusto Hennig on 2019-11-20.
 */
public class DependencyManagerReflection {

    private Hashtable<String, Object> instances = new Hashtable<>();
    private Hashtable<String, Object> instancesRules = new Hashtable<>();

    public void injectDependencies(Object instance) {

        if (instance == null)
            return;

        for (Field f : instance.getClass().getDeclaredFields()) {


            if (f.getAnnotation(Inject.class) != null) {
                try {
                    f.setAccessible(true);
                    if (f.get(instance) != null) {
                        continue;
                    }
                    f.set(instance, getInstance(f.getType()));
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            } else if (instancesRules.containsKey(f.getName() + "|" + f.getType().getName())) {
                try {
                    f.setAccessible(true);
                    f.set(instance, instancesRules.get(f.getName() + "|" + f.getType().getName()));
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }

        for (Method m : instance.getClass().getMethods()) {
            if (m.getParameterCount() == 1) {

                Class<?> parameterType = m.getParameterTypes()[0];

                if (m.getAnnotation(Inject.class) != null) {
                    try {
                        m.setAccessible(true);
                        m.invoke(instance, getInstance(parameterType));
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                } else if (instancesRules.containsKey(m.getName() + "|" + parameterType.getName())) {
                    try {
                        m.setAccessible(true);
                        m.invoke(instance, instancesRules.get(m.getName() + "|" + parameterType.getName()));
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Constructor selectNoArgConstructor(Class<?> type) {

        for (Constructor c : type.getDeclaredConstructors()) {
            if (c.getParameterCount() == 0) {
                return c;
            }
        }
        return null;

    }

    private Object getInstance(Class<?> type) {
        Object existingInstance = instances.get(type.getName());
        if (existingInstance == null) {
            Constructor selectNoArgConstructor = selectNoArgConstructor(type);
            if (selectNoArgConstructor != null) {
                try {
                    selectNoArgConstructor.setAccessible(true);
                    existingInstance = selectNoArgConstructor.newInstance();
                    instances.put(type.getName(), existingInstance);
                    injectDependencies(existingInstance);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }

        return existingInstance;
    }

    public synchronized <T> T get(Class<T> key) {
        return (T) getInstance(key);
    }

    public synchronized void set(Class classType, Object instance) {
        instances.put(classType.getName(), instance);
    }

    public synchronized void set(Object instance) {
        instances.put(instance.getClass().getName(), instance);
    }

    public void addRule(String atributeOrSetterName, Class<?> type, Object instance) {
        instancesRules.put(atributeOrSetterName + "|" + type.getName(), instance);
    }
}
