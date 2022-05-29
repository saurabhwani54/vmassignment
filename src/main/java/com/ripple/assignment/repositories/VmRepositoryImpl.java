package com.ripple.assignment.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ripple.assignment.Exception.VmBadRequestException;
import com.ripple.assignment.Exception.VmResourceNotFoundException;
import com.ripple.assignment.domain.VmSpec;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class VmRepositoryImpl implements VmRepository {

    private static final String SQL_FIND_ALL = "SELECT VM_ID, USER_ID, OS, RAM, HD, CPU " +
            "FROM VM_SPEC " +
            "WHERE USER_ID = ? GROUP BY VM_ID";
    private static final String SQL_FIND_BY_ID = "SELECT VM_ID, USER_ID, OS, RAM, HD, CPU " +
            "FROM VM_SPEC " +
            "WHERE USER_ID = ? AND VM_ID = ? GROUP BY VM_ID";
    private static final String SQL_CREATE = "INSERT INTO VM_SPEC (VM_ID, USER_ID, OS, RAM, HD, CPU) VALUES(NEXTVAL('VM_SPEC_SEQ'), ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE VM_SPEC SET OS = ?, RAM = ?, HD = ?, CPU = ? " +
            "WHERE USER_ID = ? AND VM_ID = ?";
    private static final String SQL_DELETE_VM = "DELETE FROM VM_SPEC WHERE USER_ID = ? AND VM_ID = ?";
   // private static final String SQL_DELETE_ALL_TRANSACTIONS = "DELETE FROM VM_TRANSACTIONS WHERE VM_ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<VmSpec> findAll(Integer userId) throws VmResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{userId}, vmRowMapper);
    }

    @Override
    public VmSpec findById(Integer userId, Integer vmId) throws VmResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, vmId}, vmRowMapper);
        }catch (Exception e) {
            throw new VmResourceNotFoundException("VM not found");
        }
    }

    @Override
    public Integer create(Integer userId, String os, Integer ram, Integer hd, Integer cpu) throws VmBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setString(2, os);
                ps.setInt(3, ram);
                ps.setInt(4, hd);
                ps.setInt(5, cpu);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("VM_ID");
        }catch (Exception e) {
            throw new VmBadRequestException("Invalid request");
        }
    }

    //change here
    @Override
    public void update(Integer userId, Integer vmId, VmSpec vmSpec) throws VmBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{vmSpec.getOs(), vmSpec.getRam(), userId, vmId});
        }catch (Exception e) {
            throw new VmBadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(Integer userId, Integer vmId) {
        //this.removeAllCatTransactions(vmId);
        jdbcTemplate.update(SQL_DELETE_VM, new Object[]{userId, vmId});
    }

   /* private void removeAllCatTransactions(Integer vmId) {
        jdbcTemplate.update(SQL_DELETE_ALL_TRANSACTIONS, new Object[]{vmId});
    }*/

    private RowMapper<VmSpec> vmRowMapper = ((rs, rowNum) -> {
        return new VmSpec(rs.getInt("VM_ID"),
                rs.getInt("USER_ID"),
                rs.getString("OS"),
                rs.getInt("RAM"),
                rs.getInt("HD"),
                rs.getInt("CPU"));
    });
}
