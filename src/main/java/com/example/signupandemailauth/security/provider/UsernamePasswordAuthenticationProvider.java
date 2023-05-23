package com.example.signupandemailauth.security.provider;

import com.example.signupandemailauth.security.UsernamePasswordAuthentication;
import com.example.signupandemailauth.security.provider.proxy.AuthenticationServerProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    private final AuthenticationServerProxy proxy;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String name = auth.getName();
        String password = (String) auth.getCredentials();

        boolean checked = proxy.checkUser(name, password);

        if (checked)
            return new UsernamePasswordAuthentication(name, password);
        throw new BadCredentialsException("Bad Credential.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthentication.class.isAssignableFrom(authentication);
    }
}
