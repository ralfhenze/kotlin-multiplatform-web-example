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


## Author

* Ralf Henze
