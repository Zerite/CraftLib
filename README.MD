## MCLib
Minecraft, as a dependency.

[![Issues](https://img.shields.io/github/issues/Zerite/MCLib?style=flat-square&label=Issues)](https://github.com/Zerite/MCLib/issues)
[![License](https://img.shields.io/github/license/Zerite/MCLib?style=flat-square&label=License)](https://github.com/Zerite/MCLib/blob/master/LICENSE)
[![Latest Stable](https://img.shields.io/github/v/release/Zerite/MCLib?style=flat-square&label=Latest%20Stable)](https://github.com/Zerite/MCLib/releases)

#### Contents
1. [What IS MCLib?](#1-what-is-mclib)
2. [Using it](#2-using-it)

#### 1. What IS MCLib?
MCLib is a simple Kotlin library which aims to *completely recreate* many of
the essential concepts of Minecraft.
This includes **the full REST API, protocol, NBT, chat, localization** and more to come!

#### 2. Using it.
To include MCLib into your project, add our **[GitHub Registry](https://github.com/Zerite/MCLib/packages)** to your project.
Upon a release being published, we will automatically build and publish the version
to our package registry. 

**Only these** are stable, and all other releases should be considered **beta software**.

##### Package Registry
[View Packages](https://github.com/Zerite/MCLib/packages)

```xml
<repository>
    <id>mclib</id>
    <name>MCLib Package Registry</name>
    <url>https://maven.pkg.github.com/Zerite/MCLib</url>
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
    <groupId>dev.zerite.mclib</groupId>
    <artifactId>mclib-protocol-beta</artifactId>
    <version>a4bca49</version>
</dependency>
```