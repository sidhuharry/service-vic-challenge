package gov.service.vic.demo.service.impl;

import gov.service.vic.demo.db.entity.Customer;
import gov.service.vic.demo.db.repo.CustomerRepo;
import gov.service.vic.demo.service.ICustomerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService implements ICustomerService {

    private CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    @Transactional
    public List<Customer> saveAll(List<Customer> customerList) {
        return (List<Customer>) customerRepo.saveAll(customerList);
    }

    @Override
    @Transactional
    public Optional<Customer> customerExists(UUID customerId) {
        return customerRepo.findById(customerId);
    }
}
