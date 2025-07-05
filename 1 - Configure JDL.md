# Configuring a Side by side ready JDL


## Prerequisites
Be sure to have the following prerequisites installed:
- [Node.js](https://nodejs.org/en/download/)
- Java 21
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Git](https://git-scm.com/downloads)
- Jhipster installed globally
  ```bash
  npm install -g generator-jhipster
  ```

## Procedure

1. create a new directory for your JDL project
   ```bash
   mkdir jdl
   cd jdl
   ```
2. create a new JDL file
   ```bash
    touch app.jdl
    ```
3. open the JDL file in your favorite text editor and add your JDL content
4. Select at least the following options to make it compatible with side by side:
```jdl
application {
  config {
      packageName com.mycompany.sidebysidesample.gen // note the suffix of the package name

  }
  }
service * with serviceImpl
clientRootFolder * with gen
dto Country with mapstruct // Any Entity where you only want to have CRUD operations on it
```
5. generate the JDL project
   ```bash
   jhipster import-jdl jdl/app.jdl
   ```
