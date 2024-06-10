package com.thesis.project.controller;

import com.thesis.project.dto.SmallCarListingDto;
import com.thesis.project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        SmallCarListingDto car1 = new SmallCarListingDto();
        SmallCarListingDto car2 = new SmallCarListingDto();
        car1.setId("1");
        car1.setBrand("Toyota");
        car1.setModel("Auris");
        car2.setId("2");
        car2.setBrand("Peugeot");
        car2.setModel("508");

        List<SmallCarListingDto> carListings = Arrays.asList(car1, car2);

        Mockito.when(userService.getAllCarListingsForUser(1)).thenReturn(carListings);
    }

    @Test
    public void testGetAllCarListingsForUser() throws Exception {
        mockMvc.perform(get("/users/1/cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].brand", is("Toyota")))
                .andExpect(jsonPath("$[0].model", is("Auris")))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].brand", is("Peugeot")))
                .andExpect(jsonPath("$[1].model", is("508")));
    }
}
