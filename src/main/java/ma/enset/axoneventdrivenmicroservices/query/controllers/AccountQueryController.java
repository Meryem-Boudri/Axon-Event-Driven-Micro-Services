package ma.enset.axoneventdrivenmicroservices.query.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.axoneventdrivenmicroservices.commonapi.queries.GetAccountQuery;
import ma.enset.axoneventdrivenmicroservices.commonapi.queries.GetAllAccountQuery;
import ma.enset.axoneventdrivenmicroservices.query.entities.Account;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/query/accounts")
@AllArgsConstructor
@Slf4j
public class AccountQueryController {
    private QueryGateway queryGateway;
@GetMapping("/allAccounts")
    public List<Account> accountList(){
        List<Account> response = queryGateway.query(new GetAllAccountQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join(); // qui ce que j'attend
return response;
    }

    @GetMapping("/{accoundId}")
    public Account account(@PathVariable String accoundId){
        Account response = queryGateway.query(new GetAccountQuery(accoundId), ResponseTypes.instanceOf(Account.class)).join(); // qui ce que j'attend
        return response;
    }

}
