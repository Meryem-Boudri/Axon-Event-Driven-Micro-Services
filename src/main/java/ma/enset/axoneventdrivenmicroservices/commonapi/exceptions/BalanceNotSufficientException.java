package ma.enset.axoneventdrivenmicroservices.commonapi.exceptions;

public class BalanceNotSufficientException extends RuntimeException {
    public BalanceNotSufficientException(String balanceNotSufficantException) {
        super(balanceNotSufficantException);
    }
}
