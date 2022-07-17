package gov.service.vic.demo.rest.controller;

import gov.service.vic.demo.db.entity.MenuItem;
import gov.service.vic.demo.rest.exception.InternalErrorException;
import gov.service.vic.demo.service.IMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController public class MenuController {

    private IMenuService menuService;

    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/menu") public List<MenuItem> getMenu() {
        List<MenuItem> menuItemList = new ArrayList<>();
        try {
            menuItemList = menuService.getAll();
        } catch (Exception exception) {
            throw new InternalErrorException(exception.getMessage());
        }
        return menuItemList;
    }
}
