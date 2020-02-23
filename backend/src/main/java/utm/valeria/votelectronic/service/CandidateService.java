package utm.valeria.votelectronic.service;

import utm.valeria.votelectronic.model.Candidate;

import java.util.List;

public interface CandidateService {
    
    Candidate getCandidateByNameAndSurname(String candidateName, String candidateSurname);
    List<Candidate> getAllCandidates();
}
