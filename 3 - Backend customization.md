# Backend customization

## Extension/Composition

To customize the backend, you can extend or compose the existing generated backend. This allows you to add new functionality or modify existing behavior without changing the original codebase and keep generated code value.

The goal here is to minimize the amount of modification to the generated code, and create your custom classes in a separate package. This way, you can regenerate the code without losing your customizations.

### How to extend the backend

Create a specific java package removing the `.gen` package suffix. For example, if the generated code is in `com.example.project.gen`, you can create a new package `com.example.project.custom`.
Update the Spring main Application runner class to scan the new package. For example, if your main class is `com.example.project.Application`, you can add the `@ComponentScan` annotation:

```java
@ComponentScan(basePackages = {
    "com.mycompany.sidebysidesample.gen",
    "com.mycompany.sidebysidesample",
})
```

Add this Main class to be ignored by regen by copying the path into .yo-resolve file:

```
com.mycompany.sidebysidesample.Application skip
```

Extends the current generated codebase with your own service, controllers, tests, etc. in the package you created. For example, see the `com/mycompany/sidebysidesample/service`. contribution.

### How to compose the backend

To compose the backend, you can create a new package and implement your own services, controllers, etc., without modifying the generated code. This is useful if you want to add new features or override existing functionality.
Just inject the generated services into your custom services and override the methods you want to change. For example, you can create a new service that extends the generated service and overrides the methods you want to change.
See EmployeeWithDepartementService in the `com/mycompany/sidebysidesample/service` package for an example of how to compose the backend.

### By Extension

You can extend the generated code by creating a new class that extends the generated class. For example, if you have a generated service `com.example.project.gen.MyService`, you can create a new class `com.example.project.custom.MyCustomService` that extends it. inorder to avoid injection issues, you can use the `@Primary` annotation to indicate that this is the primary bean to be used when injecting the service.
See EmployeeServiceExtension in the `com/mycompany/sidebysidesample/service` package for an example of how to extend the generated service.
