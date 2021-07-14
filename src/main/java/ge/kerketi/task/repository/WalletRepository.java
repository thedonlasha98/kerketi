package ge.kerketi.task.repository;

import ge.kerketi.task.domain.Client;
import ge.kerketi.task.domain.Wallet;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WalletRepository extends CrudRepository<Wallet, Integer> {
    Optional<Wallet> findByClientIdAndWalletType(Integer clientId, String waletType);

    Optional<Wallet> findByClient(Client client);
}
