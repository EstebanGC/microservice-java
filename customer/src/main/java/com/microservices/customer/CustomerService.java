    package com.microservices.customer;

    import org.springframework.stereotype.Service;

    @Service
    public record CustomerService(CustomerRepository customerRepository) {
        public void registerCustomer(CustomerRegistrationRequest request) {
            Customer customer = Customer.builder()
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .email(request.email())
                    .build();
            //to do: check if email is valid (RegEx)
            //to do: check if email is not taken
            //store customer in db
            customerRepository.saveAndFlush(customer);
        }

        public Customer findById(Integer id) {
            return customerRepository.findById(id)
                    .orElseThrow(() -> new CustomerNotFoundException(id));
        }

        public void deleteCustomer(Integer id) {
            Customer customer = findById(id);
            customerRepository.delete(customer);
        }
    }
