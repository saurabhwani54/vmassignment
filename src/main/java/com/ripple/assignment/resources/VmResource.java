package com.ripple.assignment.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ripple.assignment.domain.VmSpec;
import com.ripple.assignment.services.VmService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vms")
public class VmResource {

    @Autowired
    VmService vmService;

    @GetMapping("")
    public ResponseEntity<List<VmSpec>> getAllVms(HttpServletRequest request) {
        int userId = (Integer) request.getAttribute("userId");
        List<VmSpec> vmSpecs = vmService.fetchAllVMs(userId);
        return new ResponseEntity<>(vmSpecs, HttpStatus.OK);
    }

    @GetMapping("/{vmId}")
    public ResponseEntity<VmSpec> getVMById(HttpServletRequest request,
                                                    @PathVariable("vmId") Integer vmId) {
        int userId = (Integer) request.getAttribute("userId");
        VmSpec vmSpec = vmService.fetchVMById(userId, vmId);
        return new ResponseEntity<>(vmSpec, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<VmSpec> addVM(HttpServletRequest request,
                                                @RequestBody Map<String, Object> vmMap) {
        int userId = (Integer) request.getAttribute("userId");
        String os = (String) vmMap.get("os");
        int ram = (Integer) vmMap.get("ram");
        int hd = (Integer) vmMap.get("hd");
        int cpu = (Integer) vmMap.get("cpu");
        VmSpec vmSpec = vmService.addVM(userId, os, ram, hd, cpu);
        return new ResponseEntity<>(vmSpec, HttpStatus.CREATED);
    }

    @PutMapping("/{vmId}")
    public ResponseEntity<Map<String, Boolean>> updateVM(HttpServletRequest request,
                                                               @PathVariable("vmId") Integer vmId,
                                                               @RequestBody VmSpec vmSpec) {
        int userId = (Integer) request.getAttribute("userId");
        vmService.updateVM(userId, vmId, vmSpec);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{vmId}")
    public ResponseEntity<Map<String, Boolean>> deleteVM(HttpServletRequest request,
                                                               @PathVariable("vmId") Integer vmId) {
        int userId = (Integer) request.getAttribute("userId");
        vmService.removeVMWithAllTransactions(userId, vmId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
