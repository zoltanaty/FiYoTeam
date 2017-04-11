var express = require('express');
var path    = require('path');
var app = express();

// set the port of our application
// process.env.PORT lets the port be set by Heroku
var port = process.env.PORT || 8080;

// make express look in the public directory for assets (css/js/img)
app.use(express.static(__dirname + '/fiyoteam'));

// Catch all other routes and return the index file
app.get('*', (req, res) => {
  res.sendFile(path.join(__dirname, '/fiyoteam/index.html'));
});

app.listen(port, function() {
    console.log('Our app is running on http://localhost:' + port);
});