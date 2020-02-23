package utm.valeria.votelectronic.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import utm.valeria.votelectronic.model.Candidate;
import utm.valeria.votelectronic.service.CandidateService;

import javax.inject.Inject;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CandidateController {
    private static final String BASE_URL = "/api/candidate";
    
    private CandidateService candidateService;
    
    @Inject
    public void setCandidateService(CandidateService candidateService) {
        this.candidateService = candidateService;
    }
    
    @GetMapping(BASE_URL)
    public List<Candidate> getAllCandidates() {
        return this.candidateService.getAllCandidates();
    }
}
