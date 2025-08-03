<script setup>
import { data } from './version.data.js'
</script>

<center>
<img src="/assets/fern-white.svg" width="200px" style="border-radius: 200px; display: inline;">
</center>

# Fern <Badge type="tip">{{ data.version }}</Badge>

Fern — framework-agnostic, lightweight Java library for animations and transitions  

## Installation

You can install Fern using gradle, maven or directly from the JAR file.

### Gradle
---
```groovy-vue
repositories {
    maven { url "https://maven.pkg.github.com/deitylamb/fern" }
}

dependencies {
    implementation "dev.deitylamb:fern:{{ data.version }}"
}
```
### Maven
---

```xml-vue
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
        <version>{{ data.version }}</version>
    </dependency>
</dependencies>
```
### Direct JAR
---
You can also download the latest JAR file from [GitHub Releases](https://github.com/deitylamb/fern/releases) and add it directly to your project. Any distribution method is fine as long as authorship is preserved.

## Contributing

For information on contributing to Fern, please refer to the project's GitHub repository [https://github.com/deitylamb/fern](https://github.com/deitylamb/fern)

## License

Fern is released under the MIT License. See the `LICENSE` file in the project root for more details
