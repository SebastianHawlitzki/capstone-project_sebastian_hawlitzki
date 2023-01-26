package de.neuefische.backend.appUser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
                                    "accountBalance": 0
                                }
                                """,
                        true
                )
        );

    }



}