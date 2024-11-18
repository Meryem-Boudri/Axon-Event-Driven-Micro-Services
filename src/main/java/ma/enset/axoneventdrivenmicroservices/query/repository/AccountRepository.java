package ma.enset.axoneventdrivenmicroservices.query.repository;

import ma.enset.axoneventdrivenmicroservices.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
