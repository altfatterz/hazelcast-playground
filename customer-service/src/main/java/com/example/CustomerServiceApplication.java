package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    @GetMapping(value = "/customers/{id}")
    public Customer findByCustomerId(@PathVariable String id) {
        return customerRepository.findCustomerById(id);
    }

    @PutMapping(value = "/customers/{id}")
    public void updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        customerRepository.updateCustomer(id, customer);
    }
}

@Component
@CacheConfig(cacheNames = "customers")
@Slf4j
class CustomerRepository {

    private Map<String, Customer> customers = new ConcurrentHashMap<String, Customer>();

    public CustomerRepository() {
        customers.put("1", new Customer("1", "John Doe"));
        customers.put("2", new Customer("2,", "Jane Doe"));
    }

    @Cacheable
    public Customer findCustomerById(String id) {
        log.info("Loading customer with id '{}' into cache", id);
        return customers.get(id);
    }

    @CacheEvict(key = "#root.args[0]")
    public void updateCustomer(String id, Customer customer) {
        log.info("Removing customer with id '{}' from the cache", id);
        customers.put(id, customer);
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Customer implements Serializable {

    private String id;
    private String name;
}