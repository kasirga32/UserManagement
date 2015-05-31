package com.mete.infonal.controller;

import static org.hamcrest.Matchers.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mete.infonal.model.User;
import com.mete.infonal.service.imp.UserService;

public class UserControllerTest {

	@InjectMocks
	UserController controller;

	@Mock
	UserService service;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testGoHomePage() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(
					MockMvcResultMatchers.status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllUsers() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/user"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(
							MockMvcResultMatchers.model().attributeExists(
									"allUsers"))
					.andExpect(MockMvcResultMatchers.view().name("output"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCreate() {
		User u = new User();
		u.setId("3");
		u.setName("Test");
		u.setLastName("Db");
		u.setPhoneNumber("06492353211");
		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/user/save")
	                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                .sessionAttr("user", u)
	        )
	                .andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testIsValidCaptcha() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/user/validate"))
					.andExpect(MockMvcResultMatchers.status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDeletePerson() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/user/delete"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("output"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
