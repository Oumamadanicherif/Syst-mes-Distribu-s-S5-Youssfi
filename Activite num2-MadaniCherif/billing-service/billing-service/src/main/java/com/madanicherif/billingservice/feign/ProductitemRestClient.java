package com.madanicherif.billingservice.feign;

import com.madanicherif.billingservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE-OMC")
public interface ProductitemRestClient {
    @GetMapping(path="/products")
    PagedModel<Product> pageProducts(
//            @RequestParam(name = "page") int page,
//            @RequestParam(name = "size") int size
    );
    @GetMapping(path = "/products/{id}")
    Product getProductById(@PathVariable  Long id);
}
