package utm.valeria.votelectronic.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utm.valeria.votelectronic.model.PoliticalParty;
import utm.valeria.votelectronic.repository.PoliticalPartyRepository;
import utm.valeria.votelectronic.service.PoliticalPartyService;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class PoliticalPartyServiceImpl implements PoliticalPartyService {
    
    private PoliticalPartyRepository politicalPartyRepository;
    
    @Inject
    public void setPoliticalPartyRepository(PoliticalPartyRepository politicalPartyRepository) {
        this.politicalPartyRepository = politicalPartyRepository;
    }
    
    @Override
    @Transactional
    public PoliticalParty getPoliticalPartyByName(String partyName) {
        Optional<PoliticalParty> politicalPartyOptional = this.politicalPartyRepository.findByPartyName(partyName);
        return politicalPartyOptional.get();
    }
}
