package ma.enset.axoneventdrivenmicroservices.commonapi.commands;

import lombok.Getter;

public class CreateAccountCommand extends BaseCommand<String>{
   @Getter
   private Double initialBalance;
    @Getter
    private String currency;


    public CreateAccountCommand(String id, Double initialBalance, String currency) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;

    }
}
