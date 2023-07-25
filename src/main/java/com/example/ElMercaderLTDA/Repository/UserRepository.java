package com.example.ElMercaderLTDA.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.ElMercaderLTDA.Model.User;
import com.example.ElMercaderLTDA.Repository.CRUD.UserCRUDRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    
    @Autowired
    private UserCRUDRepository userCRUDRepository;

    // Listar usuarios
    public List<User> getAll() {
        return (List<User>) userCRUDRepository.findAll();
    }

    // Encontrar por ID
    public Optional<User> getUser(int id) {
        return userCRUDRepository.findById(id);
    }

    // Encontrar por Email
    public Optional<User> findByEmail(String email) {
        return userCRUDRepository.findByEmail(email);
    }

    // Encontrar por Email y Contraseña
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userCRUDRepository.findByEmailAndPassword(email, password);
    }

    // Guardar
    public User save(User user) {
        return userCRUDRepository.save(user);
    }

    // Borrar
    public void delete(User user) {
        userCRUDRepository.delete(user);
    } 
}
 