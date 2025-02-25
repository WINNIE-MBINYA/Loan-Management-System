package com.loanmanagement.Controller;

import com.loanmanagement.Dto.CustomerDTO;
import com.loanmanagement.Dto.CustomerMapper;
import com.loanmanagement.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers()
                .stream()
                .map(CustomerMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/getCustomer/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(customer -> ResponseEntity.ok(CustomerMapper.toDTO(customer)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/createCustomer")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerDTO customerDTO) {
        log.info("Creating customer: {}", customerDTO);
        customerService.createCustomer(CustomerMapper.toEntity(customerDTO));
        return ResponseEntity.ok("Customer created successfully");
    }

    @PutMapping("/updateCustomer/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomer(id, CustomerMapper.toEntity(customerDTO));
        return ResponseEntity.ok("Customer updated successfully");
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }
}
