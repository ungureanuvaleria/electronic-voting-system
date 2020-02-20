package utm.valeria.votelectronic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import utm.valeria.votelectronic.exception.WorkstationNotFoundException;
import utm.valeria.votelectronic.exception.WrongCredentialsException;
import utm.valeria.votelectronic.model.Workstation;
import utm.valeria.votelectronic.model.WorkstationCredentials;
import utm.valeria.votelectronic.service.WorkstationService;

import javax.inject.Inject;

@RestController
public class WorkstationAuthController {
    private static final String ENDPOINT_URL = "/api/auth/workstation";
    
    private WorkstationService workstationService;
    
    @Inject
    public void setWorkstationService(WorkstationService workstationService) {
        this.workstationService = workstationService;
    }
    
    @PostMapping(ENDPOINT_URL)
    public ResponseEntity<String> registerWorkstation(@RequestBody WorkstationCredentials workstationCredentials) {
        try {
            Workstation workstation = this.workstationService.registerWorkstation(workstationCredentials);
            String workstationId = workstation.getWorkstationId();
            return ResponseEntity.status(200).body(workstationId);
        } catch (WrongCredentialsException | WorkstationNotFoundException ex) {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }
}
