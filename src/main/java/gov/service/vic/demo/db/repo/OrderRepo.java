package gov.service.vic.demo.db.repo;

import gov.service.vic.demo.db.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepo extends CrudRepository<Order, UUID> {
}
