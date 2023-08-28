package com.example.MyBookShopApp.security.service;

import com.example.MyBookShopApp.security.BookstoreUserRepository;
import com.example.MyBookShopApp.security.ContactConfirmationPayload;
import com.example.MyBookShopApp.security.ContactConfirmationResponse;
import com.example.MyBookShopApp.security.RegistrationForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
public class ApiSecurityTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final BookstoreUserRepository bookstoreUserRepository;
    @MockBean
    private BookstoreUserRegister service;

    private static final RegistrationForm form = new RegistrationForm();

    @Autowired
    public ApiSecurityTest(MockMvc mockMvc, ObjectMapper objectMapper, BookstoreUserRepository bookstoreUserRepository) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.bookstoreUserRepository = bookstoreUserRepository;

    }

    @BeforeAll
    public static void init() {
        form.setEmail("123456@mail.ru");
        form.setName("123456");
        form.setPhone("+79999999999");
        form.setPass("123456");
    }

    @Test
    public void registerTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        post("/reg")
                                .content(objectMapper.writeValueAsString(form))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Mockito.verify(service, Mockito.times(1)).registerNewUser(any());
    }

    @Test
    public void loginTest() throws Exception {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("");
        doReturn(response)
                .when(service)
                .jwtLogin(any());
        MvcResult mvcResult = mockMvc.perform(post("/login")
                        .content(objectMapper.writeValueAsString(new ContactConfirmationPayload()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Mockito.verify(service, Mockito.times(1)).jwtLogin(any());
        assertEquals(response.getResult(), Objects.requireNonNull(mvcResult.getResponse().getCookie("token")).getValue());
    }

    @Test
    public void logoutTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/logout").cookie(new Cookie("token", UUID.randomUUID().toString())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/signin"))
                .andReturn();
        Cookie cookie = mvcResult.getResponse().getCookie("token");

        if (Objects.nonNull(cookie)) {
            assertNull(cookie.getValue());
        }
    }
}
