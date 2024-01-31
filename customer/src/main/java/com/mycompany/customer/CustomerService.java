package com.mycompany.customer;

public record CustomerService(CustomerRespository customerRespository) {
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        //to do: check if email is valid (RegEx)
        //to do: check if email is not taken
        //store customer in db
        customerRespository.save(customer);
    }
}
