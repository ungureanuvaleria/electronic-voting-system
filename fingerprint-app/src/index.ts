import SerialPort from "serialport";
import axios from "axios";
import Readline from "@serialport/parser-readline";
import * as Stomp from 'stompjs';
import SockJS from "sockjs-client";

const websocketEndpoint = 'http://localhost:8080/votingSystemApp';
const fingerprintScansBroker = '/user/fingerprints';
const messageDestination = '/app/fingerprintMessages';

console.log('Connecting to server via websocket to get admin inputs...');
const ws = new SockJS(websocketEndpoint);
const stompClient: Stomp.Client = Stomp.over(ws);
const websocketHeaders = {
    fingerprint_id: "Fingerprint01"
};

const virtualPort: string = "COM5";
const baseURL: string = 'http://localhost:8080/api/fingerprintScan';
const headers = {
    'Content-Type': 'application/json'
};
/*
    Initializes object which is used for making requests
    It is a library called Axios. If you are interested, you can read about it.
    Here we specify the base url of the server
    The Tomcat server, on which our Spring Boot application runs, has the access endpoint @baseURL constant declared
    higher. So if we want to access a resource on our service, we would call apiClient.post(resourceEndpoint, body);
    Usually baseURL is declared as 'http://localhost:8080/api' or 'http://localhost:8080/', but for simplicity, we
    simple specified the resource we wanted to access.
    Request headers: they are not really necessary in this case, but it would be well for you to read about REST request
    and response headers.
 */
const apiClient = axios.create({
    baseURL: baseURL,
    responseType: 'json',
    headers: headers
});

/* This was done for testing purposes */
// apiClient.post('', JSON.stringify(workstationCredentials))
//     .then(() => console.log("Successfully send data to server!"))
//     .catch(error => console.log("Oops! Something went wrong when sending data to server! Error: " + error));

/*
    Initializes the virtual COM port, with @virtualPort being the name of the port.
    Options: baudRate set to 9600, and the port will try to open automatically (it is a default implementation)
    Error callback process: I think that is pretty clear :)
*/

const serialPort = new SerialPort(virtualPort, {
    baudRate: 9600
});
/*
    Declare a new delimiter. This way, anytime you send via COM port data, when it detects '\n' character, it will flush
    the serial data buffer to 'data' event, so that you get the string that was received until '\n' character was detected.
 */
const parser = new Readline();
/*
    Pipe all of the data that comes from COM port via our parser declared higher. We talked about this.
 */
serialPort.pipe(parser);
/*
    Serial port 'open' event. When our port opens successfully, the callback function gets executed. In case you want
    to do something specific when port is open, this is where you put it.
 */
serialPort.on('open', () => {
    console.log("Opened connection at " + virtualPort + " port.");
});
/*
    Parser 'data' event. Whenever pipe detects the '\n' character, it flushes the buffer, that being @data parameter of
    the callback. After flushing, it calls the callback for this event with @data as parameter.
*/
parser.on('data', data => {
    console.log("Received new fingerprint scan: " + data);

    const stringData: string = data;
    const response: string[] = stringData.split(',');
    console.log('response: ' + response[0]);

    if (response[0].includes("SCAN_AGAIN") ||
        response[0].includes("DELETED") ||
        response[0].includes("EXISTS") ||
        response[0].includes("NON_EXISTENT"))
    {
        console.log("AHOY BITCH");
        stompClient.send(messageDestination, {}, response[0]);
    }
    else if (response[0].includes("INSERTED")) {
        stompClient.send(messageDestination, {}, response[0] + ',' + response[1]);
    }
    else if (response[0].includes("BLEAGHI")) {
        console.log(data);
    }
    else {
        console.log("GOING HERE");
        const dataArray = data.split(",", 3);
        let correctness = dataArray[2];
        const correctedCorrectnessValue = correctness.split('\r', 1)[0];

        const body = {
            fingerprint_id: 'Fingerprint0' + dataArray[0],
            user_id: dataArray[1],
            correctness: correctedCorrectnessValue
        };
        console.log(JSON.stringify(body));
        /*
            Sends the request to server that a new fingerprint was detected.
            If we get a good response, we perform an action in then() method.
            If we get an error, we perform an action in catch() method.

         */
        apiClient.post('', JSON.stringify(body))
            .then(() => console.log("Successfully send data to server!"))
            .catch(error => console.log("Oops! Something went wrong when sending data to server!"));
    }

    // switch (response[0]) {
    //     case "SCAN_AGAIN":
    //         stompClient.send(messageDestination, {}, response[0]);
    //         break;
    //     case "DELETED":
    //         stompClient.send(messageDestination, {}, response[0]);
    //         break;
    //     case "INSERTED":
    //         stompClient.send(messageDestination, {}, response[0] + ',' + response[1]);
    //         break;
    //     case "EXISTS":
    //         stompClient.send(messageDestination, {}, response[0]);
    //         break;
    //     case "NON_EXISTENT":
    //         stompClient.send(messageDestination, {}, response[0]);
    //         break;
    //     default:
    //         const dataArray = data.split(",", 3);
    //         let correctness = dataArray[2];
    //         const correctedCorrectnessValue = correctness.split('\r', 1)[0];
    //
    //         const body = {
    //             fingerprint_id: 'Fingerprint0' + dataArray[0],
    //             user_id: dataArray[1],
    //             correctness: correctedCorrectnessValue
    //         };
    //         console.log(JSON.stringify(body));
    //         /*
    //             Sends the request to server that a new fingerprint was detected.
    //             If we get a good response, we perform an action in then() method.
    //             If we get an error, we perform an action in catch() method.
    //
    //          */
    //         apiClient.post('', JSON.stringify(body))
    //             .then(() => console.log("Successfully send data to server!"))
    //             .catch(error => console.log("Oops! Something went wrong when sending data to server!"));
    //         break;
    // }
});


/*
    WebSocket Implementation part
 */

stompClient.connect({ fingerprint_id: 'Fingerprint01' }, () => {
    stompClient.subscribe(fingerprintScansBroker, message => {
        console.log(message.body);
        const request: string[] = message.body.split(',');
        switch (request[0]) {
            case 'INSERT':
                serialPort.write('1\n');
                break;
            case 'DELETE':
                serialPort.write('2\n');
                new Promise(resolve => {setTimeout(resolve, 2500)})
                    .then(() => {
                        console.log(request[1]);
                        serialPort.write(request[1] + '\n');
                    });
                break;
            case 'READ':
                serialPort.write('3\n');
                break;
            default:
                break;
        }
    });
});









