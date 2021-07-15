package ge.kerketi.task.service;

import ge.kerketi.task.model.TransactionDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TransactionService {

    Map<String, BigDecimal> cashIn(String pid, String accountNumber, BigDecimal amount, String walletType);

    void transfer(String fromAccount, String toAccount, BigDecimal amount, String walletType);

    List<TransactionDto> getTransactionHistory(String accountNumber);
}
