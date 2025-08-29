---
# https://vitepress.dev/reference/default-theme-home-page
layout: home

hero:
  name: Fern ~
  text: Java transitions can be ease!
  tagline: Fern — a lightweight Java library for animations and transitions.
  actions:
    - theme: brand
      text: Get Started
      link: /get-started
    - theme: alt
      text: Source Сode
      link: https://github.com/deitylamb/fern

features:
  - title: Framework Agnostic
    icon: 🔌
    details: Fern is framework agnostic, and can be used with any Java graphics library.
  - title: Fluent API
    icon: 💭
    details: Fern provides a fluent API for creating transitions. We care about DX.
  - title: Complex Animations
    icon: 🎞️
    details: Fern can create complex chained animations with minimal effort.
---

<script setup>
import CodeBlock from "/components/CodeBlock.vue";
import WithinHero from "/components/WithinHero.vue";

const code = `
  // Whatever Graphics lib you have
  private final Flow<Graphics> flow = Fern.<Graphics>flow(2000)    
      .delay(100)
      .ease(Easings::easeOutCubic)
      .circular()
      .speed(2)
      .loop();

  protected void paintComponent(Graphics gui) {
    flow.tick(gui, delta); // delta - time between ticks

    // x will snap from 0 to 500 over 2000 milliseconds
    int x = flow.lerp(0, 500);
    gui.fillRect(x, y, size, size);
  }
`
</script>

<WithinHero>
    <CodeBlock :code="code" language="java">
    </CodeBlock>
</WithinHero>

