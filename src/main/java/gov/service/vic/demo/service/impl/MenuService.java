package gov.service.vic.demo.service.impl;

import gov.service.vic.demo.model.MenuItem;
import gov.service.vic.demo.repo.MenuItemRepo;
import gov.service.vic.demo.service.IMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService implements IMenuService {

    private MenuItemRepo menuItemRepo;

    public MenuService(MenuItemRepo menuItemRepo) {
        this.menuItemRepo = menuItemRepo;
    }

    @Override
    public List<MenuItem> saveAll(List<MenuItem> menuItemList) {
        return (List<MenuItem>) menuItemRepo.saveAll(menuItemList);
    }

    @Override public List<MenuItem> getAll() {
        return (List<MenuItem>) menuItemRepo.findAll();
    }
}
