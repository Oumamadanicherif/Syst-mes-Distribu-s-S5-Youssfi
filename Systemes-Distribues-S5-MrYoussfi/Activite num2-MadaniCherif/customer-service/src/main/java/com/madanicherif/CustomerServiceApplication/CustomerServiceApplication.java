package com.madanicherif.CustomerServiceApplication;

import com.madanicherif.CustomerServiceApplication.entities.Customer;
import com.madanicherif.CustomerServiceApplication.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository, RepositoryRestConfiguration restConfiguration ){
        restConfiguration.exposeIdsFor(Customer.class);
        return args -> {
            customerRepository.save(new Customer(null,"Oumaima","oumaimamc@gmail.com"));
            customerRepository.save(new Customer(null,"Salma","salma@gmail.com"));
            customerRepository.save(new Customer(null,"Said","said@gmail.com"));
            customerRepository.save(new Customer(null,"Khadija","khadija@gmail.com"));
            customerRepository.findAll().forEach(
                    c-> System.out.println(c.toString())
            );
        };
    }

}
