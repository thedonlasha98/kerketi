package ge.kerketi.task.controller;

import ge.kerketi.task.model.ClientDto;
import ge.kerketi.task.model.TransactionDto;
import ge.kerketi.task.service.ApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/register-client")
    ResponseEntity<String> registerClient(@RequestBody ClientDto clientDto) {
        String response = apiService.registerClient(clientDto);

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("/cash-in")
    ResponseEntity<Void> cashIn(String pid, String accountNumber, BigDecimal amount, String walletType) {
        apiService.cashIn(pid,accountNumber,amount,walletType);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/transfer")
    ResponseEntity<Void> transfer(String fromAccount, String toAccount, BigDecimal amount, String walletType) {
        apiService.transfer(fromAccount,toAccount,amount,walletType);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/transaction-history")
    ResponseEntity<List<TransactionDto>> getTransactionHistory(String AccountNumber) {
        List<TransactionDto> response = apiService.getTransactionHistory(AccountNumber);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
