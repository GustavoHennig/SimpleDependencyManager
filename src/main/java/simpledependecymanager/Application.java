package simpledependecymanager;


/**
 * For Android APPs:
 *
 * Replace this by your Application class (anything that lives during the
 * application scope)
 */
public class Application {

    private static Application application = null;

    public static Application getInstance() {
        if (application == null)
            application = new Application();
        return application;
    }

}