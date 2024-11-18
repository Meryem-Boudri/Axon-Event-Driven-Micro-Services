package ma.enset.axoneventdrivenmicroservices.commonapi.commands;

import lombok.Getter;

public class CreditAccountCommand extends BaseCommand<String> {

    @Getter private Double amount;
    @Getter private String currency;


    public CreditAccountCommand(String id, Double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;

    }
}
