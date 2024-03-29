    package com.microservices.customer;

    import lombok.AllArgsConstructor;
    import org.springframework.stereotype.Service;
    import org.springframework.web.client.RestTemplate;

    @Service
    @AllArgsConstructor
    public class CustomerService {

        private final CustomerRepository customerRepository;
        //Rest Template used to connect microservices
        private final RestTemplate restTemplate;

            public void registerCustomer(CustomerRegistrationRequest request) {
            Customer customer = Customer.builder()
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .email(request.email())
                    .build();
            //to do: check if email is valid (RegEx)
            //to do: check if email is not taken
                customerRepository.saveAndFlush(customer);

                //Connecting fraud microservices to this one
                FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                        "http://localhost:8081/api/v1/fraud-check/{customerId}",
                        FraudCheckResponse.class,
                        customer.getId());

                if (fraudCheckResponse.isFraudster()) {
                    throw new IllegalStateException("fraudster");
                }
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
