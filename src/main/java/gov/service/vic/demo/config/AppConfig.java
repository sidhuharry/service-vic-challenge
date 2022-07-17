package gov.service.vic.demo.config;

import gov.service.vic.demo.db.entity.MenuItem;
import gov.service.vic.demo.service.IMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Inits the in-memory db with dummy values
 */
@Configuration public class AppConfig {

    private final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

    /**
     * This method will run when the application context is loaded
     */
    @Bean
    CommandLineRunner initDb(IMenuService menuService) {
        return (args) -> {
            LOG.debug("Starting local h2 db with dummy values in menu");
            menuService.saveAll(
                    Arrays.asList(new MenuItem(1, "Hamburger", 5.0f),
                                  new MenuItem(2, "Cheeseburger", 6.0f),
                                  new MenuItem(3, "Chicken Burger", 7.0f),
                                  new MenuItem(2, "Cheeseburger", 6.0f),
                                  new MenuItem(4, "Fries", 3.0f),
                                  new MenuItem(5, "Coke", 2.0f),
                                  new MenuItem(6, "Pepsi", 2.0f),
                                  new MenuItem(7, "Sundae", 3.0f),
                                  new MenuItem(8, "Apple Pie", 3.0f)
                    ));
        };
    }
}
