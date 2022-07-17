package gov.service.vic.demo.repo;

import gov.service.vic.demo.model.MenuItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepo extends CrudRepository<MenuItem, Integer> {
}
