

<p align="center">
<img src="./docs/assets/yarrow-white.svg" width="150px" style="border-radius: 200px; display: inline;">

</p>

# EARLY ALPHA
## Yarrow - Getting Started

Yarrow is a lightweight Java library for animations and transitions

It is **framework-agnostic**, meaning it works with any graphics or  
UI framework without requiring deep integration or framework-specific dependencies

It uses a **fluent API**, allowing you to create readable, chainable animation code.  
This makes defining complex animations and smooth transitions both simple and expressive

Check documentation at [https://yarrow.deitylamb.dev](https://yarrow.deitylamb.dev)

## Installation

You can install Yarrow using Gradle / Maven from the [GitHub Packages](https://github.com/deitylamb/yarrow/packages) registry


```groovy
repositories {
    maven {
        url "https://maven.pkg.github.com/deitylamb/yarrow"
        content {
            includeGroup "dev.deitylamb"
        }
    }
}

dependencies {
    implementation "dev.deitylamb:yarrow:{version}"
}
```

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/deitylamb/yarrow</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>dev.deitylamb</groupId>
        <artifactId>yarrow</artifactId>
        <version>{version}</version>
    </dependency>
</dependencies>
```

### Direct JAR


Or download the latest JAR file from [GitHub Releases](https://github.com/deitylamb/yarrow/releases) and add it directly to your project. Any distribution method is fine as long as authorship is preserved.

## Contributing

todo ?

## License

Yarrow is released under the MIT License. See the `LICENSE` file in the project root for more details
