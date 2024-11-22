package com.bnpstudio.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bnpstudio.bookstore.service.UserService;
import com.bnpstudio.bookstore.dto.UserDetailDto;
import com.bnpstudio.bookstore.entity.ResponseObject;
import com.bnpstudio.bookstore.dto.ChangePasswordDto;
import com.bnpstudio.bookstore.dto.LoginDto;
import com.bnpstudio.bookstore.dto.JwtResponseDto;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping("/register")
    public ResponseEntity<ResponseObject<UserDetailDto>> register(@RequestBody UserDetailDto userDto) {
        return ResponseEntity.ok(
                new ResponseObject(
                        HttpStatus.OK,
                        "Register thành công",
                        userService.register(userDto)));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PutMapping("/change-password")
    public ResponseEntity<ResponseObject<ChangePasswordDto>> changePassword(
            @RequestParam String email,
            @RequestBody ChangePasswordDto passwordDto) {
        return ResponseEntity.ok(
                new ResponseObject(
                        HttpStatus.OK,
                        "Change password thành công",
                        userService.changePassword(email, passwordDto)));
    }
 
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PutMapping("/update-profile")
    public ResponseEntity<ResponseObject<UserDetailDto>> updateProfile(
            @RequestParam String email,
            @RequestBody UserDetailDto userDto) {
        return ResponseEntity.ok(
                new ResponseObject(
                        HttpStatus.OK,
                        "Update profile thành công",
                        userService.updateProfile(email, userDto)));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PostMapping("/login")
    public ResponseEntity<ResponseObject<JwtResponseDto>> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(
                new ResponseObject(
                        HttpStatus.OK,
                        "Đăng nhập thành công",
                        userService.login(loginDto)));
    }
}
