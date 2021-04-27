package com.example.crypto;

import com.example.crypto.controller.ExchangeRateController;
import com.example.crypto.repository.ExchangeRateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CryptoApplicationTests {

	@Autowired
	private ExchangeRateController controller;

//	@MockBean
//	private ExchangeRateRepository exchangeRateRepository;
//
//	@Autowired
//	private MockMvc mvc;

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();

//		mvc.perform(get("/bitcoin/latest")
//				.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}


}
