package com.romeulima.daojdbc.db.exceptions;

public class DbIntegrityException extends RuntimeException{

    public DbIntegrityException(String message) {
        super(message);
    }
}
