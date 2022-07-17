package gov.service.vic.demo.service;


import gov.service.vic.demo.db.entity.MenuItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMenuService {
    List<MenuItem> saveAll(List<MenuItem> menuItemList);

    List<MenuItem> getAll();
}
