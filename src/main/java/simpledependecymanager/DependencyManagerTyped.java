package simpledependecymanager;


/*
 * Copyright 2018 Gustavo Augusto Hennig
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

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

/**
 * Created by Gustavo Augusto Hennig on 2018-08-13.
 * <p>
 * A simplistic form of dependency injection, with satisfactory performance.
 * <p>
 * Instances last for the duration of the DependencyManagerTyped
 * <p>
 * In an Android application, there may be an instance of DependencyManagerTyped per activity.
 */
public class DependencyManagerTyped {

    /**
     * Example of a common instance that do not inherit Injectable
     */
    private Application appController;

    private Hashtable<String, Object> instances = new Hashtable<>();

    public DependencyManagerTyped() {

    }

    /**
     * This is optional
     *
     * @param appController
     */
    public DependencyManagerTyped(Application appController) {
        this.appController = appController;
    }

    /**
     * This is how we share an instance across all services (BL) that do not implement Injectable
     *
     * @return
     */
    public synchronized Application getApplication() {
        if (appController == null) {
            appController = Application.getInstance();
        }
        return appController;
    }

    /**
     * Get an instance (create new if it doesn't exist)
     * <p>
     * Must extend Injectable to prevent runtime errors
     *
     * @param key
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public synchronized <T extends Injectable> T get(Class<T> key) {

        Object o = instances.get(key.getName());

        if (o == null) {
            try {
                o = key.getDeclaredConstructor(DependencyManagerTyped.class).newInstance(this);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e) {
                e.printStackTrace();
                // TODO: Handle it
                return null;
            }
            instances.put(key.getName(), o);
        }

        return (T) o;
    }

    public synchronized void set(Class classType, Injectable instance) {
        instances.put(classType.getName(), instance);
    }

    public synchronized void set(Injectable instance) {
        if (instance.getClass().getName().contains("mock")) {
            System.out.println("Mock may not be what you want to set, use Class.class instead.");
        }
        instances.put(instance.getClass().getName(), instance);
    }
}
