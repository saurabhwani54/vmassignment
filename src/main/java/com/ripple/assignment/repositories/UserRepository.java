package com.ripple.assignment.repositories;

import com.ripple.assignment.Exception.VmAuthException;
import com.ripple.assignment.domain.User;

public interface UserRepository {

    Integer create(String firstName, String lastName, String email, String password, String mobile, String role) throws VmAuthException;

    User findByEmailAndPassword(String email, String password) throws VmAuthException;

    Integer getCountByEmail(String email);
    
    Integer getCountByMobile(String mobile);

    User findById(Integer userId);

    void removeById(Integer userId);
}
