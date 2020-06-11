## CraftLib
Minecraft, as a dependency.

[![Issues](https://img.shields.io/github/issues/Zerite/CraftLib?style=flat-square&label=Issues)](https://github.com/Zerite/CraftLib/issues)
[![License](https://img.shields.io/github/license/Zerite/CraftLib?style=flat-square&label=License)](https://github.com/Zerite/CraftLib/blob/master/LICENSE)
[![Latest Stable](https://img.shields.io/github/v/release/Zerite/CraftLib?style=flat-square&label=Latest%20Stable)](https://github.com/Zerite/CraftLib/releases)

#### Contents
1. [What IS CraftLib?](#1-what-is-craftlib)
2. [Using it](#2-using-it)
3. [Special Thanks](#3-special-thanks)

#### 1. What IS CraftLib?
CraftLib is a simple Kotlin library which aims to *completely recreate* many of
the essential concepts of Minecraft.
This includes **the full REST API, protocol, NBT, chat, localization** and more to come!

#### 2. Using it.
To include CraftLib into your project, add our **[GitHub Registry](https://github.com/Zerite/CraftLib/packages)** to your project.
Upon a release being published, we will automatically build and publish the version
to our package registry. 

**Only these** are stable, and all other releases should be considered **beta software**.

##### Package Registry
[View Packages](https://github.com/Zerite/CraftLib/packages)

```xml
<repository>
    <id>craftlib</id>
    <name>CraftLib Package Registry</name>
    <url>https://maven.pkg.github.com/Zerite/CraftLib</url>
</repository>
```

##### Beta Releases
We release beta versions automatically through our CI system, and they are
all suffixed with `-beta`.
They are versioned by the latest short commit hash on the master branch.

**Please note** these are unstable and should not be used in production.

**Example**
```xml
<dependency>
    <groupId>dev.zerite.craftlib</groupId>
    <artifactId>craftlib-protocol-beta</artifactId>
    <version>a4bca49</version>
</dependency>
```

#### 3. Special Thanks
[![YourKit Java Profiler](https://www.yourkit.com/images/yklogo.png)](https://www.yourkit.com)

YourKit supports open source projects with innovative and intelligent tools
for monitoring and profiling Java and .NET applications.
YourKit is the creator of [YourKit Java Profiler](https://www.yourkit.com/java/profiler/),
[YourKit .NET Profiler](https://www.yourkit.com/.net/profiler/),
and [YourKit YouMonitor](https://www.yourkit.com/youmonitor/).