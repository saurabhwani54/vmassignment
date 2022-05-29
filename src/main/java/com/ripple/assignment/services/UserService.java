package com.ripple.assignment.services;

import com.ripple.assignment.Exception.VmAuthException;
import com.ripple.assignment.Exception.VmResourceNotFoundException;
import com.ripple.assignment.domain.User;

public interface UserService {

    User validateUser(String email, String password) throws VmAuthException;

    User registerUser(String firstName, String lastName, String email, String password, String mobile, String role) throws VmAuthException;

    void removeUserWithAllVMs(Integer userId) throws VmResourceNotFoundException;

}
