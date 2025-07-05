# Configuring the project after JDL generation

Reimport the project into your IDE (most of the IDE do not handle new facet on the fly)
Then select the Spring's prod profile in your IDE to have the correct configuration (by going to the App class.
Same for the applied maven profiles, you can select the prod profile in your IDE to have the correct configuration.

## IDE Configuration

- Docker compose to start the prod database
- Keycloak to start the prod authentication server
- Spring run
- Bruno to be able to test the backends API
- Npm start to start the frontend
- JS debug
- And more... feel free to add your own configuration
  See .run folder of this project for the run configuration.

## Takeways

At this point, a complete CRUD application is generated with few lines of code. These entities should keep being generated as you add new entities and fields to your JDL file.

## Next Steps

We'll now see how to combine custom code with generated code, and how to add custom business logic to your application without losing the generated code added value: keeping your code and dependencies forever (because maintained by the generator community) while focusing on what matters: delivering immersive experience to your customers.
