package gov.service.vic.demo.db.repo;

import gov.service.vic.demo.db.entity.MenuItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepo extends CrudRepository<MenuItem, Integer> {
}
