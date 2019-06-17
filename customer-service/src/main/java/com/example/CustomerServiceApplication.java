package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@SpringBootApplication
@EnableCaching
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

}

@RestController
class CustomerRestController {

    private final CustomerRepository customerRepository;

    public CustomerRestController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping(value = "/customers", params = "id")
    public Customer findByCustomerId(@RequestParam String id) {
        return customerRepository.findCustomerById(id);
    }
}

@Component
@CacheConfig(cacheNames = "customers")
class CustomerRepository {

    @Cacheable
    public Customer findCustomerById(String id) {
        System.out.println("Loading customer with id " + id);
        return new Customer(id);
    }

}

@Data
@AllArgsConstructor
class Customer implements Serializable {

    private String id;
}