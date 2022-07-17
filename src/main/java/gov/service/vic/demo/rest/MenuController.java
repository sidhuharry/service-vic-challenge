package gov.service.vic.demo.rest;

import gov.service.vic.demo.model.MenuItem;
import gov.service.vic.demo.service.IMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {

    private IMenuService menuService;

    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/menu")
    public List<MenuItem> getMenu() {
        return menuService.getAll();
    }
}
