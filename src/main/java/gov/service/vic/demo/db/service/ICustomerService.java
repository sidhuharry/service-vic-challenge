package gov.service.vic.demo.db.service;

import gov.service.vic.demo.db.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICustomerService {
    Customer save(Customer customer);

    List<Customer> saveAll(List<Customer> customerList);

}
