![Header](https://repository-images.githubusercontent.com/269539676/88c5b100-c869-11ea-80a5-85c6002d5ca2)

## CraftLib
The entire Minecraft codebase as a library, with protocol, NBT, chat and schematic file support.

[![Issues](https://img.shields.io/github/issues/Zerite/CraftLib?style=flat-square&label=Issues&logo=github&logoColor=white)](https://github.com/Zerite/CraftLib/issues)
[![License](https://img.shields.io/github/license/Zerite/CraftLib?style=flat-square&label=License&logo=read-the-docs&logoColor=white)](https://github.com/Zerite/CraftLib/blob/master/LICENSE)
![Latest Version](https://img.shields.io/maven-central/v/dev.zerite.craftlib/craftlib-protocol?color=%2342a5f5&label=Latest%20Version&logo=gradle&style=flat-square&logoColor=white)
![Support](https://img.shields.io/badge/Support-1.7.x%20--%201.8.x-yellow?style=flat-square&logo=minetest&logoColor=white)

#### Contents
1. [What IS CraftLib?](#1-what-is-craftlib)
2. [Using it](#2-using-it)
3. [Special Thanks](#3-special-thanks)

#### 1. What IS CraftLib?
CraftLib is a simple Kotlin library which aims to *completely recreate* many of
the essential concepts of Minecraft.
This includes **protocol, NBT, chat, localization, schematics** and more to come!

This library includes full support for Kotlin's features out of the box with
additional functions being introduced to ensure Java interoperability is simple
and straightforward.

#### 2. Using it.
To include CraftLib into your project, add the Maven Central repository to your project.
Then add the appropriate dependency notation for your build system to include our package.

**Note:** The wiki is currently a *WIP*. This file will be updated accordingly once it is
sufficiently complete.

**Maven**
```xml
<dependency>
    <groupId>dev.zerite.craftlib</groupId>
    <artifactId>craftlib-protocol</artifactId>
    <version>0.1.4</version>
</dependency>
```

**Gradle**
```groovy
implementation 'dev.zerite.craftlib:craftlib-protocol:0.1.4'
```

#### 3. Special Thanks
##### MCDevs
[![Wiki.vg](https://wiki.vg/images/c/cf/120px-Logotemp.png)](https://wiki.vg/Main_Page)

A **HUGE** thank you to the team at #mcdevs (Freenode IRC) and their [wiki](https://wiki.vg/Main_Page).
Without their work documenting the Minecraft codebase, this project wouldn't be possible.
Some comments from their wiki pages (particularly [protocol](https://wiki.vg/Protocol)) have been used 
throughout the codebase and all rights to those texts belong to their team.

##### YourKit
[![YourKit Java Profiler](https://www.yourkit.com/images/yklogo.png)](https://www.yourkit.com)

YourKit supports open source projects with innovative and intelligent tools
for monitoring and profiling Java and .NET applications.
YourKit is the creator of [YourKit Java Profiler](https://www.yourkit.com/java/profiler/),
[YourKit .NET Profiler](https://www.yourkit.com/.net/profiler/),
and [YourKit YouMonitor](https://www.yourkit.com/youmonitor/).
