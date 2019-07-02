/**
 * Validate Reference: https://www.mkyong.com/spring-boot/spring-rest-validation-example/
 * Security Reference: https://www.mkyong.com/spring-boot/spring-rest-spring-security-example/
 */
package com.stephen.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stephen.model.Book;
import com.stephen.repository.BookRepository;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureMockMvc(secure = false)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerTest {

	private static final ObjectMapper om = new ObjectMapper();
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BookRepository mockRepository;
	
	@Before
	public void init() {
		System.out.println("***********************");
		Book book = new Book(1L, "A Guide to the Bodhisattva Way of Life", "Santideva", new BigDecimal("15.41"));
		when(mockRepository.findById(1L)).thenReturn(Optional.of(book));
		System.out.println("*** mockRepository.count = " + (mockRepository.findById(1L) != null));
		
		System.out.println("Init done");
	}
	
	//@WithMockUser(username = "USER")
	@WithMockUser(roles={"USER"})
	@Test
	public void find_login_ok() throws Exception {
		System.out.println("***** find_login_ok *****");
		System.out.println("***** mockRepository.count = " + (mockRepository.findById(1L) != null));
		mockMvc.perform(get("/books/1"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id", is(1)))
					.andExpect(jsonPath("$.name", is("A Guide to the Bodhisattva Way of Life")))
					.andExpect(jsonPath("$.author", is("Santideva")))
					.andExpect(jsonPath("$.price", is(15.41)));
	}
	
	@Test
	public void find_nologin_401() throws Exception {
		mockMvc.perform(get("/books/1"))
					.andDo(print())
					.andExpect(status().isUnauthorized());
	}

	/*
    {
        "timestamp":"2019-03-05T09:34:13.280+0000",
        "status":400,
        "errors":["Author is not allowed.","Please provide a price","Please provide an author"]
    }
    */
	@Test
	@WithMockUser(roles={"ADMIN"})
	public void save_emptyAuthor_emptyPrice_400() throws Exception {
		String bookInJson = "{\"name\":\"ABC\"}";
		
		mockMvc.perform(post("/books")
				.content(bookInJson)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.timestamp", is(notNullValue())))
				.andExpect(jsonPath("$.status", is(400)))
				.andExpect(jsonPath("$.errors").isArray())
				.andExpect(jsonPath("$.errors", hasSize(3)))
				.andExpect(jsonPath("$.errors", hasItem("Author is not allowed.")))
				.andExpect(jsonPath("$.errors", hasItem("Please provide an author")))
				.andExpect(jsonPath("$.errors", hasItem("Please provide a price")));
				
		verify(mockRepository, times(0)).save(any(Book.class));
	}
	
    /*
    {
        "timestamp":"2019-03-05T09:34:13.207+0000",
        "status":400,
        "errors":["Author is not allowed."]
    }
    */
	@Test
	@WithMockUser(roles={"ADMIN"}) //@WithMockUser(username="admin", roles={"ADMIN"}) 
	public void save_invalidAuthor_400() throws Exception {
		System.out.println("*** save_invalidAuthor_400 ***");
		String bookInJson = "{\"name\":\" Spring REST tutorials\", \"author\":\"abc\",\"price\":\"9.99\"}";
		
		mockMvc.perform(post("/books")
					.content(bookInJson)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.timestamp", is(notNullValue())))
					.andExpect(jsonPath("$.status", is(400)))
					.andExpect(jsonPath("$.errors").isArray())
					.andExpect(jsonPath("$.errors", hasSize(1)))
					.andExpect(jsonPath("$.errors", hasItem("Author is not allowed.")));
		
		verify(mockRepository, times(0)).save(any(Book.class));
			
	}
}