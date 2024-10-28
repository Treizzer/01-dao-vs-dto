package com.treizer.dao_vs_dto.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.treizer.dao_vs_dto.persistence.DAOs.interfaces.ICommonDao;
import com.treizer.dao_vs_dto.persistence.entities.User;
import com.treizer.dao_vs_dto.presentation.DTOs.UserDto;
import com.treizer.dao_vs_dto.presentation.DTOs.UserInsertDto;
import com.treizer.dao_vs_dto.presentation.DTOs.UserUpdateDto;
import com.treizer.dao_vs_dto.services.interfaces.ICommonService;

@Service
public class UserService implements ICommonService<UserDto, UserInsertDto, UserUpdateDto> {

    @Autowired
    private ICommonDao<User> userDao;

    // @Autowired
    // private ModelMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> get() {
        ModelMapper mapper = new ModelMapper();
        return this.userDao.findAll()
                .stream()
                .map(user -> mapper.map(user, UserDto.class))
                // .toList(); // Was implemented in Java 16
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getById(Long id) {
        // Optional<User> udto = this.userDao.findById(id);
        var user = this.userDao.findById(id);

        if (!user.isPresent()) {
            return new UserDto();
        }

        ModelMapper mapper = new ModelMapper();
        return mapper.map(user.get(), UserDto.class);
    }

    @Override
    @Transactional
    public UserDto add(UserInsertDto userInsertDto) {
        try {
            ModelMapper mapper = new ModelMapper();
            User user = mapper.map(userInsertDto, User.class);
            user = this.userDao.save(user);
            return mapper.map(user, UserDto.class);

        } catch (Exception ex) {
            throw new UnsupportedOperationException("Error al guardar el usuario");
        }
    }

    @Override
    @Transactional
    public UserDto update(UserUpdateDto userUpdateDto, Long id) {
        var user = this.userDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontro el usuario con id: " + id)); // ???

        /*
         * if (!user.isPresent()) {
         * return new UserDto();
         * }
         * User currentUser = user.get();
         * 
         * User currentUser = user;
         * currentUser.setName(userUpdateDto.getName());
         * currentUser.setLastName(userUpdateDto.getLastName());
         * currentUser.setEmail(userUpdateDto.getEmail());
         * currentUser.setAge(userUpdateDto.getAge());
         * this.userDao.update(currentUser);
         */

        user.setName(userUpdateDto.getName());
        user.setLastName(userUpdateDto.getLastName());
        user.setEmail(userUpdateDto.getEmail());
        user.setAge(userUpdateDto.getAge());

        this.userDao.update(user);
        // return this.mapper.map(user, UserDto.class);
        return new ModelMapper().map(user, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto delete(Long id) {
        var user = this.userDao.findById(id);

        if (!user.isPresent()) {
            return new UserDto();
        }

        var userDto = new ModelMapper().map(user.get(), UserDto.class);
        // var userDto = this.mapper.map(user.get(), UserDto.class);
        this.userDao.delete(user.get());

        return userDto;
    }

}
