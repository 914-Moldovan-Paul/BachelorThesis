package com.thesis.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.project.dto.CarListingDto;
import com.thesis.project.dto.QueryDto;
import com.thesis.project.dto.SmallCarListingDto;
import com.thesis.project.model.CarListing;
import com.thesis.project.service.CarListingService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarListingController.class)
@AutoConfigureMockMvc(addFilters = false)
class CarListingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarListingService carListingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCarListing() throws Exception {
        CarListingDto carListingDto = new CarListingDto();
        carListingDto.setBrand("Toyota");
        carListingDto.setModel("Auris");

        MockMultipartFile carListingDtoFile = new MockMultipartFile("carListingDto", "", "application/json", objectMapper.writeValueAsBytes(carListingDto));
        MockMultipartFile imageFile = new MockMultipartFile("images", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "test image".getBytes());

        Mockito.when(carListingService.saveCar(ArgumentMatchers.any(CarListingDto.class), ArgumentMatchers.anyInt(), ArgumentMatchers.any(MultipartFile[].class)))
                .thenReturn(carListingDto);

        mockMvc.perform(multipart("/cars")
                        .file(carListingDtoFile)
                        .file(imageFile)
                        .param("userId", "1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Auris"));
    }

    @Test
    void getCarById() throws Exception {
        int carId = 1;
        CarListingDto carListingDto = new CarListingDto();
        carListingDto.setId(1);
        carListingDto.setBrand("Toyota");
        carListingDto.setModel("Auris");

        Mockito.when(carListingService.getCarById(carId)).thenReturn(carListingDto);

        mockMvc.perform(get("/cars/{id}", carId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Auris"));
    }

    @Test
    void getAllCarListings() throws Exception {
        List<SmallCarListingDto> carListings = new ArrayList<>();
        SmallCarListingDto smallCarListingDto = new SmallCarListingDto();
        smallCarListingDto.setBrand("Toyota");
        smallCarListingDto.setModel("Auris");
        carListings.add(smallCarListingDto);
        Mockito.when(carListingService.getAllCarListings()).thenReturn(carListings);

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].brand").value("Toyota"))
                .andExpect(jsonPath("$[0].model").value("Auris"));
    }

    @Test
    void getCarListingsForQuery() throws Exception {
        QueryDto queryDto = new QueryDto();

        List<SmallCarListingDto> carListings = new ArrayList<>();
        SmallCarListingDto smallCarListingDto = new SmallCarListingDto();
        smallCarListingDto.setBrand("Toyota");
        smallCarListingDto.setModel("Auris");
        carListings.add(smallCarListingDto);

        Mockito.when(carListingService.getCarListingsForQuery(any(QueryDto.class))).thenReturn(carListings);

        mockMvc.perform(post("/cars/by-query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(queryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].brand").value("Toyota"))
                .andExpect(jsonPath("$[0].model").value("Auris"));
    }

    @Test
    void updateCarListing() throws Exception {
        int carId = 1;
        CarListingDto carListingDto = new CarListingDto();
        carListingDto.setBrand("Toyota");
        carListingDto.setModel("Auris");

        MockMultipartFile carListingDtoFile = new MockMultipartFile("carListingDto", "", "application/json", objectMapper.writeValueAsBytes(carListingDto));
        MockMultipartFile imageFile = new MockMultipartFile("images", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "test image".getBytes());

        Mockito.when(carListingService.updateCarListing(ArgumentMatchers.anyInt(), ArgumentMatchers.any(CarListingDto.class), ArgumentMatchers.any(MultipartFile[].class)))
                .thenReturn(carListingDto);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/cars/{id}", carId)
                        .file(carListingDtoFile)
                        .file(imageFile)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Auris"));
    }

    @Test
    void deleteCarListing() throws Exception {
        int carId = 1;
        Mockito.doNothing().when(carListingService).deleteCarListing(carId);

        mockMvc.perform(delete("/cars/{id}", carId))
                .andExpect(status().isOk())
                .andExpect(content().string("Car listing with id " + carId + " deleted successfully"));
    }
}
