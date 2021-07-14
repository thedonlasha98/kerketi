package ge.kerketi.task.service;

import ge.kerketi.task.domain.Client;
import ge.kerketi.task.domain.TransactionHistory;
import ge.kerketi.task.domain.Wallet;
import ge.kerketi.task.exception.GeneralException;
import ge.kerketi.task.model.ClientDto;
import ge.kerketi.task.model.TransactionDto;
import ge.kerketi.task.repository.ClientRepository;
import ge.kerketi.task.repository.TransactionHistoryRepository;
import ge.kerketi.task.repository.WalletRepository;
import ge.kerketi.task.utils.Enums;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ge.kerketi.task.exception.ErrorMessage.COULD_NOT_FOUND_CLIENT_BY_PID;
import static ge.kerketi.task.exception.ErrorMessage.COULD_NOT_FOUND_WALLET;
import static ge.kerketi.task.utils.CCY.*;

@Service
public class ApiServiceImpl implements ApiService {

    private ClientRepository clientRepository;

    private WalletRepository walletRepository;

    private TransactionHistoryRepository transactionHistoryRepository;

    public ApiServiceImpl(ClientRepository clientRepository, WalletRepository walletRepository, TransactionHistoryRepository transactionHistoryRepository) {
        this.clientRepository = clientRepository;
        this.walletRepository = walletRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public String registerClient(ClientDto clientDto) {

        Set<String> currencies = new HashSet<>(Arrays.asList(GEL.name(), USD.name(), EUR.name()));
        Set<Wallet> wallets = new HashSet<>();

        Client client = ClientDto.toEntity(clientDto);
        client.setAccountNumber(String.valueOf(Math.round(Math.random())));
        clientRepository.save(client);

        for (String ccy : currencies) {
            Wallet wallet = new Wallet();
            wallet.setClientId(client.getId());
            wallet.setWalletType(ccy);
            wallet.setBalanceAvailable(BigDecimal.ZERO);

            wallets.add(wallet);
        }
        walletRepository.saveAll(wallets);

        return client.getAccountNumber();
    }

    @Override
    public BigDecimal cashIn(String pid, String accountNumber, BigDecimal amount, String walletType) {
        Client client = clientRepository.findByPid(pid).orElseThrow(() -> new GeneralException(COULD_NOT_FOUND_CLIENT_BY_PID));
        Wallet wallet = walletRepository.findByClientIdAndWalletType(client.getId(), walletType).orElseThrow(() -> new GeneralException(COULD_NOT_FOUND_WALLET));

        wallet.setBalanceAvailable(wallet.getBalanceAvailable().add(amount));
        walletRepository.save(wallet);

        createTransaction(amount, Enums.KERKETI.name(), Enums.KERKETI.name(), accountNumber);

        return wallet.getBalanceAvailable();
    }

    @Override
    public void transfer(String fromAccount, String toAccount, BigDecimal amount, String walletType) {
        Client fromClient = clientRepository.findByAccountNumber(fromAccount).orElseThrow(() -> new GeneralException(COULD_NOT_FOUND_CLIENT_BY_PID));
        Client toClient = clientRepository.findByAccountNumber(fromAccount).orElseThrow(() -> new GeneralException(COULD_NOT_FOUND_CLIENT_BY_PID));
    }

    @Override
    public List<TransactionDto> getTransactionHistory(String accountNumber) {
        List<TransactionHistory> transactionHistories;

        if (StringUtils.isEmpty(accountNumber)) {
            transactionHistories = (List<TransactionHistory>) transactionHistoryRepository.findAll();
        }
        else {
            transactionHistories = transactionHistoryRepository.findByFromOrToOrderByDateDesc(accountNumber,accountNumber);
        }
        return transactionHistories.stream()
                .map(TransactionDto::toDto)
                .collect(Collectors.toList());
    }

    public void createTransaction(BigDecimal amount, String initiator, String from, String to) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAmount(amount);
        transactionHistory.setFrom(from);
        transactionHistory.setTo(to);
        transactionHistory.setInitiator(initiator);
        transactionHistory.setDate(LocalDateTime.now());

        transactionHistoryRepository.save(transactionHistory);
    }
}
