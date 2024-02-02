package com.mycompany.customer;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Integer id) {
        super("No customer was found with id: " + id );
    }
}

