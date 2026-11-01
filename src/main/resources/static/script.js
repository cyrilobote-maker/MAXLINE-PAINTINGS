document.addEventListener('DOMContentLoaded', () => {
  const button = document.querySelector('.menu-toggle'),
    nav = document.querySelector('.site-nav')
  if (button && nav) {
    button.addEventListener('click', () => {
      const open = button.getAttribute('aria-expanded') !== 'true'
      button.setAttribute('aria-expanded', String(open))
      button.setAttribute(
        'aria-label',
        open ? 'Close navigation menu' : 'Open navigation menu'
      )
      nav.classList.toggle('open', open)
    })
    nav.querySelectorAll('a').forEach(a =>
      a.addEventListener('click', () => {
        nav.classList.remove('open')
        button.setAttribute('aria-expanded', 'false')
        button.setAttribute('aria-label', 'Open navigation menu')
      })
    )
    document.addEventListener('click', e => {
      if (!nav.contains(e.target) && !button.contains(e.target)) {
        nav.classList.remove('open')
        button.setAttribute('aria-expanded', 'false')
        button.setAttribute('aria-label', 'Open navigation menu')
      }
    })
    document.addEventListener('keydown', e => {
      if (e.key === 'Escape') {
        nav.classList.remove('open')
        button.setAttribute('aria-expanded', 'false')
        button.focus()
      }
    })
  }
  document
    .querySelectorAll('[data-year]')
    .forEach(n => (n.textContent = new Date().getFullYear()))
  const form = document.getElementById('contactForm')
  if (!form) return
  form.addEventListener('submit', async e => {
    e.preventDefault()
    const status = document.getElementById('formStatus'),
      submit = form.querySelector('button[type="submit"]'),
      d = new FormData(form)
    const payload = {
      name: String(d.get('name') || '').trim(),
      phone: String(d.get('phone') || '').trim(),
      email: String(d.get('email') || '').trim(),
      service: String(d.get('service') || '').trim(),
      message: String(d.get('message') || '').trim(),
      website: String(d.get('extraDetailCheck') || '').trim()
    }
    if (
      !payload.name ||
      !payload.phone ||
      !payload.service ||
      !payload.message
    ) {
      status.textContent = 'Please complete all required fields.'
      return
    }
    submit.disabled = true
    submit.textContent = 'Sending enquiry...'
    status.textContent = 'Sending your enquiry by email...'
    try {
      const response = await fetch('/api/enquiries', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      })
      const result = await response.json().catch(() => ({}))
      if (!response.ok)
        throw new Error(result.message || 'Could not send enquiry.')
      status.textContent =
        'Thank you! Your enquiry has been sent by email to MAXLINE PAINTINGS. WhatsApp is not required.'
      form.reset()
      const p = document.createElement('p')
      p.className = 'form-note'
      p.append(document.createTextNode('You may also contact us on WhatsApp: '))
      ;[
        ['+256 787 385413', 'https://wa.me/256787385413'],
        ['+256 708 447998', 'https://wa.me/256708447998']
      ].forEach((item, i) => {
        if (i) p.append(document.createTextNode(' or '))
        const a = document.createElement('a')
        a.href = item[1]
        a.target = '_blank'
        a.rel = 'noopener noreferrer'
        a.textContent = item[0]
        p.append(a)
      })
      status.insertAdjacentElement('afterend', p)
    } catch (err) {
      status.textContent =
        err.message || 'Email could not be sent. Please try again or call us.'
    } finally {
      submit.disabled = false
      submit.textContent = 'Send enquiry'
    }
  })
})
