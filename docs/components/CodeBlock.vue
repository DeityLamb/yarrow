<template>
  <div class="code-block">
    <pre><code class="hljs" v-html="highlightedCode"></code></pre>
  </div>
</template>

<script setup>
import hljs from 'highlight.js';
import 'highlight.js/styles/github-dark.css';
import { computed } from 'vue';

const props = defineProps({
  code: {
    type: String,
    required: true,
  },
  language: {
    type: String,
    default: 'typescript',
  },
});

const highlightedCode = computed(() => {
  const language = props.language || 'plaintext';
  if (hljs.getLanguage(language)) {
    try {
      return hljs.highlight(props.code, { language, ignoreIllegals: true }).value;
    } catch (e) {
      console.error(e);
    }
  }
  return hljs.highlightAuto(props.code).value;
});
</script>

<style scoped>
.code-block {
  position: relative;
}
pre {
  margin: 0;
}
code {
  border-radius: 12px;
}
</style>
