package com.bdccenset.comptecqrses.commands.Controllers;


import com.bdccenset.comptecqrses.common_api.command.CreateAccountCommand;
import com.bdccenset.comptecqrses.common_api.dtos.CreateAccountDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor
public class AccountCommandController {
    private CommandGateway commandGateway;
    @PostMapping(path = "/create")
    public CompletableFuture<String> createAccount(@RequestBody  CreateAccountDTO request){
     CompletableFuture<String> commandResponse= commandGateway.send(new CreateAccountCommand(
             UUID.randomUUID().toString(),
             request.getInitialBalance(),
             request.getCurrency()
     ));
     return commandResponse;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        ResponseEntity<String> entity= new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return entity;
    }
}
