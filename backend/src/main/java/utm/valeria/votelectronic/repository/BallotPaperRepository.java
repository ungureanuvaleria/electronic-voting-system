package utm.valeria.votelectronic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utm.valeria.votelectronic.model.BallotPaper;

@Repository
public interface BallotPaperRepository extends JpaRepository<BallotPaper, Long> {

}
