package utm.valeria.votelectronic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import utm.valeria.votelectronic.exception.WorkstationNotFoundException;
import utm.valeria.votelectronic.exception.WrongCredentialsException;
import utm.valeria.votelectronic.model.Workstation;
import utm.valeria.votelectronic.model.WorkstationAuthResponse;
import utm.valeria.votelectronic.model.WorkstationCredentials;
import utm.valeria.votelectronic.service.WorkstationService;

import javax.inject.Inject;

@RestController
@CrossOrigin("*")
public class WorkstationAuthController {
    private static final String ENDPOINT_URL = "/api/auth/workstation";
    
    private WorkstationService workstationService;
    
    @Inject
    public void setWorkstationService(WorkstationService workstationService) {
        this.workstationService = workstationService;
    }
    
    @PostMapping(ENDPOINT_URL)
    public WorkstationAuthResponse registerWorkstation(@RequestBody WorkstationCredentials workstationCredentials) {
        try {
            Workstation workstation = this.workstationService.registerWorkstation(workstationCredentials);
            String workstationId = workstation.getWorkstationId();
            WorkstationAuthResponse workstationAuthResponse = new WorkstationAuthResponse();
            workstationAuthResponse.setWorkstationId(workstationId);
            return workstationAuthResponse;
        } catch (WrongCredentialsException | WorkstationNotFoundException ex) {
            WorkstationAuthResponse workstationAuthResponse = new WorkstationAuthResponse();
            workstationAuthResponse.setWorkstationId("-1");
            return workstationAuthResponse;
        }
    }
}
