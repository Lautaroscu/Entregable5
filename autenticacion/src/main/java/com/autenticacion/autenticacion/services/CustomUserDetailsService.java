package com.autenticacion.autenticacion.services;


import com.autenticacion.autenticacion.clients.AccountClient;
import com.autenticacion.autenticacion.clients.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountClient accountClient;

    @Autowired
    public CustomUserDetailsService(AccountClient accountClient) {
        super();
        this.accountClient = accountClient;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar la en la base de datos de cuentas mediante un cliente http por email
        AccountModel accountModel = accountClient.getByOwnerEmail(email);


        // Convertir el objeto User a un objeto UserDetails que entiende Spring Security
        return new org.springframework.security.core.userdetails.User(
                accountModel.getOwnerEmail(),
                accountModel.getPassword(),
                new ArrayList<>()
        );
    }


}
