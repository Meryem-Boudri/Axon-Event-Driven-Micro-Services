package ma.enset.axoneventdrivenmicroservices.commonapi.commands.events;

import lombok.Getter;
import ma.enset.axoneventdrivenmicroservices.commonapi.enums.AccountStatus;

public class AccountCreatedEvent extends BaseEvent<String>{
   @Getter private Double initialBalance;
   @Getter private String currency;
  @Getter private AccountStatus status ;

    public AccountCreatedEvent(String id , Double initialBalance, String currency , AccountStatus status) {

        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
        this.status = status;
    }

}
