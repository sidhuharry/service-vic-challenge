package gov.service.vic.demo.db.repo;

import gov.service.vic.demo.db.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends CrudRepository<Order, String> {
}
