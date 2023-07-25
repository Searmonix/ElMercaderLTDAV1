package com.example.ElMercaderLTDA.Repository.CRUD;

import com.example.ElMercaderLTDA.Model.User;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserCRUDRepository extends CrudRepository <User, Integer>{
    
    // Encontrar por email digitado
    // @Query("SELECT * FROM User WHERE EMAIL = email")
    public Optional<User> findByEmail(String email);

    // Encontrar por email y contraseña
    // @Query("SELECT * FROM User WHERE EMAIL AND PASSWORD = email, password")
    public Optional<User> findByEmailAndPassword(String email, String password);
    
}
