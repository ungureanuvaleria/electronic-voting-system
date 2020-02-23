package utm.valeria.votelectronic.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import utm.valeria.votelectronic.model.BallotPaperRegistration;
import utm.valeria.votelectronic.model.Candidate;
import utm.valeria.votelectronic.service.BallotPaperService;

import javax.inject.Inject;

@RestController
@CrossOrigin("*")
public class BallotPaperRegistrationController {
    
    private static final String ENDPOINT_URL = "/api/ballotPaperRegistration";
    
    private BallotPaperService ballotPaperService;
    
    @Inject
    public void setBallotPaperService(BallotPaperService ballotPaperService) {
        this.ballotPaperService = ballotPaperService;
    }
    
    @PostMapping(ENDPOINT_URL)
    public void registerBallotPaper(@RequestBody BallotPaperRegistration ballotPaperRegistration) {
        Long scanId = ballotPaperRegistration.getScanId();
        Candidate candidate = ballotPaperRegistration.getCandidate();
        this.ballotPaperService.createBallotPaper(scanId, candidate);
        /*
            TODO
             Here @RequestBody will be an object which will contain scanId and a ballot paper with selected vote.
             Since we want a vote to be anonymous, when we received a new successful scan from fingerprint, we save it
             in the database along with the user who did that, so we only have a record for scan in our DB.
             When user makes his vote, we find his scan object via scanId, and we create a new ballot paper in our DB,
             not specifying the user that said this. Before creating the ballot paper, we do the stuff you said:
                Sing the ballot paper with CEC public key.
                Generate key pair for user.
                Sign ballot paper with user private key.
                Send key pair to user via SMS or email.
             To identify the vote, we will store user's public key in ballot paper object so we can read it.
             Also, we will store user public key under user table as well, so that when the user will vote again, we
             don't create another key pair
             Caroce, aici trebuie să îmi explici oleacă cum trebuie de făcut și eu o să te ajut.
             Analizează mîine tot asta
             Eu am făcut partea cu receive la scan și transmiterea către web, doar că va trebuie de făcut asta la web și
             de testat.
             
         */
    }
}
