package ge.kerketi.task.controller;

import ge.kerketi.task.model.TransactionDto;
import ge.kerketi.task.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/cash-in")
    ResponseEntity<Map<String,BigDecimal>> cashIn(String pid, String accountNumber, BigDecimal amount, String walletType) {
        Map<String,BigDecimal> response = transactionService.cashIn(pid,accountNumber,amount,walletType);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/transfer")
    ResponseEntity<Void> transfer(String fromAccount, String toAccount, BigDecimal amount, String walletType) {
        transactionService.transfer(fromAccount,toAccount,amount,walletType);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/transaction-history")
    ResponseEntity<List<TransactionDto>> getTransactionHistory(String AccountNumber) {
        List<TransactionDto> response = transactionService.getTransactionHistory(AccountNumber);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
