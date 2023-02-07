package de.neuefische.backend.controller;

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


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;


    // Test cases:
    // 1. whenAuthUserAccountNumberEqualsTransactionSenderAccountNumber_thenReturnAuthUserTransactions
    // 2. whenAuthUserAccountNumberEqualsTransactionReceiverAccountNumber_thenReturnAuthUserTransactions

   /*
    @Test
    @WithMockUser(username = "senderUser", roles = "BASIC")
    void getAllFromAuthUser_whenEmpty_thenReturn400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions"))
                .andExpectAll(
                        status().isBadRequest()
                );
    }

    */
   @Test
   @WithMockUser(username = "authUser", roles = "BASIC")
    void getAllFromAuthUser_whenAuthUserAccountNumberEqualsTransactionSenderAccountNumber_thenReturnAuthUserTransactions() throws Exception{
       String authUser = """
                {
                    "username":"authUser",
                    "password":"password",
                    "accountNumber": "", 
                    "accountType": "private",
                    "accountBalance": ""
                }
                """;

       String expectedAuthUser = """
                {
                    "username": "authUser",
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
                       .content(authUser))
               .andExpect(status().isOk())
               .andExpect(content().json(expectedAuthUser));

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
                         
                        """));


       mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions"))
               .andExpect(status().isOk())
               .andExpect(content().json("""
                                  [
                                  {
                                "id": "1",
                                "senderAccountNumber": 1,
                                "receiverAccountNumber": 2,
                                "amount": 500.0,
                                "purpose": "test transaction"
                                }
                                ]
                                """,
                               false));

   }

/*
    @Test
    @WithMockUser(username = "authUser", roles = "BASIC")
    void getAllFromAuthUser_whenAuthUserAccountNumberEqualsTransactionReceiverAccountNumber_thenReturnAuthUserTransactions() throws Exception{
        String authUser = """
                {
                    "username":"authUser",
                    "password":"password",
                    "accountNumber": "", 
                    "accountType": "private",
                    "accountBalance": ""
                }
                """;

        String expectedAuthUser = """
                {
                    "username": "authUser",
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

        String user3 = """
                {
                    "username": "receiverUser",
                    "password": "password",
                    "accountNumber": "",
                    "accountType": "private",
                    "accountBalance": ""
                }
                """;

        String expectedUser3 = """
                {
                    "username": "receiverUser",
                    "password": "",
                    "accountNumber": 3, 
                    "accountType": "private",
                    "accountBalance": 1500
                }
                """;

        String user4 = """
                {
                    "username": "receiverUser",
                    "password": "password",
                    "accountNumber": "",
                    "accountType": "private",
                    "accountBalance": ""
                }
                """;

        String expectedUser4 = """
                {
                    "username": "receiverUser",
                    "password": "",
                    "accountNumber": 4, 
                    "accountType": "private",
                    "accountBalance": 1500
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authUser))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedAuthUser));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(receiverUser))
                .andExpect(content().json(expectedReceiverUser));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user3))
                .andExpect(content().json(expectedUser3));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user4))
                .andExpect(content().json(expectedUser4));


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
                         
                        """));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        
                        {       "id": "2",
                                "senderAccountNumber": 3,
                                "receiverAccountNumber": 4,
                                "amount": 500,
                                "purpose": "test transaction",
                                "transactionDate": ""
                        }
                         
                        """));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                  [
                                  {
                                "id": "1",
                                "senderAccountNumber": 1,
                                "receiverAccountNumber": 2,
                                "amount": 500.0,
                                "purpose": "test transaction"
                                }
                                ]
                                """,
                        false));

    }


 */


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
    void sendTransaction_whenPostTransactionWithAmountHigherThanAccountBalance_thenShouldReturnBadRequestException() throws Exception{
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
    void sendTransaction_PostTransactionAndReceiverAccountNumberNotExists_thenShouldReturnItemNotFoundException() throws Exception{
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