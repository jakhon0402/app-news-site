package uz.pdp.appnewssite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.Role;
import uz.pdp.appnewssite.entity.User;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.UserDto;
import uz.pdp.appnewssite.repository.RoleRepo;
import uz.pdp.appnewssite.repository.UserRepo;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepo roleRepo;

    public ApiResponse addUser(UserDto userDto) {
        Optional<Role> optionalRole = roleRepo.findById(userDto.getRoleId());
        if(optionalRole.isEmpty())
            return new ApiResponse("Bundat idlik role mavjud emas!", false);
        User user = new User(
                userDto.getFullName(),
                userDto.getUsername(),
                passwordEncoder.encode(userDto.getPassword()),
                optionalRole.get(),
                true
        );
        userRepo.save(user);
        return new ApiResponse("Yangi user qo'shildi !",true);
    }

    public ApiResponse editUser(UserDto userDto,long userId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if(optionalUser.isEmpty())
            return new ApiResponse("Bunday idlik user mavjud emas !",false);
        Optional<Role> optionalRole = roleRepo.findById(userDto.getRoleId());
        if(optionalRole.isEmpty())
            return new ApiResponse("Bundat idlik role mavjud emas!", false);
        User user = optionalUser.get();
        user.setFullName(userDto.getFullName());
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(optionalRole.get());
        user.setEnabled(true);
        userRepo.save(user);
        return new ApiResponse("Yangi user qo'shildi !",true);
    }

    public ApiResponse deleteUser(long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if(optionalUser.isEmpty())
            return new ApiResponse("Bunday idlik user mavjud emas !",false);
        userRepo.delete(optionalUser.get());
        return new ApiResponse("User o'chirildi !",true);
    }

    public User viewUser(long id) {
        return userRepo.findById(id).orElseThrow(null);
    }
}
