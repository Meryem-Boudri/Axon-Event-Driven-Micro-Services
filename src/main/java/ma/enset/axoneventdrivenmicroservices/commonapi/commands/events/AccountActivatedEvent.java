package ma.enset.axoneventdrivenmicroservices.commonapi.commands.events;

import lombok.Getter;
import ma.enset.axoneventdrivenmicroservices.commonapi.enums.AccountStatus;

public class AccountActivatedEvent extends BaseEvent<String>{
    @Getter
    private AccountStatus status;


    public AccountActivatedEvent(String id , AccountStatus status) {

        super(id);
        this.status = status;
    }

}
