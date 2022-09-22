# Kotlin Multiplatform Web Example

How can we share data structures and validation code between a Kotlin/JVM backend and a TypeScript
frontend? By creating a shared library with [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
and making use of Kotlin-to-JavaScript-compilation.

This repository demonstrates this approach by implementing a simple todo list app. It is made of
three Gradle submodules:

| Module               | Description                                                                                         |
|----------------------|-----------------------------------------------------------------------------------------------------|
| [backend](backend)   | Provides an API for the frontend, built with Kotlin and [Javalin](https://javalin.io/)              |
| [frontend](frontend) | Provides the user interface, built with plain TypeScript (no framework used, to keep things simple) |
| [shared](shared)     | Data structures and validation code that is shared between backend and frontend                     |


## System Requirements

* a Java runtime environment (JRE) for Gradle
* `npm` for the frontend


## Build & Run

### Backend

To build and run the backend, do this:
```sh
$ ./gradlew :backend:run
```
Use `gradlew.bat` if you are on Windows.

The backend is now accessible at [http://localhost:7070/](http://localhost:7070/). The OpenAPI
Swagger UI can be accessed at [http://localhost:7070/api/swagger-ui/](http://localhost:7070/api/swagger-ui/).

Be aware that the tasks will just be held in memory and won't be persisted! As soon as you stop
the backend by hitting Ctrl+C, all created tasks will be gone!


### Frontend

To be able to build the frontend, you first need to build the `shared` Kotlin Multiplatform library:
```sh
$ ./gradlew :shared:assemble
```
The built JavaScript and TypeScript code can now be found in `/build/js/packages/shared/kotlin`.
This will be used by npm in the next step:
```sh
$ cd frontend
frontend $ npm install
frontend $ npm run build
```

The executables can now be found in `/frontend/dist`. Just open the `index.html` file in your
browser, and you are ready to go.


### Tests

There are some backend integration tests. If you have a backend up-and-running, you first need to
stop it. Now you can run the backend tests:
```sh
$ ./gradlew :backend:test
```

The shared library also has a few unit tests:
```sh
$ ./gradlew :shared:allTests
```


## Author

* Ralf Henze
