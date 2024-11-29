//guimain.js

var socket = connect();

function connect() {
    const host     = document.location.host;
    const pathname = document.location.pathname;
    const addr     = "ws://" + host + pathname + "accessgui";
    console.log("connect addr="+addr)
    // Assicura che sia aperta un unica connessione
    if (socket !== undefined && socket.readyState !== WebSocket.CLOSED) {
        alert("WARNING: Connessione WebSocket già stabilita");
    }
    socket = new WebSocket(addr); // CONNESSIONE

    socket.onopen = function (event) {
        //addTo_msgArea("Connessione " + addr + " ok");
        showMsg("Connessione " + addr + " ok");
    };
    socket.onerror = function (event) {
        //addTo_msgArea("ERROR " + event.data);
        showMsg("ERROR " + event.data);
    };
    socket.onmessage = function (event) { // RICEZIONE
        //let [type, payload, info] = event.data.split("/");
        v = event.data
       console.log("onmessage event=" + v)
    };
    return socket;
}


function sendMessage(message) {
    console.log("sendMessage "+ message);
    socket.send(message);
}


