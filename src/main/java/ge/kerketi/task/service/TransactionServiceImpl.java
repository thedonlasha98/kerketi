package ge.kerketi.task.service;

import ge.kerketi.task.domain.Client;
import ge.kerketi.task.domain.TransactionHistory;
import ge.kerketi.task.domain.Wallet;
import ge.kerketi.task.exception.GeneralException;
import ge.kerketi.task.model.TransactionDto;
import ge.kerketi.task.repository.ClientRepository;
import ge.kerketi.task.repository.TransactionHistoryRepository;
import ge.kerketi.task.repository.WalletRepository;
import ge.kerketi.task.utils.Enums;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ge.kerketi.task.exception.ErrorMessage.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    private ClientRepository clientRepository;

    private WalletRepository walletRepository;

    private TransactionHistoryRepository transactionHistoryRepository;

    public TransactionServiceImpl(ClientRepository clientRepository, WalletRepository walletRepository, TransactionHistoryRepository transactionHistoryRepository) {
        this.clientRepository = clientRepository;
        this.walletRepository = walletRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, BigDecimal> cashIn(String pid, String accountNumber, BigDecimal amount, String walletType) {
        Client client = clientRepository.findByPid(pid).orElseThrow(() -> new GeneralException(COULD_NOT_FOUND_CLIENT_BY_PID));
        Wallet wallet = walletRepository.findByClientIdAndWalletType(client.getId(), walletType).orElseThrow(() -> new GeneralException(COULD_NOT_FOUND_WALLET));

        wallet.setBalanceAvailable(wallet.getBalanceAvailable().add(amount));
        walletRepository.save(wallet);

        createTransaction(amount, Enums.KERKETI.name(), Enums.KERKETI.name(), accountNumber);

        Map<String,BigDecimal> response = new HashMap<>();
        {
            response.put("Balance",wallet.getBalanceAvailable());
        }

        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void transfer(String fromAccount, String toAccount, BigDecimal amount, String walletType) {
        Wallet fromWallet = walletRepository.getWalletByAccountNumberAndWalletType(fromAccount, walletType)
                .orElseThrow(() -> new GeneralException(COULD_NOT_FOUND_WALLET.getDescription() + " " + fromAccount));
        Wallet toWallet = walletRepository.getWalletByAccountNumberAndWalletType(toAccount, walletType)
                .orElseThrow(() -> new GeneralException(COULD_NOT_FOUND_WALLET.getDescription() + " " + toAccount));

        if (fromWallet.getBalanceAvailable().compareTo(amount) < 0) {
            throw new GeneralException(NOT_ENOUGH_BALANCE);
        } else {
            fromWallet.setBalanceAvailable(fromWallet.getBalanceAvailable().subtract(amount));
            toWallet.setBalanceAvailable(toWallet.getBalanceAvailable().add(amount));
            Set<Wallet> wallets = new HashSet<>(Arrays.asList(fromWallet, toWallet));
            walletRepository.saveAll(wallets);

            createTransaction(amount, fromAccount, fromAccount, toAccount);
        }
    }

    @Override
    public List<TransactionDto> getTransactionHistory(String accountNumber) {
        List<TransactionHistory> transactionHistories;

        if (StringUtils.isEmpty(accountNumber)) {
            transactionHistories = (List<TransactionHistory>) transactionHistoryRepository.findAll();
        } else {
            transactionHistories = transactionHistoryRepository.findByFromOrToOrderByDateDesc(accountNumber, accountNumber);
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
