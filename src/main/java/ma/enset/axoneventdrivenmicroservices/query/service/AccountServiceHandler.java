package ma.enset.axoneventdrivenmicroservices.query.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.axoneventdrivenmicroservices.commonapi.commands.events.AccountActivatedEvent;
import ma.enset.axoneventdrivenmicroservices.commonapi.commands.events.AccountCreatedEvent;
import ma.enset.axoneventdrivenmicroservices.commonapi.commands.events.AccountCreditedEvent;
import ma.enset.axoneventdrivenmicroservices.commonapi.commands.events.AccountDebitedEvent;
import ma.enset.axoneventdrivenmicroservices.commonapi.enums.OperationType;
import ma.enset.axoneventdrivenmicroservices.commonapi.queries.GetAccountQuery;
import ma.enset.axoneventdrivenmicroservices.commonapi.queries.GetAllAccountQuery;
import ma.enset.axoneventdrivenmicroservices.query.entities.Account;
import ma.enset.axoneventdrivenmicroservices.query.entities.Operation;
import ma.enset.axoneventdrivenmicroservices.query.repository.AccountRepository;
import ma.enset.axoneventdrivenmicroservices.query.repository.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AccountServiceHandler {
    //ecouter les evenements

    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    @EventHandler
    public void on(AccountCreatedEvent event) {
        log.info("************");
        log.info("AccountCreatedEvent received");
        accountRepository.save(new Account(
                event.getId(),
                event.getInitialBalance(),
                event.getStatus(),
                event.getCurrency(),
                null

        ));



    }
    @EventHandler
    public void on(AccountActivatedEvent event) {
        log.info("************");
        log.info("AccountActivatedEvent received");
        Account account = accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent event) {
        log.info("************");
        log.info("AccountDebitedEvent received");
        Account account = accountRepository.findById(event.getId()).get();

        Operation operation=new Operation();
        operation.setAccount(account);
        operation.setAmount(event.getAmount());
        operation.setDate(new Date()); //doit la recevoire de event
        operation.setOperationType(OperationType.DEBIT);
        operationRepository.save(operation);

        account.setBalance(account.getBalance() - event.getAmount());
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountCreditedEvent event) {
        log.info("************");
        log.info("AccountCreditedEvent received");
        Account account = accountRepository.findById(event.getId()).get();

        Operation operation=new Operation();
        operation.setAccount(account);
        operation.setAmount(event.getAmount());
        operation.setDate(new Date()); //doit la recevoire de event
        operation.setOperationType(OperationType.CREDIT);
        operationRepository.save(operation);

        account.setBalance(account.getBalance() + event.getAmount());
        accountRepository.save(account);
    }


    @QueryHandler
    public List<Account> on (GetAllAccountQuery query){
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account on (GetAccountQuery query){
        return accountRepository.findById(query.getAccountId()).get();
    }


}
