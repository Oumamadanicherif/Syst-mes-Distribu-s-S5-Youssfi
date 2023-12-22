package com.bdccenset.comptecqrses.commands.Aggregates;

import com.bdccenset.comptecqrses.common_api.command.CreateAccountCommand;
import com.bdccenset.comptecqrses.common_api.enums.AccountStatus;
import com.bdccenset.comptecqrses.common_api.events.AccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus accountStatus;

    public AccountAggregate() {
        //Required by AXON
    }
    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        //Required by AXON
        if(createAccountCommand.getInitialBalance()<0) throw new RuntimeException("Impossible .....");
        //OK
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getInitialBalance(),
                createAccountCommand.getCurrency()
        ));
     }
    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.accountId= event.getId();
        this.balance= event.getInitialBalance();
        this.currency=event.getCurrency();
        this.accountStatus=AccountStatus.CREATED;

    }
}
