package com.treizer.dao_vs_dto.presentation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.treizer.dao_vs_dto.presentation.DTOs.UserDto;
import com.treizer.dao_vs_dto.presentation.DTOs.UserInsertDto;
import com.treizer.dao_vs_dto.presentation.DTOs.UserUpdateDto;
import com.treizer.dao_vs_dto.services.interfaces.ICommonService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ICommonService<UserDto, UserInsertDto, UserUpdateDto> userService;

    // Get All
    @GetMapping
    public ResponseEntity<List<UserDto>> get() {
        // return new ResponseEntity<>(this.userService.get(), HttpStatus.OK);
        return ResponseEntity.ok(this.userService.get());
    }

    // Get By Id
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        var userDto = this.userService.getById(id);

        return userDto == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(userDto);
    }

    // Create user
    @PostMapping
    public ResponseEntity<UserDto> add(@RequestBody UserInsertDto userInsertDto) {
        var userDto = this.userService.add(userInsertDto);

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(userDto.getId())
                        .toUri())
                .body(userDto);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@RequestBody UserUpdateDto userUpdateDto, @PathVariable Long id) {
        var userDto = this.userService.update(userUpdateDto, id);

        return userDto == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(userDto);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable Long id) {
        var userDto = this.userService.delete(id);

        return userDto == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(userDto);
    }

}
