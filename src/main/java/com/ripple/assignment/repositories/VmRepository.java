package com.ripple.assignment.repositories;


import java.util.List;

import com.ripple.assignment.Exception.VmBadRequestException;
import com.ripple.assignment.Exception.VmResourceNotFoundException;
import com.ripple.assignment.domain.VmSpec;

public interface VmRepository {

    List<VmSpec> findAll(Integer userId) throws VmResourceNotFoundException;

    VmSpec findById(Integer userId, Integer vmId) throws VmResourceNotFoundException;

    Integer create(Integer userId, String os, Integer ram, Integer hd, Integer cpu) throws VmBadRequestException;

    void update(Integer userId, Integer vmId, VmSpec vmSpec) throws VmBadRequestException;

    void removeById(Integer userId, Integer vmId);

}
