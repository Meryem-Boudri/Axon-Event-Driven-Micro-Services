package ma.enset.axoneventdrivenmicroservices.commonapi.dto;

import lombok.Data;

@Data
public class CreditAccountRequestDTO {
    private String accountId;
    private Double amount;
    private String currency;
}
