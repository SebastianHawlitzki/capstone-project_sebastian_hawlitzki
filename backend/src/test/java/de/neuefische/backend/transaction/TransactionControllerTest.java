package de.neuefische.backend.transaction;

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


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "senderUser", roles = "BASIC")
    void getAllFromAuthUser_whenAuthUserAccountNumberEqualsTransactionSenderAccountNumberOrTransactionReceiverAccountNumber_thenReturnAuthUserTransactions() throws Exception {
        String senderUser = """
                {
                    "username":"senderUser",
                    "password":"password",
                    "accountNumber": "", 
                    "accountType": "private",
                    "accountBalance": ""
                }
                """;

        String expectedSenderUser = """
                {
                    "username": "senderUser",
                    "password": "",
                    "accountNumber": 1,
                    "accountType": "private",
                    "accountBalance": 1500
                }
                """;

        String receiverUser = """
                {
                    "username": "receiverUser",
                    "password": "password",
                    "accountNumber": "",
                    "accountType": "private",
                    "accountBalance": ""
                }
                """;

        String expectedReceiverUser = """
                {
                    "username": "receiverUser",
                    "password": "",
                    "accountNumber": 2, 
                    "accountType": "private",
                    "accountBalance": 1500
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(senderUser))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedSenderUser));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(receiverUser))
                .andExpect(content().json(expectedReceiverUser));


        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions").with(user("senderUser"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                                
                        {       "id": "1",
                                "senderAccountNumber": 1,
                                "receiverAccountNumber": 2,
                                "amount": 500,
                                "purpose": "test transaction",
                                "transactionDate": ""
                        }
                         
                        """));


        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions").with(user("receiverUser"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                                
                        {       "id": "2",
                                "senderAccountNumber": 2,
                                "receiverAccountNumber": 1,
                                "amount": 500,
                                "purpose": "test transaction",
                                "transactionDate": ""
                        }
                         
                        """));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions").with(user("senderUser")))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                  [
                                  {
                                "id": "1",
                                "senderAccountNumber": 1,
                                "receiverAccountNumber": 2,
                                "amount": 500.0,
                                "purpose": "test transaction"
                                },
                                {       
                                "id": "2",
                                "senderAccountNumber": 2,
                                "receiverAccountNumber": 1,
                                "amount": 500.0,
                                "purpose": "test transaction"
                                }
                                                         
                                ]
                                """,
                        false));

    }

    @Test
    @WithMockUser(username = "senderUser", roles = "BASIC")
    void getAllFromAuthUser_whenNoTransactionExists_thenReturnEmptyArray() throws Exception {

        String senderUser = """
                {
                    "username":"senderUser",
                    "password":"password",
                    "accountNumber": "", 
                    "accountType": "private",
                    "accountBalance": ""
                }
                """;

        String expectedSenderUser = """
                {
                    "username": "senderUser",
                    "password": "",
                    "accountNumber": 1, 
                    "accountType": "private",
                    "accountBalance": 1500
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(senderUser))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedSenderUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions").with(user("senderUser")))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                  []
                                """,
                        true));

    }


    @Test
    @WithMockUser(username = "senderUser", roles = "BASIC")
    void sendTransaction_whenTransactionSendWithLessAmountThanAccountBalance_thenShouldReturnTransaction() throws Exception {
        String senderUser = """
                {
                    "username":"senderUser",
                    "password":"password",
                    "accountNumber": "", 
                    "accountType": "private",
                    "accountBalance": ""
                }
                """;

        String expectedSenderUser = """
                {
                    "username": "senderUser",
                    "password": "",
                    "accountNumber": 1, 
                    "accountType": "private",
                    "accountBalance": 1500
                }
                """;

        String receiverUser = """
                {
                    "username": "receiverUser",
                    "password": "password",
                    "accountNumber": "",
                    "accountType": "private",
                    "accountBalance": ""
                }
                """;

        String expectedReceiverUser = """
                {
                    "username": "receiverUser",
                    "password": "",
                    "accountNumber": 2, 
                    "accountType": "private",
                    "accountBalance": 1500
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(senderUser))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedSenderUser));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(receiverUser))
                .andExpect(content().json(expectedReceiverUser));


        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {       "id": "1",
                                "senderAccountNumber": 1,
                                "receiverAccountNumber": 2,
                                "amount": 500,
                                "purpose": "test transaction",
                                "transactionDate": ""
                        }
                        """)).andExpectAll(
                status().isOk(),
                content().json("""
                                  {
                                "id": "1",
                                "senderAccountNumber": 1,
                                "receiverAccountNumber": 2,
                                "amount": 500,
                                "purpose": "test transaction"
                                }
                                """,
                        false
                )
        );

    }

    @Test
    @WithMockUser(username = "senderUser", roles = "BASIC")
    void sendTransaction_whenPostTransactionWithAmount500_thenSenderUserAccountBalanceShouldReduceFor500() throws Exception {
        String senderUser = """
                {   
                    "id": "1",
                    "username":"senderUser",
                    "password":"password",
                    "accountNumber": "", 
                    "accountType": "private",
                    "accountBalance": ""
                }
                """;

        String expectedSenderUser = """
                {
                    "id": "1",
                    "username": "senderUser",
                    "password": "",
                    "accountNumber": 1, 
                    "accountType": "private",
                    "accountBalance": 1500
                }
                """;

        String receiverUser = """
                {
                    "username": "receiverUser",
                    "password": "password",
                    "accountNumber": "",
                    "accountType": "private",
                    "accountBalance": ""
                }
                """;

        String expectedReceiverUser = """
                {
                    "username": "receiverUser",
                    "password": "",
                    "accountNumber": 2, 
                    "accountType": "private",
                    "accountBalance": 1500
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(senderUser))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedSenderUser));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(receiverUser))
                .andExpect(content().json(expectedReceiverUser));


        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {       "id": "1",
                                "senderAccountNumber": 1,
                                "receiverAccountNumber": 2,
                                "amount": 500,
                                "purpose": "test transaction",
                                "transactionDate": ""
                        }
                        """)).andExpectAll(
                status().isOk(),
                content().json("""
                                  {
                                 "id": "1",
                                "senderAccountNumber": 1,
                                "receiverAccountNumber": 2,
                                "amount": 500,
                                "purpose": "test transaction"
                                }
                                """,
                        false
                )
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/api/app-users/me"))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                                          {
                                              "id": "1",
                                              "username": "senderUser",
                                              "password": "",
                                              "accountNumber": 1, 
                                              "accountType": "private",
                                              "accountBalance": 1000
                                          }
                                        """,
                                true
                        )
                );
    }


    @Test
    @WithMockUser(username = "senderUser", roles = "BASIC")
    void sendTransaction_whenPostTransactionWithAmountHigherThanAccountBalance_thenShouldReturnBadRequestException() throws Exception {
        String senderUser = """
                {
                    "username":"senderUser",
                    "password":"password",
                    "accountNumber": "", 
                    "accountType": "private",
                    "accountBalance": ""
                }
                """;

        String expectedSenderUser = """
                {
                    "username": "senderUser",
                    "password": "",
                    "accountNumber": 1, 
                    "accountType": "private",
                    "accountBalance": 1500
                }
                """;

        String receiverUser = """
                {
                    "username": "receiverUser",
                    "password": "password",
                    "accountNumber": "",
                    "accountType": "private",
                    "accountBalance": ""
                }
                """;

        String expectedReceiverUser = """
                {
                    "username": "receiverUser",
                    "password": "",
                    "accountNumber": 2, 
                    "accountType": "private",
                    "accountBalance": 1500
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(senderUser))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedSenderUser));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(receiverUser))
                .andExpect(content().json(expectedReceiverUser));


        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {       "id": "1",
                                "senderAccountNumber": 1,
                                "receiverAccountNumber": 2,
                                "amount": 2000,
                                "purpose": "test transaction",
                                "transactionDate": ""
                        }
                        """)).andExpectAll(MockMvcResultMatchers.status().isBadRequest()
        );

    }

    @Test
    @WithMockUser(username = "senderUser", roles = "BASIC")
    void sendTransaction_PostTransactionAndReceiverAccountNumberNotExists_thenShouldReturnItemNotFoundException() throws Exception {
        String senderUser = """
                {
                    "username":"senderUser",
                    "password":"password",
                    "accountNumber": "", 
                    "accountType": "private",
                    "accountBalance": ""
                }
                """;

        String expectedSenderUser = """
                {
                    "username": "senderUser",
                    "password": "",
                    "accountNumber": 1, 
                    "accountType": "private",
                    "accountBalance": 1500
                }
                """;

        String receiverUser = """
                {
                    "username": "receiverUser",
                    "password": "password",
                    "accountNumber": "",
                    "accountType": "private",
                    "accountBalance": ""
                }
                """;

        String expectedReceiverUser = """
                {
                    "username": "receiverUser",
                    "password": "",
                    "accountNumber": 2, 
                    "accountType": "private",
                    "accountBalance": 1500
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(senderUser))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedSenderUser));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(receiverUser))
                .andExpect(content().json(expectedReceiverUser));


        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {       "id": "1",
                                "senderAccountNumber": 1,
                                "receiverAccountNumber": 3,
                                "amount": 500,
                                "purpose": "test transaction",
                                "transactionDate": ""
                        }
                        """)).andExpectAll(MockMvcResultMatchers.status().isNotFound()
        );

    }

    @Test
    @WithMockUser(username = "senderUser", roles = "BASIC")
    void sendTransaction_PostTransactionAndReceiverAccountNumberExists_thenShouldReturnTransaction() throws Exception {
        String senderUser = """
                {
                    "username":"senderUser",
                    "password":"password",
                    "accountNumber": "", 
                    "accountType": "private",
                    "accountBalance": ""
                }
                """;

        String expectedSenderUser = """
                {
                    "username": "senderUser",
                    "password": "",
                    "accountNumber": 1, 
                    "accountType": "private",
                    "accountBalance": 1500
                }
                """;

        String receiverUser = """
                {
                    "username": "receiverUser",
                    "password": "password",
                    "accountNumber": "",
                    "accountType": "private",
                    "accountBalance": ""
                }
                """;

        String expectedReceiverUser = """
                {
                    "username": "receiverUser",
                    "password": "",
                    "accountNumber": 2, 
                    "accountType": "private",
                    "accountBalance": 1500
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(senderUser))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedSenderUser));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(receiverUser))
                .andExpect(content().json(expectedReceiverUser));


        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {       "id": "1",
                                "senderAccountNumber": 1,
                                "receiverAccountNumber": 2,
                                "amount": 500,
                                "purpose": "test transaction",
                                "transactionDate": ""
                        }
                        """)).andExpectAll(
                status().isOk(),
                content().json("""
                                  {
                                "id": "1",
                                "senderAccountNumber": 1,
                                "receiverAccountNumber": 2,
                                "amount": 500,
                                "purpose": "test transaction"
                                }
                                """,
                        false
                )
        );

    }

}