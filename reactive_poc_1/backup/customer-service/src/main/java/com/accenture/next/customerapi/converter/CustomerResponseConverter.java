package com.accenture.next.customerapi.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.accenture.next.customerapi.entity.Customer;
import com.accenture.next.customerapi.resources.response.CustomerResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomerResponseConverter {

  public Mono<List<CustomerResponse>> buildCustomerResponse(List<Customer> customerList) {
    return Flux.fromIterable(customerList)
        .doFirst(() -> log.debug("Converting customerList to Customer response"))
        .map(this::buildCustomerResponse)
        .collectList();
  }

  public CustomerResponse buildCustomerResponse(Customer customer) {
    return CustomerResponse.builder()
        .id(customer.getId())
        .name(customer.getName())
        .customerType(customer.getCustomerType())
        .customerCode(customer.getCustomerCode())
        .enabled(customer.getEnabled())
        .products(customer.getProducts())
        .createdBy(customer.getCreatedBy())
        .createdDate(localDateTimeToString(customer.getCreatedDate()))
        .modifiedBy(customer.getModifiedBy())
        .modifiedDate(localDateTimeToString(customer.getModifiedDate()))
        .build();
  }

  private static String localDateTimeToString(LocalDateTime localDateTime) {
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    return Optional.ofNullable(localDateTime).isPresent() ? localDateTime.format(formatter) : null;
  }

}
