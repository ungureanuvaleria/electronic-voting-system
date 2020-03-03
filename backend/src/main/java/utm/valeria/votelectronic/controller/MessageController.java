package utm.valeria.votelectronic.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import utm.valeria.votelectronic.exception.FingerprintNotFoundException;
import utm.valeria.votelectronic.exception.WorkstationNotFoundException;
import utm.valeria.votelectronic.service.FingerprintService;
import utm.valeria.votelectronic.service.WorkstationService;

import javax.inject.Inject;

@Controller
public class MessageController {
    
    private FingerprintService fingerprintService;
    private WorkstationService workstationService;
    
    @Inject
    public void setFingerprintService(FingerprintService fingerprintService) {
        this.fingerprintService = fingerprintService;
    }
    
    @Inject
    public void setWorkstationService(WorkstationService workstationService) {
        this.workstationService = workstationService;
    }
    
    @MessageMapping("/fingerprintMessages")
    public void parseMessage(String message, SimpMessageHeaderAccessor headerAccessor) {
        String fingerprintSessionId = headerAccessor.getSessionId();
        try {
            this.fingerprintService.parseNewMessage(message, fingerprintSessionId);
        } catch (FingerprintNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @MessageMapping("/workstationMessages")
    public void parseWorkstationMessage(String message, SimpMessageHeaderAccessor headerAccessor) {
        String workstationSessionId = headerAccessor.getSessionId();
        try {
            this.workstationService.parseMessage(message, workstationSessionId);
        } catch (WorkstationNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
