package com.thesis.project.mapper;

import com.thesis.project.dto.CarListingDto;
import com.thesis.project.dto.SmallCarListingDto;
import com.thesis.project.model.CarListing;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CarListingMapper {

    CarListingMapper INSTANCE = Mappers.getMapper(CarListingMapper.class);

    @Mapping(target = "carImageUrl", expression = "java(carListing.getImages().get(0).getImageUrl())")
    SmallCarListingDto carListingToSmallCarListingDto(CarListing carListing);

    @Mapping(target = "userDisplayName", expression = "java(carListing.getUser() != null ? carListing.getUser().getFirstName() + ' ' + carListing.getUser().getLastName() : null)")
    CarListingDto carListingToCarListingDto(CarListing carListing);

    CarListing carListingDtoToCarListing(CarListingDto dto);
}
