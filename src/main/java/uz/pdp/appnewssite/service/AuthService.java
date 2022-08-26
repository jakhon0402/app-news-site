package uz.pdp.appnewssite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.User;
import uz.pdp.appnewssite.exception.ResourceNotFoundException;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.RegisterDto;
import uz.pdp.appnewssite.repository.RoleRepo;
import uz.pdp.appnewssite.repository.UserRepo;
import uz.pdp.appnewssite.utils.AppConstants;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponse registerUser(RegisterDto registerDto) {
        if(!registerDto.getPassword().equals(registerDto.getPrePassword()))
            return new ApiResponse("Parollar mos emas !", false);
        if(userRepo.existsByUsername(registerDto.getUsername())){
            return new ApiResponse("Bunday username avval ro'yhatdan o'tgan!",false);
        }
        User user = new User(
                registerDto.getFullName(),
                registerDto.getUsername(),
                passwordEncoder.encode(registerDto.getPassword()),
                roleRepo.findByName(AppConstants.USER).orElseThrow(()->new ResourceNotFoundException("role","user",AppConstants.USER)),
                true
        );
        userRepo.save(user);
        return new ApiResponse("Muvaffaqiyatli ro'yhatdan o'tdingiz!",true);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }
}
