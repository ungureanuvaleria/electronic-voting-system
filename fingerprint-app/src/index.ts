import SerialPort from "serialport";
import axios from "axios";
import Readline from "@serialport/parser-readline";

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
});







