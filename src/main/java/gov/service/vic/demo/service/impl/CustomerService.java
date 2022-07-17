package gov.service.vic.demo.service.impl;

import gov.service.vic.demo.model.Customer;
import gov.service.vic.demo.repo.CustomerRepo;
import gov.service.vic.demo.service.ICustomerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
