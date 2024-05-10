package com.rest.api.store.service;

import com.rest.api.store.dto.AuthDTO;
import com.rest.api.store.entity.Customer;
import com.rest.api.store.exception.CustomerAlreadyExistsException;
import com.rest.api.store.repository.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private SecurityContextRepository securityContextRepository;
    @Mock
    private AuthenticationManager authManager;
    @Mock
    private FindByIndexNameSessionRepository<? extends Session> sessionRepository;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private UserDetails userDetails;
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegister_NewCustomer_Success() {
        AuthDTO dto = new AuthDTO("test@example.com", "password");
        when(customerRepository.findByEmail(dto.email())).thenReturn(Optional.empty());

        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(dto.password())).thenReturn(encodedPassword);

        String result = authService.register(dto);

        assertEquals("Registered successfully!", result);
        verify(customerRepository, times(1)).save(any());
    }

    @Test
    void testRegister_ExistingCustomer_ExceptionThrown() {
        AuthDTO dto = new AuthDTO("existing@example.com", "password");
        when(customerRepository.findByEmail(dto.email())).thenReturn(Optional.of(new Customer()));

        assertThrows(CustomerAlreadyExistsException.class, () -> authService.register(dto));
        verify(customerRepository, never()).save(any());
    }

    @Test
    void testLogin() {
        AuthDTO dto = new AuthDTO("test@example.com", "password");
        Authentication authentication = mock(Authentication.class);

        when(authManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("test@example.com");


        when(request.getSession()).thenReturn(mock(HttpSession.class));

        authService.login(dto, request, response);

        verify(authManager, times(1)).authenticate(any());
        verify(securityContextRepository, times(1)).saveContext(any(), eq(request), eq(response));
    }
}
