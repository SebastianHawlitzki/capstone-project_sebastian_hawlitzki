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


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TransactionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getAll_whenEmpty_thenReturn200() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/transactions"))
                .andExpectAll(
                        status().isOk(),
                        content().json("[]")
                );
    }


    // sendTransaction test cases
    // 1. whenSenderUserPostTransactionWithAmount500_thenSenderUserAccountBalanceShouldReduceFor500
    // 2. whenSenderUserPostTransactionWithAmount500_thenReceiverUserAccountBalanceShouldRiseFor500
    // 3. whenSenderUserPostTransactionWithAmountHigherThanAccountBalance_thenShouldReturnNotEnoughBalanceException
    // 4. whenSenderUserPostTransactionAndAccountBalanceIsSameOrEquals0_thenShouldReturnNotEnoughBalanceException
    // 5. whenSenderUserPostTransactionAndReceiverUserNotExists_thenShouldReturnItemNotFoundException
    // 6. whenSenderUserPostTransactionAndReceiverUserExists_thenShouldReturnTransaction

    @Test
    @WithMockUser(username = "senderUser", password = "password", roles = "BASIC")
    void sendTransaction_whenTransactionSend_thenShouldReturnTransaction() throws Exception {
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

        mvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(senderUser))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedSenderUser));

        mvc.perform(MockMvcRequestBuilders.post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(receiverUser))
                .andExpect(content().json(expectedReceiverUser));



        mvc.perform(MockMvcRequestBuilders.post("/api/transactions")
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