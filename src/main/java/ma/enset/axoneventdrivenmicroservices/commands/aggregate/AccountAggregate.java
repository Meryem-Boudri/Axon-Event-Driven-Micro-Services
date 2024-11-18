package ma.enset.axoneventdrivenmicroservices.commands.aggregate;

import ma.enset.axoneventdrivenmicroservices.commonapi.commands.CreateAccountCommand;
import ma.enset.axoneventdrivenmicroservices.commonapi.commands.CreditAccountCommand;
import ma.enset.axoneventdrivenmicroservices.commonapi.commands.DebitAccountCommand;
import ma.enset.axoneventdrivenmicroservices.commonapi.commands.events.AccountActivatedEvent;
import ma.enset.axoneventdrivenmicroservices.commonapi.commands.events.AccountCreatedEvent;
import ma.enset.axoneventdrivenmicroservices.commonapi.commands.events.AccountCreditedEvent;
import ma.enset.axoneventdrivenmicroservices.commonapi.commands.events.AccountDebitedEvent;
import ma.enset.axoneventdrivenmicroservices.commonapi.enums.AccountStatus;
import ma.enset.axoneventdrivenmicroservices.commonapi.exceptions.AmountNegativeException;
import ma.enset.axoneventdrivenmicroservices.commonapi.exceptions.BalanceNotSufficientException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId ;
    private Double balance ;
    private String currency ;
    private AccountStatus status ;


    public AccountAggregate() {
        //required
    }

    @CommandHandler //subscribe on command bus
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        //logic metier
        if (createAccountCommand.getInitialBalance()<0)throw new RuntimeException("initial balance is negative");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getInitialBalance(),
                createAccountCommand.getCurrency(),
                AccountStatus.CREATED

        ));

    }
    @EventSourcingHandler //evolution function
    public void on(AccountCreatedEvent accountCreatedEvent) {
this.accountId = accountCreatedEvent.getId() ;
        this.balance = accountCreatedEvent.getInitialBalance();
        this.currency = accountCreatedEvent.getCurrency();
        this.status = AccountStatus.CREATED ;
        AggregateLifecycle.apply(
                new AccountActivatedEvent(accountCreatedEvent.getId(),
                AccountStatus.ACTIVATED));

    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent accountActivatedEvent) {
        this.status=accountActivatedEvent.getStatus();

    }

    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCommand){
        if(creditAccountCommand.getAmount() < 0) throw  new AmountNegativeException("Amount should not be negative");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                creditAccountCommand.getId(),
                creditAccountCommand.getAmount(),
                creditAccountCommand.getCurrency()

        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent accountCreditedEvent) {
        this.balance += accountCreditedEvent.getAmount();

    }

    @CommandHandler
    public void handle(DebitAccountCommand debitAccountCommand){
        if(debitAccountCommand.getAmount() < 0) throw  new AmountNegativeException("Amount should not be negative");
        if(this.balance < debitAccountCommand.getAmount()) throw  new BalanceNotSufficientException("Balance not sufficant Exception "  +balance);

        AggregateLifecycle.apply(new AccountCreditedEvent(
                debitAccountCommand.getId(),
                debitAccountCommand.getAmount(),
                debitAccountCommand.getCurrency()

        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent accountDebitedEvent) {
        this.balance -= accountDebitedEvent.getAmount();

    }
}
