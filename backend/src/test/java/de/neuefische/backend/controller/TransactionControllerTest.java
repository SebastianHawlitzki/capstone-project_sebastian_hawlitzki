package de.neuefische.backend.controller;

import de.neuefische.backend.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;




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
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json("[]")
                );
    }

    @Test
    void getAll_whenTransactionsExist_thenReturn200() throws Exception {

        java.util.Date date = new java.util.Date();
        Transaction transaction = new Transaction("1", 1, 2, 500, "Überweisung an ", date);

        mvc.perform(MockMvcRequestBuilders.get("/api/transactions"))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json("[]")
                );

    }



}