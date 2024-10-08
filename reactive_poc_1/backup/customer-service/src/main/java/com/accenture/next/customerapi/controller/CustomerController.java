package com.accenture.next.customerapi.controller;


import static com.accenture.next.customerapi.constant.ErrorConstants.INVALID_FIELD_CODE;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.next.customerapi.annotation.EnumPattern;
import com.accenture.next.customerapi.entity.Customer;
import com.accenture.next.customerapi.enums.CustomerType;
import com.accenture.next.customerapi.resources.response.CustomerResponse;
import com.accenture.next.customerapi.service.CustomerService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/***
 * https://howtodoinjava.com/spring-webflux/spring-webflux-tutorial/
 * @author ram.prakash.singh
 *
 */
@RestController
@RequestMapping("/customer-service")
@Slf4j
@Validated
public class CustomerController {

  private final CustomerService customerService;


  public CustomerController (CustomerService customerService) {

    this.customerService = customerService;
  }


  @GetMapping(value = "/{customerType}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<List<CustomerResponse>>> getCustomers (
      @PathVariable @EnumPattern(targetClassType = CustomerType.class, message = INVALID_FIELD_CODE) String customerType
  ) {

    return customerService.getCustomers(customerType)
        .doFirst(() -> log.info("Request received to get customer details for customerTpe : {}", customerType))
        .map(customerResponse -> ResponseEntity.ok().body(customerResponse))
        .doOnSuccess(id -> log.info("Fetched customer details for customerType successfully : {}", customerType))
        .doOnError(throwable -> log.error(
            "Error occurred for customerType {} : {}",
            customerType,
            throwable.getMessage()
        ));
  }

  //Streaming with ndjson
  /*
  @GetMapping(value = "/{customerType}", produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Flux<Customer> getCustomersFlux(
      @PathVariable @EnumPattern(targetClassType = CustomerType.class, message = INVALID_FIELD_CODE) String customerType) {
    return customerService.getCustomersFlux(customerType);
  } */

}
