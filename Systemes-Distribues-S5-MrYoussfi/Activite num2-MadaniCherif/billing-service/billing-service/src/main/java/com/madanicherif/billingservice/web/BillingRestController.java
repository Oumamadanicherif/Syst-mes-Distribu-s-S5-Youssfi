package com.madanicherif.billingservice.web;

import com.madanicherif.billingservice.entities.Bill;
import com.madanicherif.billingservice.entities.Productitem;
import com.madanicherif.billingservice.feign.CustomerRestClient;
import com.madanicherif.billingservice.feign.ProductitemRestClient;
import com.madanicherif.billingservice.model.Customer;
import com.madanicherif.billingservice.model.Product;
import com.madanicherif.billingservice.repository.BillRepository;
import com.madanicherif.billingservice.repository.ProductitemRepository;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class BillingRestController {
    private BillRepository billRepository;
    private ProductitemRepository productitemRepository;
    private CustomerRestClient customerRestClient;
    private ProductitemRestClient productitemRestClient;

    public BillingRestController(BillRepository billRepository, ProductitemRepository productitemRepository, CustomerRestClient customerRestClient, ProductitemRestClient productitemRestClient) {
        this.billRepository = billRepository;
        this.productitemRepository = productitemRepository;
        this.customerRestClient = customerRestClient;
        this.productitemRestClient = productitemRestClient;
    }
    @GetMapping(path = "/fullBill/{id}")
    public Bill getBill(@PathVariable(name="id") Long id){
     Bill bill= billRepository.findById(id).get();
     Customer customer= customerRestClient.getCustomerById(bill.getCustomerID());
     bill.setCustomer(customer);
     bill.getProductitems().forEach(pi ->{
         Product product = productitemRestClient.getProductById(pi.getProductID());
         //pi.setProduct(product);
         pi.setProductName(product.getName());
     });
     return bill;
    }
}
