package ge.kerketi.task.repository;

import ge.kerketi.task.domain.TransactionHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionHistoryRepository extends CrudRepository<TransactionHistory, Integer> {
    
    @Override
    List<TransactionHistory> findAll();

    List<TransactionHistory>  findByFromOrToOrderByDateDesc(String from, String to);
}
