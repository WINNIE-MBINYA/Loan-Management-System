package com.loanmanagement.Dto;

import com.loanmanagement.Dto.CustomerDTO;
import com.loanmanagement.Entity.Customer;

public class CustomerMapper {

    public static CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setAddress(customer.getAddress());
        return dto;
    }

    public static Customer toEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setAddress(dto.getAddress());
        return customer;
    }
}
