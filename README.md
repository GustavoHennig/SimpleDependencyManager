
### Simply Dependency Manager


This project contains two different implementations of Dependency Injection.

##### DependencyManagerTyped

It is performatic and typed, without the use of reflection.

Works passing the Manager to the constructor of every Business Login Service.


##### DependencyManagerReflection



It uses the well known `@Inject` annotation,
 the injector will try to create automatically on every attribute 
 with this annotation when the type has constructor without arguments.
 
 
#### Examples

There are examples of use in the unit tests.