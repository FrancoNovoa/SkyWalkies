package com.mindhub.skywalkies.Controllers;

import com.mindhub.skywalkies.Service.AvatarService;
import com.mindhub.skywalkies.Service.ClientService;
import com.mindhub.skywalkies.Service.Client_orderService;
import com.mindhub.skywalkies.Service.Ordered_productService;
import com.mindhub.skywalkies.dtos.ClientDTO;
import com.mindhub.skywalkies.dtos.Client_orderDTO;
import com.mindhub.skywalkies.dtos.Ordered_productDTO;
import com.mindhub.skywalkies.models.Avatar;
import com.mindhub.skywalkies.models.Bill;
import com.mindhub.skywalkies.models.Client;
import com.mindhub.skywalkies.models.Ordered_product;
import com.mindhub.skywalkies.repositories.ClientRepository;
import com.mindhub.skywalkies.repositories.Client_OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AvatarService avatarService;

    @Autowired
    private Client_orderService client_orderService;

    @Autowired
    private ClientService clientService;


    @RequestMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.getAllClients().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>(
                    "you must fill in the fields", HttpStatus.FORBIDDEN);
        }
        if (clientService.findClientByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        Avatar avatar = new Avatar(1, 1, 1, 1, 1);
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password), false, new Bill(), avatar);
        clientService.saveClient(client);
        avatarService.saveAvatar(avatar);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/clients/current")
    public ClientDTO getAll(Authentication authentication) {
        return new ClientDTO(clientService.findClientByEmail(authentication.getName()));
    }

    @GetMapping("/clients/clientOrder")
    public List<Client_orderDTO> getClientOrders(){
        return client_orderService.getAllClientsOrders().stream().map(client_order -> new Client_orderDTO(client_order)).collect(Collectors.toList());

    }


}