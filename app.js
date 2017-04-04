var express = require('express');
var app = express();
var server = require('http').Server(app);

server.listen(process.env.PORT || 9999,function(){
    console.log("Server connected. Listening on port: 9999");
});

app.use( express.static(__dirname + '/fiyoteam' ) );


