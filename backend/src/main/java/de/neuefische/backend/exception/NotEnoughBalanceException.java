package de.neuefische.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotEnoughBalanceException extends RuntimeException{

    public NotEnoughBalanceException(double accountBalance){
        super("Not enough balance" + accountBalance);
    }
}
