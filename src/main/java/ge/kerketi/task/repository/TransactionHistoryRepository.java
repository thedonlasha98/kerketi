package ge.kerketi.task.repository;

import ge.kerketi.task.domain.TransactionHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionHistoryRepository extends CrudRepository<TransactionHistory, Integer> {

    List<TransactionHistory> findByFromOrToOrderByDateDesc(String from, String to);
}
