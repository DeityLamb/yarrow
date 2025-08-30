import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  vite: {
    server: { port: 3000 }
  },
  title: "Yarrow - Lightweight Java library for animations and transitions",
  description: "Yarrow - framework agnostic, lightweight Java library for animations and transitions with care about DX",
  cleanUrls: true,
  appearance: 'dark',
  head: [
    ['link', { rel: 'icon', href: '/assets/yarrow-white.svg' }]
  ],
  
  themeConfig: {

    search: {
      provider: 'local'
    },

    outline: {
      label: 'Quick Links',
      level: [2,4]
    },

    footer: {
      message: 'Made with ❤️'
    },
    
    editLink: {
      pattern: 'https://github.com/deitylamb/yarrow/edit/main/docs/:path',
      text: 'Edit this page on GitHub'
    },
    
    siteTitle: "yarrow",
    logo: {
      light: "/assets/yarrow-black.svg",
      dark: "/assets/yarrow-white.svg",
      alt: "Yarrow logo"
    },
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: 'Home', link: '/' }
    ],
    sidebar: [
      {
        text: 'Introduction',
        items: [
          { text: 'Get Started', link: '/get-started' },
          { text: 'Overview', link: '/overview' },
        ]
      },
      {
        text: 'Core Concepts',
        items: [
          { text: 'Control Flow', link: '/core/controlling' },
          { text: 'Decorators and Sequences', link: '/core/decorators' },
          { text: 'Tracks', link: '/core/tracks' },
          { text: 'Tweens', link: '/core/tweens' },
        ]
      },
      {
        text: "Examples",
        items: []
      },
      {
        text: "Miscellaneous",
        items: [
          { text: 'Glossary', link: '/glossary' },
        ]
      }
    ],

    socialLinks: [
      { icon: 'github', link: 'https://github.com/deitylamb/yarrow' }
    ]
  }
})
