package com.ripple.assignment.services;


import java.util.List;

import com.ripple.assignment.Exception.VmBadRequestException;
import com.ripple.assignment.Exception.VmResourceNotFoundException;
import com.ripple.assignment.domain.VmSpec;

public interface VmService {

    List<VmSpec> fetchAllVMs(Integer userId);

    VmSpec fetchVMById(Integer userId, Integer vmId) throws VmResourceNotFoundException;

    VmSpec addVM(Integer userId, String os, Integer ram, Integer hd, Integer cpu) throws VmBadRequestException;

    void updateVM(Integer userId, Integer vmId, VmSpec vmSpec) throws VmBadRequestException;

    void removeVMWithAllTransactions(Integer userId, Integer vmId) throws VmResourceNotFoundException;

}
