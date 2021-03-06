package it.f2informatica.webapp.test.security;

import it.f2informatica.mongodb.domain.User;
import it.f2informatica.services.responses.UserResponse;
import it.f2informatica.webapp.gateway.UserServiceGateway;
import it.f2informatica.webapp.security.CustomAuthenticationSuccessHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomAuthenticationSuccessHandlerTest {

	@Mock
	private UserServiceGateway userServiceGateway;

	@Mock
	private HttpSession session;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private Authentication authentication;

	private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

	@Before
	public void setUp() {
		authenticationSuccessHandler = new CustomAuthenticationSuccessHandler();
		when(authentication.getName()).thenReturn("username");
		when(request.getSession(true)).thenReturn(session);
		when(userServiceGateway.getAuthenticatedUser("username")).thenReturn(createUser());
		authenticationSuccessHandler.setUserServiceGateway(userServiceGateway);
	}

	@Test
	public void onAuthenticationSuccess() throws ServletException, IOException {
		ArgumentCaptor<String> attributeArgumentCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
		// ------- invocation time --------------
		authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
		// --------------------------------------
		verify(session).setAttribute(attributeArgumentCaptor.capture(), userArgumentCaptor.capture());
		assertThat(attributeArgumentCaptor.getValue()).isEqualTo("user");
		assertThat(userArgumentCaptor.getValue()).isEqualTo(createUser());
	}

	private UserResponse createUser() {
		UserResponse user = new UserResponse();
		user.setUserId("1234567890");
		user.setUsername("username");
		return user;
	}

}
