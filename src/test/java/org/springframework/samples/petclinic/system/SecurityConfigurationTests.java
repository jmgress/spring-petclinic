/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.system;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for SSO security configuration.
 *
 * @author Copilot
 */
@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigurationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void publicResourcesShouldBeAccessible() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk());
	}

	@Test
	void protectedResourcesShouldRequireAuthentication() throws Exception {
		mockMvc.perform(get("/vets.html"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrlPattern("**/oauth2/authorization/sso"));
	}

	@Test
	@WithMockUser
	void authenticatedUserCanAccessProtectedResources() throws Exception {
		mockMvc.perform(get("/vets.html")).andExpect(status().isOk());
	}

}