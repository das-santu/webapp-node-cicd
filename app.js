var express = require("express");
var app = express();
var port = 3000;

app.set('view engine', 'ejs')

let env_data = {
    HOSTNAME: process.env.HOSTNAME,
    NODE_IP: process.env.NODE_IP,
    POD_IP: process.env.POD_IP,
};

// home route
app.get("/", (req, res) => {
    res.render('index', { env_data: env_data })
});

// home route
app.get("/app", (req, res) => {
    res.render('app', { env_data: env_data })
});

// To run the server
app.listen(port, () => {
    console.log(`App UI available on: http://0.0.0.0:${port}`)
})
