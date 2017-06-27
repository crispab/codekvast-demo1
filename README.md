# codekvast-spring-heroku
Sample of using Codekvast on a Spring boot based app running in Heroku.

It illustrates a number of ways for building a Spring Boot app with
Codekvast agent attached.

Each method lives in a separate branch.

All Java code is the same in all branches. The only things that differ are the build scripts.

Branches:

* `gradle-application` - Uses the standard Gradle `application` plugin, with support for Spring Boot executable jar disabled.
* `gradle-executable-jar` - Uses the Gradle `spring-boot-gradle-plugin` for building a Spring Boot-style executable jar file.
* `maven-executable-jar` - Uses the Maven `xxx` plugin for building a Spring Boot-style executable jar file.

