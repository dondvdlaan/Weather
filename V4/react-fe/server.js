const express   = require('express');
const server    = express();
const path      = require('path');
const PORT      = process.env.PORT || 5000;

// serve up production assets
server.use(express.static(path.resolve(__dirname, 'fe/build')));

// let the react app to handle any unknown routes 
// serve up the index.html if express does'nt recognize the route
server.get('*', (req, res) => {
res.sendFile(path.resolve(__dirname, 'fe', 'build', 'index.html'));
});

// if not in production use the port 5000
console.log('server started on port:',PORT);
server.listen(PORT);