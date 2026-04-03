package com.n2work.user.service;


import com.n2work.exceptions.EmptyException;
import com.n2work.exceptions.NotFoundException;
import com.n2work.user.model.User;
import com.n2work.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        boolean existentEmail = userRepository.existsByEmail(user.getEmail());
        if(existentEmail) throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado!");
        PasswordService passwordService = new PasswordService();
        String  encryptPassword = passwordService.encryptPassword(user.getPassword());
        user.setPassword(encryptPassword);
        return userRepository.save(user);

    }

    public List<User> listUsers(){
        return userRepository.findAll();
    }

    public User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EmptyException("User not found"));
    }

    public User patchUserById(Long id, Map<String, Object> updates){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(User.class, key);
            if(field != null){
                field.setAccessible(true);
                ReflectionUtils.setField(field,user,value);
            }

        });
        return userRepository.save(user);
    }

    public String deleteUser(Long id){
          if(id == null){
              throw  new EmptyException("Empty ID");

          }
          User user = userRepository.findById(id)
                  .orElseThrow(()-> new NotFoundException("User not found"));

          userRepository.deleteById(id);

          return "User deleted " + user.getName();
    }
}
