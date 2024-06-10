package com.thesis.project.controller;

import com.thesis.project.service.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ImageController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @Test
    public void testGetImagesForCarListing() throws Exception {
        int carListingId = 1;
        when(imageService.getImagesForCarListing(carListingId))
                .thenReturn(Arrays.asList("image1", "image2"));

        mockMvc.perform(get("/images/{id}", carListingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("image1")))
                .andExpect(jsonPath("$[1]", is("image2")));
    }

    @Test
    public void testGetFirstImageForCarListing() throws Exception {
        int carListingId = 1;
        when(imageService.getFirstImageForCarListing(carListingId))
                .thenReturn("image1");

        mockMvc.perform(get("/images/get-first/{id}", carListingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("image1"));
    }
}
