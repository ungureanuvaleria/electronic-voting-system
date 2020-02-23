package utm.valeria.votelectronic.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utm.valeria.votelectronic.model.BallotPaper;
import utm.valeria.votelectronic.model.Candidate;
import utm.valeria.votelectronic.model.ScanRecord;
import utm.valeria.votelectronic.repository.BallotPaperRepository;
import utm.valeria.votelectronic.repository.CandidateRepository;
import utm.valeria.votelectronic.repository.ScanRecordRepository;
import utm.valeria.votelectronic.service.BallotPaperService;
import utm.valeria.votelectronic.service.CandidateService;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BallotPaperServiceImpl implements BallotPaperService {
    
    private BallotPaperRepository ballotPaperRepository;
    private CandidateRepository candidateRepository;
    private ScanRecordRepository scanRecordRepository;
    
    @Inject
    public void setBallotPaperRepository(BallotPaperRepository ballotPaperRepository) {
        this.ballotPaperRepository = ballotPaperRepository;
    }
    
    @Inject
    public void setCandidateRepository(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }
    
    @Inject
    public void setScanRecordRepository(ScanRecordRepository scanRecordRepository) {
        this.scanRecordRepository = scanRecordRepository;
    }
    
    @Override
    @Transactional
    public void createBallotPaper(Long scanId, Candidate candidate) {
        BallotPaper ballotPaper = new BallotPaper();
        Candidate selectedCandidate = this.candidateRepository.getOne(candidate.getId());
        ballotPaper.setCandidate(selectedCandidate);
        ballotPaper.setVoteTime(LocalDateTime.now());
        
        this.ballotPaperRepository.save(ballotPaper);
    
        Optional<ScanRecord> scanRecord = this.scanRecordRepository.findScanRecordByScanId(scanId);
        scanRecord.ifPresent(record -> this.scanRecordRepository.delete(record));
    }
}
