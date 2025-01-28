//guimain.js

var socket = connect();

function connect() {
    const host     = document.location.host;
    const addr     = "ws://" + host +"/accessgui";
    console.log("connect addr="+addr)
    // Assicura che sia aperta un unica connessione
    if (socket !== undefined && socket.readyState !== WebSocket.CLOSED) {
        alert("WARNING: Connessione WebSocket già stabilita");
    }
    socket = new WebSocket(addr); // CONNESSIONE

    socket.onopen = function (event) {
        //addTo_msgArea("Connessione " + addr + " ok");
        console.log("Connessione " + addr + " ok");
    };
    socket.onerror = function (event) {
        //addTo_msgArea("ERROR " + event.data);
        console.log("ERROR " + event.data);
    };
    socket.onmessage = function (event) { 
        // Ricezione del messaggio
        const v = event.data;
        console.log("onmessage event=" + v);
    
        // Parsing del messaggio con formato info(SRC, VAR, VAL)
        const regex = /info\(([^,]+),\s*([^,]+),\s*([^)]+)\)/;
        const match = v.match(regex);
    
        if (match) {
            const src = match[1].trim();
            const variable = match[2].trim();
            const value = match[3].trim().toLowerCase();
    
            console.log(`Parsed message: SRC=${src}, VAR=${variable}, VAL=${value}`);
    
            // Mappa tra variabili ricevute e ID degli elementi nel DOM
            const variableToElementId = {
                "Ws_status": "WasteStorage",
                "As_status": "AshStorage",
                "Incinerator_status": "Incinerator",           
                "MentalState": "OpRobot_Status",
                "RobotState" : "OpRobot_Job"
            };
    
            // Aggiorna il valore dell'elemento DOM corrispondente
            const elementId = variableToElementId[variable];
            if (elementId) {
                const element = document.getElementById(elementId);
                if (element) {
                    element.innerHTML  = value;
                    console.log(`Updated ${elementId} with value: ${value}`);
                } else {
                    console.warn(`Element with ID ${elementId} not found.`);
                }
            } else {
                console.warn(`No mapping found for variable: ${variable}`);
            }
        } else {
            console.error("Message format is invalid:", v);
        }
    };
    
    return socket;
}

function sendMessage(message) {
    console.log("sendMessage "+ message);
    socket.send(message);
}

document.getElementById("pulsante").addEventListener("click", ()=>sendMessage('request/1'));

document.addEventListener("DOMContentLoaded", function () {
    setTimeout(test, 2000);
    setTimeout(controllaCampi, 50000);
})

function test(){
    document.getElementById("pulsante").click();
    document.getElementById("pulsante").disabled=true;
}


function controllaCampi() {
    const campoAsh = document.getElementById("AshStorage");
    const campoWaste = document.getElementById("WasteStorage");

    // Controllo del contenuto dei campi
    if (campoAsh.innerHTML.trim() === "half") {
        console.log("Il campoAsh è OK");
        campoAsh.style.border = "2px solid green";
    } else {
        console.log("Il campoAsh è sbagliato!");
        campoAsh.style.border = "2px solid red";
    }

    if (campoWaste.innerHTML.trim() === "0") {
        console.log("Il campoWaste è OK");
        campoWaste.style.border = "2px solid green";
    } else {
        console.log("Il campoWaste è sbagliato!");
        campoWaste.style.border = "2px solid red";
    }
}




/* 
<!--  
 Possibili valori di MentalState:
 A default MentalState=""
 - HOME: Nello stato home facciamo UPDATERSOURCE MentalState=HOME e UPDATERSOURCE RobotStatus=IDLE. Nella home area aspettando dispatch robotStart 
    (-> andimo nello stato goToWasteIn nel quale cambio stato RobotStatus in WORKING), lo invia wis dopo che abbiamo verificato condizioni.
    In goToWasteIn UPDATERSOURCE RobotStatus=WORKING. La moverobotdone ci porta in loadRP.
 - WASTEIN: nello stato loadRP MentalState viene modificato in WASTEIN -> UPDATERSOURCE MentalState=WASTEIN. La moverobotdone ci porta in startBurn.
 - BURNIN: nello stato startBurn MentalState viene modificato in BURNIN -> UPDATERSOURCE MentalState=BURNIN. La moverobotdone ci porta in home_go_burnOut.
    In home_go_burnOut la moverobotdone ci porta in gatheringAsh    .
 - BURNOUT: nello stato gatheringAsh MentalState viene modificato in BURNOUT -> UPDATERSOURCE MentalState=BURNOUT. La moverobotdone ci porta in unload.
 - ASHOUT: nello stato unload MentalState viene modificato in ASHOUT -> UPDATERSOURCE MentalState=ASHOUT e UPDATERSOURCE RobotStatus=IDLE. La moverobotdone 
    ci porta in home.


 Possibili valori di RobotStatus:
 - IDLE
 - WORKING 
 -->
*/