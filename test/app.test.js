const request = require('supertest')
const app = require('../app') // Update this with the correct file name
const { expect } = require('chai') // Import expect from Chai assertion library

describe('GET /', () => {
  it('responds with 200 and renders index with Welcome', async () => {
    const res = await request(app).get('/')
    expect(res.status).to.equal(200)
    expect(res.text).to.contain('<h1>Welcome!</h1>') // Update this with your actual index view content
  })
})

describe('GET /app', () => {
  it('responds with 200 and renders app section', async () => {
    const res = await request(app).get('/app')
    expect(res.status).to.equal(200)
    expect(res.text).to.contain('<h1>App Section!!</h1>') // Update this with your actual app view content
  })
})
