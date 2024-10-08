package com.accenture.next.customerapi.service;


import static com.accenture.next.customerapi.constant.ErrorConstants.CUSTOMER_NOT_FOUND_CODE;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.accenture.next.customerapi.converter.CustomerResponseConverter;
import com.accenture.next.customerapi.entity.Customer;
import com.accenture.next.customerapi.exception.NotFoundException;
import com.accenture.next.customerapi.repository.CustomerRepository;
import com.accenture.next.customerapi.resources.response.CustomerResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class CustomerService {

  private final CustomerResponseConverter customerResponseConverter;

  private final CustomerRepository customerRepository;


  public CustomerService (
      CustomerResponseConverter fulfillmentResponseConverter,
      CustomerRepository customerRepository
  ) {

    this.customerResponseConverter = fulfillmentResponseConverter;
    this.customerRepository = customerRepository;
  }


  public Mono<List<CustomerResponse>> getCustomers (String customerType) {

    return customerRepository.findByCustomerType(customerType)
        .doFirst(() -> log.debug("Processing get customer request for customerType : {}", customerType))
        .switchIfEmpty(
            Mono.error(new NotFoundException(new HttpClientErrorException(HttpStatus.NOT_FOUND),
                                             CUSTOMER_NOT_FOUND_CODE, customerType
            )))
        .collectList()
        .flatMap(customerResponseConverter::buildCustomerResponse);
  }


  //Streaming
  public Flux<Customer> getCustomersFlux (String customerType) {

    return customerRepository.findByCustomerType(customerType)
        .doFirst(() -> log.debug("Processing get customer request for customerType : {}", customerType))
        .switchIfEmpty(
            Mono.error(new NotFoundException(new HttpClientErrorException(HttpStatus.NOT_FOUND),
                                             CUSTOMER_NOT_FOUND_CODE, customerType
            )));
  }

}
