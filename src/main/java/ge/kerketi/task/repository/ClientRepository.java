package ge.kerketi.task.repository;

import ge.kerketi.task.domain.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Integer> {
    Optional<Client> findByPid(String pid);

    Optional<Client> findByAccountNumber(String fromAccount);

    @Query(value = "select right(uuid_short(),9)", nativeQuery = true)
    Integer getUniqueAccountNumber();
}