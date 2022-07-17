package gov.service.vic.demo.service;

import gov.service.vic.demo.db.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface ICustomerService {
    Customer save(Customer customer);

    List<Customer> saveAll(List<Customer> customerList);

    @Transactional Optional<Customer> customerExists(UUID customerId);
}
