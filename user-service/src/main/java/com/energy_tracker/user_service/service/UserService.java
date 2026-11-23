package com.energy_tracker.user_service.service;

import com.energy_tracker.user_service.dto.UserDto;
import com.energy_tracker.user_service.exception.UserNotFoundException;
import com.energy_tracker.user_service.model.User;
import com.energy_tracker.user_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUser(UserDto userDto){

        User user=User.builder()
            .firstName(userDto.getFirstName())
            .lastName(userDto.getLastName())
            .email(userDto.getEmail())
            .address(userDto.getAddress())
            .alerting(userDto.isAlerting())
            .energyAlertingThreshold(userDto.getEnergyAlertingThreshold())
            .build();

        final User saved=userRepository.save(user);
        return toUserDto(saved);
    }

    public UserDto getUserByEmail(Long id){
        return userRepository.findById(id)
                             .map(this::toUserDto)
                             .orElse(null);
    }

    public String updateUser(Long id,UserDto userDto){
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setAlerting(userDto.isAlerting());
        user.setEnergyAlertingThreshold(userDto.getEnergyAlertingThreshold());
        userRepository.save(user);
        return "User updated successfully";
    }

    public String deleteUserById(Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException("User not found");
        }
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    public UserDto toUserDto(User user){
        return UserDto.builder()
                      .id(user.getId())
                      .firstName(user.getFirstName())
                      .lastName(user.getLastName())
                      .email(user.getEmail())
                      .address(user.getAddress())
                      .alerting(user.isAlerting())
                      .energyAlertingThreshold(user.getEnergyAlertingThreshold())
                      .build();
    }
}
