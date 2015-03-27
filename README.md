Introduction
JaQu stands for Java Query and allows to access databases using pure Java. The original JaQu was developed by Thomas Mueller and can be found at http://www.h2database.com/html/jaqu.html. This extension of JaQu is following a different path then intended by the original author, it extends the abilities of JaQu into an ORM tool allowing for pure java grammar and Object Relational Mapping. With JaQu ORM you can persist objects into the Database with One2One, One2Many and Many2Many relationships. You can lazy load or eager load your relationships and manage primary key relations.

JaQu ORM still allows for the former JaQu capabilities which together with the ORM gives the developer a single tool for all his database needs using an object oriented Java on the one hand based on a very SQL like grammar. Using Generics the framework helps the developer with building his queries.

In order to use JaQu ORM, post compilation is needed on Entity Objects. There are two ways to achieve this task, using ant or using the eclipse plugin provided. For runtime, only the jaqu.jar is needed.

Using eclipse
Use automatic update for eclipse to get the plugin from the site.

update site URL: http://jaqu-orm.googlecode.com/svn/trunk/plugin

Once you have the plugin installed you will find that it was unpacked into your plugins directory of eclipse. In that plugin directory, under lib you will find jaqu.jar and jaqu-ext.jar. You will need jaqu.jar for your run-time deployment of your code. You will need jaqu-ext.jar, only if you plan on using ant and not the plugin. For development however you will not need any of these jars as the plugin takes care of that for you.

Basically a project that includes JaQu entities needs to have a JaQu Nature. In order to add this nature all you need to do is stand on your project, press your context button (right mouse button) and select Add/Remove Jaqu Nature. This will also add the necessary jaqu library to your project.

To make things simpler to start, you can check out the JaquTest Project from this svn. Once you have this project you will see it does not compile as you will need to add Jaqu Nature to it. Once you add the Jaqu Nature the project will compile. Use the different tests as examples for what you can do with JaQu ORM.

JaquTest Project

You will have to define a database. For the tests you will need a database user and password and the appropriate drivers. See the JaquTest class for connection configuration. There are two classes where you will need to fix your connection settings; JaquTest and EntitySessionTests.

Issues can be posted here on googlecode. I also welcome suggestions and ideas for improvements.
