package gov.service.vic.demo.service;


import gov.service.vic.demo.model.MenuItem;
import gov.service.vic.demo.repo.MenuItemRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMenuService {
    List<MenuItem> saveAll(List<MenuItem> menuItemList);
}
