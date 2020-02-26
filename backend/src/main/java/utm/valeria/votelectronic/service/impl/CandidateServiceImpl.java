package utm.valeria.votelectronic.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utm.valeria.votelectronic.model.Candidate;
import utm.valeria.votelectronic.model.PoliticalParty;
import utm.valeria.votelectronic.repository.CandidateRepository;
import utm.valeria.votelectronic.service.CandidateService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {
    
    private CandidateRepository candidateRepository;
    
    @Inject
    public void setCandidateRepository(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }
    
    @Override
    @Transactional
    public Candidate getCandidateByNameAndSurname(String candidateName, String candidateSurname) {
        return this.candidateRepository.findByCandidateNameAndCandidateSurname(candidateName, candidateSurname).get();
    }
    
    @Override
    @Transactional
    public List<Candidate> getAllCandidates() {
        List<Candidate> candidates = this.candidateRepository.findAll();
        if (candidates == null) {
            candidates = new ArrayList<>();
        }
        return candidates;
    }
}
