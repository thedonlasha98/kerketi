package ge.kerketi.task.service;

import ge.kerketi.task.domain.Client;
import ge.kerketi.task.domain.Wallet;
import ge.kerketi.task.model.ClientDto;
import ge.kerketi.task.repository.ClientRepository;
import ge.kerketi.task.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static ge.kerketi.task.utils.CCY.*;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    private WalletRepository walletRepository;

    public ClientServiceImpl(ClientRepository clientRepository, WalletRepository walletRepository) {
        this.clientRepository = clientRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String,String> registerClient(ClientDto clientDto) {

        Set<String> currencies = new HashSet<>(Arrays.asList(GEL.name(), USD.name(), EUR.name()));
        Set<Wallet> wallets = new HashSet<>();

        Client client = ClientDto.toEntity(clientDto);
        String accountNumber = clientRepository.getUniqueAccountNumber().toString();
        client.setAccountNumber(accountNumber);
        clientRepository.save(client);

        for (String ccy : currencies) {
            Wallet wallet = new Wallet();
            wallet.setClientId(client.getId());
            wallet.setWalletType(ccy);
            wallet.setBalanceAvailable(BigDecimal.ZERO);

            wallets.add(wallet);
        }
        walletRepository.saveAll(wallets);

        Map<String,String> response = new HashMap<>();
        {
            response.put("AccountNumber",client.getAccountNumber());
        }

        return response;
    }

}
