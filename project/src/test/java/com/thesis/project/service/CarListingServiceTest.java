package com.thesis.project.service;

import com.thesis.project.dto.CarListingDto;
import com.thesis.project.dto.SmallCarListingDto;
import com.thesis.project.exception.ResourceNotFoundException;
import com.thesis.project.helper.ImageHelper;
import com.thesis.project.mapper.CarListingMapper;
import com.thesis.project.model.CarImage;
import com.thesis.project.model.CarListing;
import com.thesis.project.model.User;
import com.thesis.project.repository.CarImageRepository;
import com.thesis.project.repository.CarListingRepository;
import com.thesis.project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarListingServiceTest {

    @InjectMocks
    private CarListingService carListingService;

    @Mock
    private CarListingRepository carListingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarImageRepository carImageRepository;

    @Mock
    private CarListingMapper carListingMapper;

    @Mock
    private ImageHelper imageHelper;

    @BeforeEach
    void setUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        Method initMethod = CarListingService.class.getDeclaredMethod("init");
        initMethod.setAccessible(true);
        initMethod.invoke(carListingService);
    }

    @Test
    void testSaveCar() {
        CarListingDto carListingDto = new CarListingDto();
        carListingDto.setBrand("Toyota");
        carListingDto.setModel("Camry");

        MultipartFile[] images = new MultipartFile[]{};
        User user = new User();
        CarListing carListing = new CarListing();
        carListing.setBrand("Toyota");
        carListing.setModel("Camry");

        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(carListingRepository.save(any(CarListing.class))).thenReturn(carListing);
        when(carListingMapper.carListingDtoToCarListing(carListingDto)).thenReturn(carListing);
        when(carListingMapper.carListingToCarListingDto(carListing)).thenReturn(carListingDto);

        CarListingDto result = carListingService.saveCar(carListingDto, 1, images);

        assertNotNull(result);
        assertEquals(result.getBrand(), "Toyota");
        assertEquals(result.getModel(), "Camry");
        verify(carListingRepository, times(1)).save(any(CarListing.class));
        verify(carImageRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testGetCarById() {
        CarListing carListing = new CarListing();
        CarListingDto carListingDto = new CarListingDto();
        when(carListingRepository.findById(anyInt())).thenReturn(Optional.of(carListing));
        when(carListingMapper.carListingToCarListingDto(carListing)).thenReturn(carListingDto);

        CarListingDto result = carListingService.getCarById(1);

        assertNotNull(result);
        verify(carListingRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetCarById_NotFound() {
        when(carListingRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> carListingService.getCarById(1));
    }

    @Test
    void testGetAllCars() {
        List<CarListing> carListings = new ArrayList<>();
        CarListing carListing = new CarListing();
        carListings.add(carListing);

        when(carListingRepository.findAll()).thenReturn(carListings);

        SmallCarListingDto smallCarListingDto = new SmallCarListingDto();
        when(carListingMapper.carListingToSmallCarListingDto(carListing)).thenReturn(smallCarListingDto);

        List<SmallCarListingDto> result = carListingService.getAllCarListings();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(carListingRepository, times(1)).findAll();
    }

    @Test
    void testUpdateCarListing() {
        int carListingId = 1;
        CarListingDto updatedCarListingDto = new CarListingDto();
        updatedCarListingDto.setBrand("UpdatedBrand");
        updatedCarListingDto.setModel("UpdatedModel");
        MultipartFile[] images = new MultipartFile[]{};
        CarListing originalCarListing = new CarListing();
        originalCarListing.setImages(new ArrayList<>());
        when(carListingRepository.findById(carListingId)).thenReturn(Optional.of(originalCarListing));
        when(carListingRepository.save(any(CarListing.class))).thenReturn(new CarListing());
        when(carListingMapper.carListingDtoToCarListing(updatedCarListingDto)).thenReturn(originalCarListing);
        when(carListingMapper.carListingToCarListingDto(any(CarListing.class))).thenReturn(updatedCarListingDto);

        CarListingDto result = carListingService.updateCarListing(carListingId, updatedCarListingDto, images);

        assertNotNull(result);
        verify(carListingRepository, times(1)).findById(carListingId);
        verify(carListingRepository, times(1)).save(any(CarListing.class));
    }

    @Test
    void testDeleteCarListing() {
        CarListing carListing = new CarListing();
        CarImage carImage = new CarImage();
        carImage.setImageUrl("imageUrl");
        carListing.setImages(Collections.singletonList(carImage));

        when(carListingRepository.findById(anyInt())).thenReturn(Optional.of(carListing));

        carListingService.deleteCarListing(1);

        verify(carListingRepository, times(1)).deleteById(anyInt());
        verify(imageHelper, times(1)).deleteImage("imageUrl");
    }

    @Test
    void testGetCarCount() {
        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[]{"BrandA", "ModelA", 5L});
        when(carListingRepository.countByBrandAndModel()).thenReturn(mockResults);

        Map<String, Integer> result = carListingService.getCarCount();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("BrandA ModelA"));
        assertEquals(5, result.get("BrandA ModelA"));
    }
}
