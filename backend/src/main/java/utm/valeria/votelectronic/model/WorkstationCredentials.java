package utm.valeria.votelectronic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WorkstationCredentials {
    
    @JsonProperty("workstation_id")
    private String workstationId;
    
    @JsonProperty("workstation_password")
    private String workstationPassword;
}
