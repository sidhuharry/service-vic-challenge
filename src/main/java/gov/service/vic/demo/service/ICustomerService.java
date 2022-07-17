package gov.service.vic.demo.service;

import gov.service.vic.demo.model.Customer;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICustomerService {
    Customer save(Customer customer);

    List<Customer> saveAll(List<Customer> customerList);

}
