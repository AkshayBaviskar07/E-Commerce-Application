package com.akshay.customer.service;

import com.akshay.customer.exception.CustomerNotFoundException;
import com.akshay.customer.model.Address;
import com.akshay.customer.model.Customer;
import com.akshay.customer.model.CustomerRequest;
import com.akshay.customer.model.CustomerResponse;
import com.akshay.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepo;
    private final CustomerMapper mapper;

    public Integer createCustomer(CustomerRequest request) {
        Customer customer = mapper.toCustomer(request);
        List<Address> addresses = customer.getAddress();

        if (!addresses.isEmpty()) {
            for (Address address : addresses) {
                address.setCustomer(customer);
            }
        }
        customerRepo.save(customer);
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        var customer = customerRepo.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Cannot update customer:: No customer found with provided id:: %s", request.id())
                ));
        
        mergerCustomer(customer, request);
        customerRepo.save(customer);
    }

    private void mergerCustomer(Customer customer, CustomerRequest request) {

        if (StringUtils.isNotBlank(request.firstName())) {
             customer.setFirstName(request.firstName());
        }
        if (StringUtils.isNotBlank(request.lastName())) {
             customer.setLastName(request.lastName());
        }
        if (StringUtils.isNotBlank(request.email())) {
             customer.setEmail(request.email());
        }
        if (request.address() != null) {
             customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepo.findAll()
                .stream()
                .map(mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existsById(Integer customerId) {
        return customerRepo.findById(customerId)
                .isPresent();
    }

    public CustomerResponse findById(Integer customerId) {
        return customerRepo.findById(customerId)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Customer not found with id:: %s", customerId)
                ));
    }

    public void deleteCustomer(Integer customerId) {
        customerRepo.deleteById(customerId);
    }
}
