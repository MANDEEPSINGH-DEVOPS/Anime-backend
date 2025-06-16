package abp.project.anime.dao;

import abp.project.anime.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //Get/user
    public User obtenerUserPorId(int id){
        String sql = "CALL obtenerUserByProcedure(?)";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(User.class), id);
    }

    //register
    public User insertarUser(String name, String email, String password, String phone) {
        String sql = "CALL InsertarUser(?,?,?,?)";

        int rowsAffected = jdbcTemplate.update(sql, name, email, password, phone);

        if (rowsAffected > 0) {
            User user = new User();
            user.setName(name); user.setEmail(email); user.setPassword(password); user.setPhone(phone);
            return user;
        } else {
            return null;
        }
    }

    //put
    public int updateUser(int id, String name, String email, String password, String phone) {
        String sql = "CALL updateUser(?,?,?,?,?)";
        return jdbcTemplate.update(sql, id, name, email, password, phone); // Uso correcto
    }


    //login
    public User Login(String email, String password){
        String sql = "CALL Login(?,?)";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email, password);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
