## MCLib
Minecraft, as a dependency.

[![Issues](https://img.shields.io/github/issues/Zerite/MCLib?style=flat-square&label=Issues)](https://github.com/Zerite/MCLib/issues)
[![Forks](https://img.shields.io/github/forks/Zerite/MCLib?style=flat-square&label=Forks)](https://github.com/Zerite/MCLib/network/members)
[![Stars](https://img.shields.io/github/stars/Zerite/MCLib?style=flat-square&label=Stars)](https://github.com/Zerite/MCLib/stargazers)
[![License](https://img.shields.io/github/license/Zerite/MCLib?style=flat-square&label=License)](https://github.com/Zerite/MCLib/blob/master/LICENSE)
[![](https://jitpack.io/v/Zerite/MCLib.svg)](https://jitpack.io/#Zerite/MCLib)

#### Contents
1. [What IS MCLib?](#1-what-is-mclib)
2. [Using it](#2-using-it)
    1. [Add JitPack](#add-jitpack)
    2. [Include MCLib](#include-mclib)

#### 1. What IS MCLib?
MCLib is a simple Kotlin library which aims to *completely recreate* many of
the essential concepts of Minecraft.
This includes **the full REST API, protocol, NBT, chat, localization** and more to come!

#### 2. Using it.
To include MCLib into your project, add JitPack to your repositories and
include the dependency.

##### Add JitPack
*Maven*
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

*Gradle*
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

##### Include MCLib
*Maven*
```xml
<dependencies>
    <dependency>
        <groupId>com.github.Zerite</groupId>
        <artifactId>MCLib</artifactId>
        <version>Tag</version>
    </dependency>
</dependencies>
```

*Gradle*
```groovy
dependencies {
    implementation 'com.github.Zerite:MCLib:Tag'
}
```