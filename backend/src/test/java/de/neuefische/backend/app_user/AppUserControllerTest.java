package de.neuefische.backend.app_user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AppUserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void create_whenSignUp_shouldCreateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "id": "1",
                            "username": "user",
                            "password": "password",
                            "accountType": "private",
                            "accountBalance": 0
                        }
                        """)
        ).andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.content().json("""
                                  {
                                    "id": "1",
                                    "username": "user",
                                    "password": "",
                                    "accountNumber": 1,
                                    "accountType": "private",
                                    "accountBalance": 1500.0
                                }
                                """,
                        true
                )
        );

    }

    @Test
    void create_whenAppUserAlreadyExists_shouldReturnConflict() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "id": "1",
                            "username": "user",
                            "password": "password",
                            "accountType": "private",
                            "accountBalance": 0
                        }
                        """)).andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.content().json("""
                                  {
                                    "id": "1",
                                    "username": "user",
                                    "password": "",
                                    "accountNumber": 1,
                                    "accountType": "private",
                                    "accountBalance": 1500
                                }
                                """,
                        true
                )
        );
        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "id": "1",
                            "username": "user",
                            "password": "password",
                            "accountType": "private",
                            "accountBalance": 0
                        }
                        """)).andExpectAll(
                MockMvcResultMatchers.status().isConflict());

    }


    @Test
    @WithMockUser(username = "user", roles = "BASIC")
    void login_whenAppUserExist_thenReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users/login"))
                .andExpectAll(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void me_whenAppUserNotLoggedIn_thenReturn401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/app-users/me")).
                andExpectAll(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", roles = "BASIC")
    void me_whenAppUserLoggedIn_thenReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/app-users/me")).
                andExpectAll(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void logout_whenAppUserNotLoggedInAndLogsOut_shouldReturn401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/app-users/logout"))
                .andExpectAll(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", roles = "BASIC")
    void logout_whenAppUserLoggedInAndLogsOut_shouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/app-users/logout"))
                .andExpectAll(MockMvcResultMatchers.status().isOk());
    }

}