package com.ripple.assignment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ripple.assignment.Exception.VmBadRequestException;
import com.ripple.assignment.Exception.VmResourceNotFoundException;
import com.ripple.assignment.domain.VmSpec;
import com.ripple.assignment.repositories.VmRepository;

import java.util.List;

@Service
@Transactional
public class VmServiceImpl implements VmService {

    @Autowired
    VmRepository vmRepository;

    @Override
    public List<VmSpec> fetchAllVMs(Integer userId) {
        return vmRepository.findAll(userId);
    }

    @Override
    public VmSpec fetchVMById(Integer userId, Integer vmId) throws VmResourceNotFoundException {
        return vmRepository.findById(userId, vmId);
    }

    @Override
    public VmSpec addVM(Integer userId, String os, Integer ram, Integer hd, Integer cpu) throws VmBadRequestException {
        int vmId = vmRepository.create(userId, os, ram, hd, cpu);
        return vmRepository.findById(userId, vmId);
    }

    @Override
    public void updateVM(Integer userId, Integer vmId, VmSpec vmSpec) throws VmBadRequestException {
        vmRepository.update(userId, vmId, vmSpec);
    }

    @Override
    public void removeVMWithAllTransactions(Integer userId, Integer vmId) throws VmResourceNotFoundException {
        this.fetchVMById(userId, vmId);
        vmRepository.removeById(userId, vmId);
    }
}
