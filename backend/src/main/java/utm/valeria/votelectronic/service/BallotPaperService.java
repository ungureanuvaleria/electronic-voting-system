package utm.valeria.votelectronic.service;

import utm.valeria.votelectronic.model.Candidate;

public interface BallotPaperService {
    
    void createBallotPaper(Long scanId, Candidate candidate);
}
