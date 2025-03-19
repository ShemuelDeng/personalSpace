// 缓存滚动条宽度
let cachedScrollbarWidth

// 检查页面是否有滚动
const hasScroll = () => {
  return document.documentElement.scrollHeight > document.documentElement.clientHeight
}

// 获取滚动条宽度
const getScrollbarWidth = () => {
  if (cachedScrollbarWidth !== undefined) {
    return cachedScrollbarWidth
  }
  
  const outer = document.createElement('div')
  outer.style.visibility = 'hidden'
  outer.style.overflow = 'scroll'
  document.body.appendChild(outer)

  const inner = document.createElement('div')
  outer.appendChild(inner)

  cachedScrollbarWidth = outer.offsetWidth - inner.offsetWidth
  outer.parentNode.removeChild(outer)

  return cachedScrollbarWidth
}

// 禁用页面滚动
export const disableScroll = () => {
   document.body.classList.add('scroll-disabled')
}

// 启用页面滚动
export const enableScroll = () => {
  document.body.classList.remove('scroll-disabled')
} 