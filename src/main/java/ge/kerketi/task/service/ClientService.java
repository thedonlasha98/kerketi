package ge.kerketi.task.service;

import ge.kerketi.task.model.ClientDto;

import java.util.Map;

public interface ClientService {

    Map<String,String> registerClient(ClientDto clientDto);

}
