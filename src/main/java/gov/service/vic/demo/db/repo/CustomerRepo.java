package gov.service.vic.demo.db.repo;

import gov.service.vic.demo.db.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends CrudRepository<Customer, String> {
}
