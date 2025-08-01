import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  title: "Fern - Lightweight Java library for animations and transitions",
  description: "Fern - framework agnostic, lightweight Java library for animations and transitions with care about DX",
  head: [
    ['link', { rel: 'icon', href: '/fern-white.svg' }]
  ],
  
  themeConfig: {
    footer: {
      message: 'Released under the MIT License.'
    },
    

    editLink: {
      pattern: 'https://github.com/deitylamb/fern/edit/main/docs/:path',
      text: 'Edit this page on GitHub'
    },
    
    siteTitle: "FERN",
    logo: {
      light: "/fern-black.svg",
      dark: "/fern-white.svg",
      alt: "Fern logo"
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
          { text: 'Decorators & Sequences', link: '/core/decorators' },
        ]
      }
    ],

    socialLinks: [
      { icon: 'github', link: 'https://github.com/deitylamb/fern' }
    ]
  }
})
