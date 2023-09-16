package com.api.srs.resource;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@WithMockUser
public class GenericResourceTest {

  protected static final String MESSAGE = "Message Test";

  @Autowired
  private MockMvc mockMvc;

  protected void genericTestOKStatus(RequestBuilder requestBuilder) throws Exception {
    MockHttpServletResponse response = this.mockMvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn()
        .getResponse();

    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  protected void genericTestConflictStatus(RequestBuilder requestBuilder, String message) throws Exception {
    MockHttpServletResponse response = this.mockMvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isConflict())
        .andReturn()
        .getResponse();

    Assertions.assertTrue(HttpStatus.CONFLICT.value() == response.getStatus() && response.getContentAsString().equalsIgnoreCase(message));
  }

  protected void genericTestInternalErrorStatus(RequestBuilder requestBuilder, String message) throws Exception {
    MockHttpServletResponse response = this.mockMvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isInternalServerError())
        .andReturn()
        .getResponse();

    Assertions.assertTrue(HttpStatus.INTERNAL_SERVER_ERROR.value() == response.getStatus() && response.getContentAsString().equalsIgnoreCase(message));
  }
}
