package ma.enset.axoneventdrivenmicroservices.commonapi.dto;

import lombok.Data;

@Data
public class DebitAccountRequestDTO {
    private String accountId;
    private Double amount;
    private String currency;
}
