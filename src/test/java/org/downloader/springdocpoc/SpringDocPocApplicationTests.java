package org.downloader.springdocpoc;

import api.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import response.ResponseEnvelope;
import service.UserService;

import java.util.Optional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
class SpringDocPocApplicationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Autowired
	private UserService userService;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext,
					  RestDocumentationContextProvider restDocumentation) {

		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.apply(documentationConfiguration(restDocumentation))
				.build();
	}

	@Test
	public void testGetUser() throws Exception {

		int id = 1;
		Optional optional = userService.getUser(id);

		String userJson = null;
		if(optional.isPresent()) {
			User user = (User) optional.get();
			userJson = new ObjectMapper().writeValueAsString(user);
		}

        assert userJson != null;
        mockMvc.perform(get("/user?id=" + id)
				.contentType("application/json")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(userJson))
				.andDo(document("{methodName}",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint())));
	}

	@Test
	public void testRegisterUser() throws Exception {

		int id = 1;
		String password = "123456789";
		ResponseEnvelope expectedEnvelope = new ResponseEnvelope(true, "Registration successful!");
		String responseJson = new ObjectMapper().writeValueAsString(expectedEnvelope);

		mockMvc.perform(post("/user/register?id=" + id + "&password=" + password)
				.contentType("application/json")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(responseJson))
				.andDo(document("{methodName}",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint())));
	}
}
