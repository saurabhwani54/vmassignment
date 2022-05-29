package com.ripple.assignment.domain;

public class VmSpec {

    private Integer vmId;
    private Integer userId;
    private String os;
    private Integer ram;
    private Integer hd;
    private Integer cpu;
	public VmSpec(Integer vmId, Integer userId, String os, Integer ram, Integer hd, Integer cpu) {
		super();
		this.vmId = vmId;
		this.userId = userId;
		this.os = os;
		this.ram = ram;
		this.hd = hd;
		this.cpu = cpu;
	}
	public Integer getVmId() {
		return vmId;
	}
	public void setVmId(Integer vmId) {
		this.vmId = vmId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public Integer getRam() {
		return ram;
	}
	public void setRam(Integer ram) {
		this.ram = ram;
	}
	public Integer getHd() {
		return hd;
	}
	public void setHd(Integer hd) {
		this.hd = hd;
	}
	public Integer getCpu() {
		return cpu;
	}
	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}
	

}
