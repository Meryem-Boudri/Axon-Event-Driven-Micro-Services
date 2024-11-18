package ma.enset.axoneventdrivenmicroservices.commands.controllers;

import lombok.AllArgsConstructor;
import ma.enset.axoneventdrivenmicroservices.commonapi.commands.CreateAccountCommand;
import ma.enset.axoneventdrivenmicroservices.commonapi.commands.CreditAccountCommand;
import ma.enset.axoneventdrivenmicroservices.commonapi.commands.DebitAccountCommand;
import ma.enset.axoneventdrivenmicroservices.commonapi.dto.CreateAccountRequestDTO;
import ma.enset.axoneventdrivenmicroservices.commonapi.dto.CreditAccountRequestDTO;
import ma.enset.axoneventdrivenmicroservices.commonapi.dto.DebitAccountRequestDTO;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/command/account")
@AllArgsConstructor
public class AccountCommandController {

    private CommandGateway commandGateway; //interagit avec bus de command puis communique avec l'aggregate
    private EventStore eventStore;
    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO requestDTO) {
        CompletableFuture<String> commandResponse = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                requestDTO.getInitialBalance(),
                requestDTO.getCurrency()
        )); //command handler
        return commandResponse;

    }

    @PutMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO requestDTO) {
        CompletableFuture<String> commandResponse = commandGateway.send(new CreditAccountCommand(
                requestDTO.getAccountId(),
                requestDTO.getAmount(),
                requestDTO.getCurrency()
        )); //command handler
        return commandResponse;

    }

    @PutMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO requestDTO) {
        CompletableFuture<String> commandResponse = commandGateway.send(new DebitAccountCommand(
                requestDTO.getAccountId(),
                requestDTO.getAmount(),
                requestDTO.getCurrency()
        )); //command handler
        return commandResponse;

    }
    @ExceptionHandler
    public ResponseEntity<String> exceptionHandler(Exception exception){
        ResponseEntity <String> response = new ResponseEntity<>(exception.getMessage() ,
                HttpStatus.INTERNAL_SERVER_ERROR);
        return response;

    }

    @GetMapping("/evenStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId) {
        return eventStore.readEvents(accountId).asStream();
    }


}
