package gov.service.vic.demo.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.service.vic.demo.db.repo.CustomerRepo;
import gov.service.vic.demo.db.repo.OrderRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OrderRepo orderRepo;

    @MockBean
    CustomerRepo customerRepo;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
}