package utm.valeria.votelectronic.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import utm.valeria.votelectronic.exception.FingerprintNotFoundException;
import utm.valeria.votelectronic.service.FingerprintService;

import javax.inject.Inject;

@Controller
public class MessageController {
    
    private FingerprintService fingerprintService;
    
    @Inject
    public void setFingerprintService(FingerprintService fingerprintService) {
        this.fingerprintService = fingerprintService;
    }
    
    @MessageMapping("/messages")
    public void parseMessage(String message, SimpMessageHeaderAccessor headerAccessor) {
        String fingerprintSessionId = headerAccessor.getSessionId();
        try {
            this.fingerprintService.parseNewMessage(message, fingerprintSessionId);
        } catch (FingerprintNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
