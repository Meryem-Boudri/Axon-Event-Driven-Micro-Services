package ma.enset.axoneventdrivenmicroservices.commonapi.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data@NoArgsConstructor
public class GetAccountQuery {
    private String accountId;
}
