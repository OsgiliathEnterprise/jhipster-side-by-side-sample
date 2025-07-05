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
