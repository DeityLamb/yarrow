

<p align="center">
<img src="./docs/assets/fern-white.svg" width="150px" style="border-radius: 200px; display: inline;">

</p>

# EARLY ALPHA
## Fern - Getting Started

Fern is a lightweight Java library for animations and transitions

It is **framework-agnostic**, meaning it works with any graphics or  
UI framework without requiring deep integration or framework-specific dependencies

It uses a **fluent API**, allowing you to create readable, chainable animation code.  
This makes defining complex animations and smooth transitions both simple and expressive

Check documentation at [https://fern.deitylamb.dev](https://fern.deitylamb.dev)

## Installation

You can install Fern using Gradle / Maven from the [GitHub Packages](https://github.com/deitylamb/fern/packages) registry


```groovy
repositories {
    maven {
        url "https://maven.pkg.github.com/deitylamb/fern"
        content {
            includeGroup "dev.deitylamb"
        }
    }
}

dependencies {
    implementation "dev.deitylamb:fern:{version}"
}
```

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/deitylamb/fern</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>dev.deitylamb</groupId>
        <artifactId>fern</artifactId>
        <version>{version}</version>
    </dependency>
</dependencies>
```

### Direct JAR


Or download the latest JAR file from [GitHub Releases](https://github.com/deitylamb/fern/releases) and add it directly to your project. Any distribution method is fine as long as authorship is preserved.

## Contributing

todo ?

## License

Fern is released under the MIT License. See the `LICENSE` file in the project root for more details
