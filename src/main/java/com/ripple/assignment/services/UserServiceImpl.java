package com.ripple.assignment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ripple.assignment.Exception.VmAuthException;
import com.ripple.assignment.Exception.VmResourceNotFoundException;
import com.ripple.assignment.domain.User;
import com.ripple.assignment.repositories.UserRepository;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) throws VmAuthException {
        if(email != null) email = email.toLowerCase();
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password, String mobile, String role) throws VmAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if(email != null) email = email.toLowerCase();
        if(!pattern.matcher(email).matches())
            throw new VmAuthException("Invalid email format");
        Integer count = userRepository.getCountByEmail(email);
        if(count > 0)
            throw new VmAuthException("Email already in use");
        Integer countMobile = userRepository.getCountByMobile(mobile);
        if(countMobile > 0)
            throw new VmAuthException("Mobile Number already in use");
        Integer userId = userRepository.create(firstName, lastName, email, password, mobile, role);
        return userRepository.findById(userId);
    }
    
    @Override
    public void removeUserWithAllVMs(Integer userId) throws VmResourceNotFoundException {
    	userRepository.removeById(userId);
    }
}
