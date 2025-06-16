package abp.project.anime.service;

import abp.project.anime.dao.UserDao;
import abp.project.anime.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public ResponseEntity login(User user){
        try {
            User u = userDao.Login(user.getEmail(),user.getPassword());
            if (u == null) {
                return new ResponseEntity<>("Correo o contraseña incorrecto", HttpStatus.NOT_FOUND);
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity getUser(int id){
        try{
            User user = userDao.obtenerUserPorId(id);
            if(user != null && user.getId() != 0){
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado ningún user con id "+ id);
        }catch (RuntimeException e){
            e.printStackTrace();
            return new ResponseEntity<>("Error al acceder a la base de datos", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity insertUser(User user) {
        try {
            User row = userDao.insertarUser(user.getName(), user.getEmail(), user.getPassword(), user.getPhone());
            System.out.println("Name: " + user.getName() + "Email: " + user.getEmail() + "Password: " + user.getPassword() + "Phone: " + user.getPhone());

            if (row != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(row);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el usuario. Verifica los datos proporcionados.");

        } catch (DataAccessException e) {
            String errorMessage = "Error de base de datos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Error inesperado: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    public ResponseEntity updateUser(int id, User user) {
        try {
            System.out.println("Intentando actualizar usuario con ID: " + id);
            System.out.println("Datos recibidos: Nombre=" + user.getName() + ", Email=" + user.getEmail() + ", Phone=" + user.getPhone());

            int row = userDao.updateUser(id, user.getName(), user.getEmail(), user.getPassword(), user.getPhone());
            if (row != 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body(user);
            }else{
                System.out.println("No se pudo actualizar el usuario. Verifica los datos.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo actualizar el usuario. Verifica los datos proporcionados.");
            }

        } catch (DataAccessException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }
}
