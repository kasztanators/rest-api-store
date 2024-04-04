package com.rest.api.store.service;

import com.rest.api.store.dto.AuthDTO;
import com.rest.api.store.entity.Cart;
import com.rest.api.store.entity.Customer;
import com.rest.api.store.exception.CustomerAlreadyExistsException;
import com.rest.api.store.repository.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Setter
public class AuthService {

    @Value(value = "${custom.max.session}")
    private int maxSession;

    private final PasswordEncoder passwordEncoder;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy;
    private final AuthenticationManager authManager;
    private final RedisIndexedSessionRepository redisIndexedSessionRepository;
    private final SessionRegistry sessionRegistry;
    private final CustomerRepository customerRepository;

    public AuthService(PasswordEncoder passwordEncoder,
                       SecurityContextRepository securityContextRepository,
                       AuthenticationManager authManager,
                       RedisIndexedSessionRepository redisIndexedSessionRepository,
                       SessionRegistry sessionRegistry,
                       CustomerRepository customerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.securityContextRepository = securityContextRepository;
        this.authManager = authManager;
        this.redisIndexedSessionRepository = redisIndexedSessionRepository;
        this.sessionRegistry = sessionRegistry;
        this.customerRepository = customerRepository;
        this.securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    }

    public String register(AuthDTO dto) {
        String email = dto.email().trim();

        Optional<Customer> exists = customerRepository
                .findByEmail(email);

        if (exists.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists!");
        }

        var customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(passwordEncoder.encode(dto.password()));
        customer.setLocked(true);
        customer.setAccountNonExpired(true);
        customer.setCredentialsNonExpired(true);
        customer.setEnabled(true);
        customer.setCart(new Cart());
        customerRepository.save(customer);
        return "Registered successfully!";
    }


    public String login(AuthDTO dto, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = authManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(
                dto.email().trim(), dto.password()));

        validateMaxSession(authentication);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        this.securityContextHolderStrategy.setContext(context);
        this.securityContextRepository.saveContext(context, request, response);
        String sessionId = request.getSession().getId();

        return sessionId;
    }


    private void validateMaxSession(Authentication authentication) {
        if (maxSession <= 0) {
            return;
        }

        var principal = (UserDetails) authentication.getPrincipal();
        List<SessionInformation> sessions = this.sessionRegistry.getAllSessions(principal, false);

        if (sessions.size() >= maxSession) {
            sessions.stream() //
                    .min(Comparator.comparing(SessionInformation::getLastRequest)) //
                    .ifPresent(sessionInfo -> this.redisIndexedSessionRepository.deleteById(sessionInfo.getSessionId()));
        }
    }

}
