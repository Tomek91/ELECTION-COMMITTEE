package pl.com.app.repository.impl;


import org.springframework.stereotype.Repository;
import pl.com.app.exceptions.ExceptionCode;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.Candidate;
import pl.com.app.repository.CandidateRepository;
import pl.com.app.repository.generic.AbstractGenericRepository;

@Repository
public class CandidateRepositoryImpl extends AbstractGenericRepository<Candidate> implements CandidateRepository {

    @Override
    public Long candidatesNumbersByPoliticalParty(Long politicalPartyId) {
        try {
            if (politicalPartyId == null) {
                throw new NullPointerException("POLITICAL PARTY ID IS NULL");
            }
            return (Long) getEntityManager().createQuery("select count(c.name) from " + getEntityClass().getCanonicalName()
                    + " c join c.politicalParty p where p.id = :politicalPartyId")
                    .setParameter("politicalPartyId", politicalPartyId)
                    .getSingleResult();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
        }
    }
}
