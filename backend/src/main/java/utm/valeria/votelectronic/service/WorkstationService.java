package utm.valeria.votelectronic.service;

import utm.valeria.votelectronic.exception.WorkstationNotFoundException;
import utm.valeria.votelectronic.exception.WrongCredentialsException;
import utm.valeria.votelectronic.model.Workstation;
import utm.valeria.votelectronic.model.WorkstationCredentials;

public interface WorkstationService {
    
    Workstation registerWorkstation(WorkstationCredentials workstationCredentials)
            throws WorkstationNotFoundException, WrongCredentialsException;
    Workstation getWorkstationByWorkstationId(String workstationId) throws WorkstationNotFoundException;
    void setWorkstationSessionId(String workstationId, String sessionId) throws WorkstationNotFoundException;
    void parseMessage(String message, String workstationSessionId) throws WorkstationNotFoundException;
}
