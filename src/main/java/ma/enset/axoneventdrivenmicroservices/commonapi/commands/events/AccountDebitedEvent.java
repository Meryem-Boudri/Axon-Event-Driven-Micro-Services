package ma.enset.axoneventdrivenmicroservices.commonapi.commands.events;

import lombok.Getter;

public class AccountDebitedEvent extends BaseEvent<String>{

    @Getter
    private Double amount;
    @Getter private String currency;


    public AccountDebitedEvent(String id , Double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
