package uz.pdp.appnewssite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnewssite.entity.User;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.RegisterDto;
import uz.pdp.appnewssite.payload.UserDto;
import uz.pdp.appnewssite.service.AuthService;
import uz.pdp.appnewssite.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PreAuthorize(value = "hasAuthority('VIEW_USER')")
    @DeleteMapping("/{id}")
    public User viewUser(@PathVariable long id){
        return userService.viewUser(id);
    }

    @PreAuthorize(value = "hasAuthority('ADD_USER')")
    @PostMapping
    public HttpEntity<?> addUser(@Valid @RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.addUser(userDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_USER')")
    @PutMapping("/{id}")
    public HttpEntity<?> editUser(@Valid @RequestBody UserDto userDto,@PathVariable long id){
        ApiResponse apiResponse = userService.editUser(userDto,id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_USER')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteUser(@PathVariable long id){
        ApiResponse apiResponse = userService.deleteUser(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


}
