package com.example.ElMercaderLTDA.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.ElMercaderLTDA.Model.User;
import com.example.ElMercaderLTDA.Services.UserServices;

import java.util.List;

@RestController
@CrossOrigin(origins= "*", methods = {
    RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE
})
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserServices userServices;

    @GetMapping("/all")
    public List<User> getAll() {
        return userServices.getAll();
    }

    @GetMapping("/{email}")
    public boolean emailIsUsed(@PathVariable String email) {
        return userServices.emailIsUsed(email);
    }

    @GetMapping("/{email}/{password}")
    public User userAuth(@PathVariable String email, @PathVariable String password) {
        return userServices.userAuth(email, password);
    }

    // El plan acá es regresar algo al html en vez de guardarlo en la base de datos
    // Dependiendo de cúal sea la respuesta de la existencia de un email
    // Haciendo la solicitud por el lado del servidor, no por el front end
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody User user) {
        
        if (userServices.emailIsUsed(user.getEmail())) {
            return user;
        } else {
            return userServices.save(user);
        }
    }
}
