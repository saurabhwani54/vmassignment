package com.ripple.assignment.repositories;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ripple.assignment.Exception.VmAuthException;
import com.ripple.assignment.domain.User;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_CREATE = "INSERT INTO VM_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, MOBILE, ROLE) VALUES(NEXTVAL('VM_USERS_SEQ'), ?, ?, ?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM VM_USERS WHERE EMAIL = ?";
    private static final String SQL_COUNT_BY_MOBILE = "SELECT COUNT(*) FROM VM_USERS WHERE MOBILE = ?";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, MOBILE, ROLE " +
            "FROM VM_USERS WHERE USER_ID = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, MOBILE, ROLE " +
            "FROM VM_USERS WHERE EMAIL = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM VM_USERS WHERE USER_ID = ?";
    private static final String SQL_DELETE_ALL_VMs = "DELETE FROM VM_SPEC WHERE USER_ID = ?";
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String firstName, String lastName, String email, String password, String mobile, String role) throws VmAuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, email);
                ps.setString(4, hashedPassword);
                ps.setString(5, mobile);
                ps.setString(6, role);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("USER_ID");
        }catch (Exception e) {
            throw new VmAuthException("Invalid details. Failed to create account");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws VmAuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
            if(!BCrypt.checkpw(password, user.getPassword()))
                throw new VmAuthException("Invalid email/password");
            return user;
        }catch (EmptyResultDataAccessException e) {
            throw new VmAuthException("Invalid email/password");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId}, userRowMapper);
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(rs.getInt("USER_ID"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"),
                rs.getString("MOBILE"),
                rs.getString("ROLE"));
    });

	@Override
	public Integer getCountByMobile(String mobile) {
		return jdbcTemplate.queryForObject(SQL_COUNT_BY_MOBILE, new Object[]{mobile}, Integer.class);
	}

	@Override
	public void removeById(Integer userId) {
		this.removeAllVMs(userId);
        jdbcTemplate.update(SQL_DELETE_USER, new Object[]{userId});		
	}
	
	private void removeAllVMs(Integer userId) {
		jdbcTemplate.update(SQL_DELETE_ALL_VMs, new Object[]{userId});
	}
}
