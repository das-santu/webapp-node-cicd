const express = require('express')
const app = express()
const port = 3000

app.set('view engine', 'ejs')

const envData = {
  HOSTNAME: process.env.HOSTNAME,
  NODE_IP: process.env.NODE_IP,
  POD_IP: process.env.POD_IP
}

// home route
app.get('/', (req, res) => {
  res.render('index', { envData })
})

// home route
app.get('/app', (req, res) => {
  res.render('app', { envData })
})

// To run the server
app.listen(port, () => {
  console.log(`App UI available on: http://0.0.0.0:${port}`)
})

module.exports = app
