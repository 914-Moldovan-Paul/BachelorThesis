package com.thesis.project.service;

import com.thesis.project.dto.CredentialsDto;
import com.thesis.project.dto.SignUpDto;
import com.thesis.project.dto.SmallCarListingDto;
import com.thesis.project.dto.UserDto;
import com.thesis.project.mapper.CarListingMapper;
import com.thesis.project.mapper.UserMapper;
import com.thesis.project.model.User;
import com.thesis.project.exception.AppException;
import com.thesis.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByUsername(credentialsDto.getUsername())
                .orElseThrow(() -> new AppException("Unknown username and password combination", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return UserMapper.INSTANCE.toUserDto(user);
        }
        throw new AppException("Unknown username and password combination", HttpStatus.NOT_FOUND);
    }

    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = UserMapper.INSTANCE.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        User savedUser = userRepository.save(user);

        return UserMapper.INSTANCE.toUserDto(savedUser);
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return UserMapper.INSTANCE.toUserDto(user);
    }

    public List<SmallCarListingDto> getAllCarListingsForUser(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND))
                .getCarListings()
                .stream()
                .map(CarListingMapper.INSTANCE::carListingToSmallCarListingDto)
                .collect(Collectors.toList());
    }

}
