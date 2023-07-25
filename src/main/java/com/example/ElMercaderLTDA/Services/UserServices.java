package com.example.ElMercaderLTDA.Services;

import com.example.ElMercaderLTDA.Model.User;
import com.example.ElMercaderLTDA.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices {
    
    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.getAll();
    }


    public Optional<User> getUser(int id) {
        return userRepository.getUser(id);
    }


    public boolean emailIsUsed(String email) {
        /* Tipo de dato usuario, con la condición que puede ser Null o no.
        userExists, trae el email. Si el email existe, el dato Optional junto al isPresent devolverá True
        Solamente para saber si existe o no el email */

        // Busca si el email existe. 
        // ¿Existe? userExists.isPresent(); = True
        // ¿No existe? Optional = Null; userExists.isPresent(); = False     
        Optional<User> userExists = userRepository.findByEmail(email);
        return userExists.isPresent(); 
    }


    public boolean validEntries(User user) {
        return (user.getEmail() != null && user.getPassword() != null && user.getName() != null);
    }


    public User save(User user) {
        // Negación se encarga de iniciar o no el if. Cuando un email exista debería ser True
        // Pero no podemos guardar emails ya existentes, por lo que negar cuando un email no exista
        // permite guardarlo 
        if (!emailIsUsed(user.getEmail())) {           
            if (validEntries(user)) {
                return userRepository.save(user);
            }
        }
        return user;
    }


    public User userAuth(String email, String password) {
        Optional<User> userRegistered = userRepository.findByEmailAndPassword(email, password);
        // Retorna userRegistered. Pero si la instancia de Optional.isPresent() No tiene valor.
        // Retorna un valor generado, basado en el resultado de función provista 
        return userRegistered.orElseGet(() -> new User(null, email, password, "NO DEFINIDO"));
    } 
}


