package com.zegasoftware.eventremainder.model.mapper;

import com.zegasoftware.eventremainder.model.dto.UserDto;
import com.zegasoftware.eventremainder.model.entity.User;
import com.zegasoftware.eventremainder.model.payload.UserRegistrationRequest;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto map(User user);

    @InheritInverseConfiguration(name = "map")
    User map(UserDto userDto);

    User registrationRequestToUser(UserRegistrationRequest userRegistrationRequest);
}
