package utm.valeria.votelectronic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import utm.valeria.votelectronic.exception.FingerprintNotFoundException;
import utm.valeria.votelectronic.model.Fingerprint;
import utm.valeria.votelectronic.model.FingerprintScan;
import utm.valeria.votelectronic.service.FingerprintService;

import javax.inject.Inject;

@RestController
@CrossOrigin("*")
public class FingerprintScanController {
    private static final String ENDPOINT_URL = "/api/fingerprintScan";
    
    private FingerprintService fingerprintService;
    
    @Inject
    public void setFingerprintService(FingerprintService fingerprintService) {
        this.fingerprintService = fingerprintService;
    }
    
    @PostMapping(ENDPOINT_URL)
    @Transactional
    public void receiveFingerprintScan(@RequestBody FingerprintScan fingerprintScan) {
        try {
            this.fingerprintService.transferFingerprintScanToWorkstation(fingerprintScan);
        } catch (FingerprintNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        /* TODO
            1. @RequestBody should be in format(example):
            {
                fingerprint_id: 1,
                user_id: 4,
                correctness: 456
            }
            For this you must create an class. For reference, look at ../model/WorkstationCredentials,
            it should give you an idea.
            This is also how the object should be created in fingerprint-app(example):
                const data = {
                    fingerprint_id: 1,
                    user_id: 4,
                    correctness: 456
                };
                *NOTE*. The field names on Java class should correspond with fields from Typescript object.
                If you dont want to get an error, there is an annotation @JsonProperty. The value of it indicates the name
                of the field that is contained in the object from Typescript
                So if my Java class field name will be "fingerprintId" and Typescript object field name will be
                "fingerprint_id", it wil not get mapped correctly. This is why you can add @JsonProperty("fingerprint_id")
                to fingerprintId field, so that Jackson(it is a JSON parser library) will know that "fingerprint_id" property
                 corresponds to "fingerprintId" instance variable.
            Look at fingerprint-app and you will understand where you must do this.
            @RequestBody class can be called FingerprintScan but it is up to you how you name it.
            2. Next thing that should be done is about websockets.
            You already have a database table "workstations" where the data about Workstation is stored.
            It is created automatically on startup.
            Now you must add a class which will also be a database table (guide yourself using Workstation class).
            You decide on properties. You can start with basic ID, fingerprintId etc.
            But you must create a one-to-one mapping between workstation and fingerprint. This is because a fingerprint
            is connected to a single station. To do that, you can search for "spring jpa one-to-one mapping"
            This is necessary because once a fingerprint sends some data, we must know to which workstation it maps
            If we get a fingerprint scan from fingerprintOne, we should know to which workstation we must send this data.
            This is why using the one-to-one relation, by having the fingerprintId, we can find the fingerprint row in database,
            and with it comes the workstation to which we must send the data.
            3. Websocket implementation.
            I added an instance variable, brokerMessagingTemplate, it allows you to send data via websocket.
            Now, when you log in with a workstation(which is done via REST), and it's successful,
             you have to create, in a separate component in Angular which waits for a scan, a websocket, which will listen
             to new messages addressed to it. With this you can ask me for help, but here is a good link.
             https://www.javaguides.net/2019/06/spring-boot-angular-8-websocket-example-tutorial.html
             *NOTE* I already did configure the websocket server in Spring, it should be workable,
              but you can always contact me
             I think it is enough for your day. If you will fail something, I will do it tomorrow, and you will quickly
             catch on, I promise.
             
         */
    }
}
