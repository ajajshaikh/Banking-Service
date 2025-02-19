package com.ajaj.config;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.ajaj.model.User;
import com.ajaj.repository.UserRepository;

import java.util.*;

@Component
public class CustomAuth implements AuthenticationProvider {
	public static String username = new String();

	@Autowired
	UserRepository userrepository;

	@Override
	public Authentication authenticate(Authentication auth) {

		username = auth.getName();
		String pass = auth.getCredentials().toString();
		String password = new String();
		if (userrepository.findByUsername(username).isEmpty() == false) {
			List<User> list = userrepository.findByUsername(username);
			for (User u : list)
				password = u.getPassword();
			if (password.equals(pass))
				return new UsernamePasswordAuthenticationToken(username, pass, Collections.emptyList());
			else
				throw new BadCredentialsException("authentication failed");

		} else
			throw new BadCredentialsException("authentication failed");
	}

	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}

}
