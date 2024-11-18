package ma.enset.axoneventdrivenmicroservices.commonapi.commands.events;

import lombok.Getter;

public class AccountCreditedEvent extends BaseEvent<String>{
   @Getter private Double amount;
   @Getter private String currency;

    public AccountCreditedEvent(String id , Double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
