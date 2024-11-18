package ma.enset.axoneventdrivenmicroservices.commonapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor
public class CreateAccountRequestDTO {
    private Double initialBalance;
    private String currency;

}
