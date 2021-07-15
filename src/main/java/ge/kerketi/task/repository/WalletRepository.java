package ge.kerketi.task.repository;

import ge.kerketi.task.domain.Wallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WalletRepository extends CrudRepository<Wallet, Integer> {

    Optional<Wallet> findByClientIdAndWalletType(Integer clientId, String waletType);

    @Query(value = "SELECT \n" +
            "    b.id AS id,\n" +
            "    b.client_id AS clientId,\n" +
            "    b.wallet_type AS walletType,\n" +
            "    b.balance_available AS balanceAvailable\n" +
            "FROM\n" +
            "    tbl_client a \n" +
            "        INNER JOIN\n" +
            "    tbl_wallet b ON a.id = b.client_id\n" +
            "and a.account_number = :accountNumber\n" +
            "and b.wallet_type = :walletType", nativeQuery = true)
    Optional<Wallet> getWalletByAccountNumberAndWalletType(String accountNumber, String walletType);


}
