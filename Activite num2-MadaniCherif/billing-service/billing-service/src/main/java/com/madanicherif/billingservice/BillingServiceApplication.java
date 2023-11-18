package com.madanicherif.billingservice;

import com.madanicherif.billingservice.entities.Bill;
import com.madanicherif.billingservice.entities.Productitem;
import com.madanicherif.billingservice.feign.CustomerRestClient;
import com.madanicherif.billingservice.feign.ProductitemRestClient;
import com.madanicherif.billingservice.model.Customer;
import com.madanicherif.billingservice.model.Product;
import com.madanicherif.billingservice.repository.BillRepository;
import com.madanicherif.billingservice.repository.ProductitemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}
	@Bean
   CommandLineRunner start(BillRepository billRepository,
						   ProductitemRepository productitemRepository,
						   CustomerRestClient customerRestClient,
						   ProductitemRestClient productitemRestClient){
		return args -> {
			Customer customer= customerRestClient.getCustomerById(1L);
			Bill bill = billRepository.save(new Bill(null,new Date(),null,customer.getId(),null));
			PagedModel<Product> pagedModel= productitemRestClient.pageProducts();
			pagedModel.forEach(product -> {
				Productitem productitem= new Productitem();
				productitem.setPrice(product.getPrice());
				productitem.setQuantity(1+new Random().nextInt(100));
                productitem.setBill(bill);
				productitem.setProductID(product.getId());
				productitemRepository.save(productitem);
			});
		};
   }
}
