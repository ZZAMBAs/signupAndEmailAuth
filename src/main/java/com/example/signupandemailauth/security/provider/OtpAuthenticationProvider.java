package com.example.signupandemailauth.security.provider;

import com.example.signupandemailauth.security.OtpAuthentication;
import com.example.signupandemailauth.security.provider.proxy.AuthenticationServerProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OtpAuthenticationProvider implements AuthenticationProvider {
    private final AuthenticationServerProxy proxy;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String name = auth.getName();
        String code = auth.getCredentials().toString();

        proxy.checkOTP(name, code);

        return new OtpAuthentication(name, code);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthentication.class.isAssignableFrom(authentication);
    }
}
