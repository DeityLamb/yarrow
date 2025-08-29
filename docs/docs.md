# Yarrow: A Simple and Lightweight Transition Library for Java

## Overview
Yarrow is a simple and lightweight Java library designed for creating smooth and customizable transitions and animations. It provides a flexible API to define how values change over time, enabling developers to easily implement dynamic visual effects and interactive behaviors in Java applications. It is framework-agnostic, meaning it can be integrated with any Java graphics library.

## Core Concepts

### Transitions (`Transitionable<T>`)
A `Transition` is the fundamental building block. It defines how a value changes from a starting state to an ending state over a specified duration. `Transitionable<T>` is an interface that provides methods for controlling the transition (play, pause, reset, stop, seek), chaining transitions (`then`, `circular`), applying decorators (`ease`, `delay`, `repeat`, `loop`), and interpolating values (`lerp`).

Key methods:
- `transition(double duration)`: Creates a new transition with a given duration.
- `alpha()`: Returns the current progress of the transition (0.0 to 1.0).
- `tick(T graphics, double delta)`: Advances the transition by `delta` milliseconds.
- `play()`, `pause()`, `reset()`, `stop()`, `restart()`: Control the lifecycle of the transition.
- `seek(double duration)`: Jumps the transition to a specific point in time.
- `lerp(from, to)`: Linearly interpolates between two values based on the current `alpha`. Overloaded for `double`, `int`, `long`, `float`, and `Color`.

### Flows (`Flowable<T>`)
Flows allow you to combine multiple transitions or other flows to create more complex animation sequences.
- **`FocusFlow<T>`**: Manages two transitions, typically for "hover" and "blur" states, allowing for interactive focus effects. It can be set to `focus` or `blur` state.
- **`ParallelFlow<T>`**: Executes multiple `Flowable` instances (transitions or other flows) concurrently.

### Tweens (`Tweenable<T>`)
Tweens are responsible for interpolating values during a transition. Yarrow provides mechanisms to define how different types of properties (e.g., numbers, colors) are interpolated.
- `Tweenable<T>`: Functional interface for applying a tween.
- `CompoundTween<T>`: Combines multiple `Tweenable` instances to apply them simultaneously.
- `PipeTween<T>`: Applies an easing function to another `Tweenable`.
- `TweenBuilder<T>`: A fluent API for constructing complex `Tweenable` objects.

### Easings (`Easings.Ease`)
Easings (or easing functions) control the rate of change of an animation over time. Instead of a linear progression, easings allow for non-linear acceleration and deceleration, making animations feel more natural and visually appealing. Yarrow includes a variety of common easing functions (e.g., `easeInQuad`, `easeOutCubic`, `easeInOutCubic`) and a `springEase` function for physics-based animations.

### Hooks (`HookSubscriber<T>`, `HookPublisher<T>`, `HookBuilder<T>`)
Hooks provide a way to subscribe to events during a transition's lifecycle, such as `onPlay`, `onPause`, `onTick`, and `onProgress`. This allows for custom logic to be executed at specific points in the animation.

## Usage

Yarrow's API is designed to be intuitive and chainable. You can start by creating a `Transition` and then chaining various decorators or combining them into `Flows`.

For practical examples, refer to the `samples/java/dev/deitylamb/yarrow/` directory in the project:
- `EaseSample.java`: Demonstrates various easing functions and how to apply them to a visual element.
- `GradientSample.java`: Shows how to animate gradients using Yarrow's color interpolation capabilities.
- `SwingGifRecorder.java`: An example of how to record animations created with Yarrow into a GIF file, showcasing integration with Swing.

### Basic Transition Example (Conceptual)

```java
import dev.deitylamb.yarrow.Yarrow;
import dev.deitylamb.yarrow.transitions.Transitionable;
import dev.deitylamb.yarrow.common.Color;
import dev.deitylamb.yarrow.common.Easings;

public class MyAnimation {
    public static void main(String[] args) {
        // Animate a double value from 0.0 to 1.0 over 2 seconds with an ease-in effect
        Transitionable<Double> doubleTransition = Yarrow.transition(2000) // duration in milliseconds
            .from(0.0) // This 'from' and 'to' are conceptual for lerp, not part of Transition directly
            .to(1.0)   // The Transitionable itself manages alpha, lerp uses it.
            .ease(Easings::easeOutCubic)
            .delay(100) // Add a 100ms delay before the transition starts
            .speed(2); // Make the transition twice as fast

        // Animate a Color from red to blue over 1.5 seconds, then reverse and loop
        Transitionable<Color> colorTransition = Yarrow.transition(1500)
            .from(Color.RED)
            .to(Color.BLUE)
            .circular() // Play forward then reverse
            .loop();    // Loop indefinitely

        // Example of using hooks (assuming 'graphics' is a context object like Graphics2D)
        Transitionable<Object> hookedTransition = Yarrow.transition(1000)
            .onProgress((graphics, progress) -> {
                System.out.println("Progress: " + progress);
                // Update UI based on progress
            })
            .onPlay(() -> System.out.println("Animation started!"))
            .onPause(() -> System.out.println("Animation paused."));

        // To run the transition, you would typically call tick() in a game loop or Swing timer:
        // doubleTransition.play();
        // colorTransition.play();
        // hookedTransition.play();

        // In your rendering loop (e.g., paintComponent in Swing, or game loop):
        // doubleTransition.tick(null, deltaTime); // deltaTime is time elapsed since last tick
        // Color currentColor = colorTransition.lerp(Color.RED, Color.BLUE);
        // double currentAlpha = doubleTransition.lerp(0.0, 1.0);
    }
}
```

## Project Structure

- `src/main/java/dev/deitylamb/yarrow/`: Contains the core library source code.
    - `common/`: Utility classes like `Color`, `Easings`, `YarrowUtils`.
    - `flows/`: Implementations of `Flowable` (e.g., `FocusFlow`, `ParallelFlow`).
    - `hooks/`: Classes for managing and subscribing to transition events.
    - `transitions/`: Core transition logic and decorators (`Transition`, `SequenceTransition`, `DelayTransition`, `EaseTransition`, `RepeatTransition`).
    - `tweens/`: Classes for interpolating values (`Tweenable`, `CompoundTween`, `PipeTween`, `TweenBuilder`).
- `src/test/java/dev/deitylamb/yarrow/`: Unit tests for the library.
- `samples/java/dev/deitylamb/yarrow/`: Example applications demonstrating library usage.
- `docs/`: Documentation website source (built with VitePress).
- `build.gradle`: Gradle build script for the project.
- `gradle/libs.versions.toml`: Manages project dependencies.
- `gradle.properties`: Project-specific Gradle properties.
- `LICENSE`: MIT License details.

## Building the Project

Yarrow uses Gradle as its build automation tool.

### Prerequisites
- Java Development Kit (JDK) 8 or higher.

### Build Commands

To build the project:
```bash
./gradlew build
```

To run the tests:
```bash
./gradlew test
```

To run the sample applications:
- Run the Gradient Sample:
  ```bash
  ./gradlew samplesGradient
  ```
- Run the Ease Sample:
  ```bash
  ./gradlew samplesEase
  ```
- Run the Swing GIF Recorder:
  ```bash
  ./gradlew recordGif
  ```

### Documentation Website

The `docs/` directory contains the source for the project's documentation website, built using VitePress.

To run the documentation website locally:
```bash
npm install # if you haven't already
npm run docs:dev
```

To build the documentation website for deployment:
```bash
npm run docs:build
```

