package an.kte.service;

import an.kte.model.Client;
import an.kte.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class ClientService implements CommonService<Client> {
    @Autowired
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        Client client1 = Client
                .builder()
                .name("Alex")
                .discount1(.05)
                .discount2(.1)
                .build();
        Client client2 = Client
                .builder()
                .name("Ivan")
                .discount1(.01)
                .discount2(.2)
                .build();
        clientRepository.save(client1);
        clientRepository.save(client2);
    }

    @Override
    public ListCrudRepository<Client, Long> getRepository() {
        return clientRepository;
    }
}
