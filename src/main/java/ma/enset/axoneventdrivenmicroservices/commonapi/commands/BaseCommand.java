package ma.enset.axoneventdrivenmicroservices.commonapi.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public abstract class BaseCommand<T> {
    @TargetAggregateIdentifier //identifiant de l'aggrigate pour effectuer la commande
    @Getter
    private T id;
    public BaseCommand(T id) {
        this.id = id;
    }

}
