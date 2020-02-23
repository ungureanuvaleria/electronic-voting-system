package utm.valeria.votelectronic.model;

import lombok.Data;

@Data
public class BallotPaperRegistration {
    
    private Long scanId;
    private Candidate candidate;
}
