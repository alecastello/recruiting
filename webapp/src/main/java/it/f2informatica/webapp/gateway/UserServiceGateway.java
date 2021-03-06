package it.f2informatica.webapp.gateway;

import it.f2informatica.services.requests.ChangePasswordRequest;
import it.f2informatica.services.requests.UserRequest;
import it.f2informatica.services.responses.RoleResponse;
import it.f2informatica.services.responses.UserResponse;
import it.f2informatica.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceGateway {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserResponse getAuthenticatedUser(String username) {
		return userService.findByUsername(username);
	}

	public Iterable<RoleResponse> loadRoles() {
		return userService.loadRoles();
	}

	public Page<UserResponse> findAllUsers(Pageable pageable) {
		return userService.findAll(pageable);
	}

	public UserResponse findUserById(String userId) {
		return userService.findUserById(userId);
	}

	public UserResponse saveUser(UserRequest request) {
		return userService.saveUser(request);
	}

	public boolean updateUser(UserRequest request) {
		return userService.updateUser(request);
	}

	public void deleteUser(String userId) {
		userService.deleteUser(userId);
	}

	public boolean updatePassword(ChangePasswordRequest request) {
		return userService.changePassword(request);
	}

	public UserRequest prepareNewUserToSave() {
		UserRequest userRequest = new UserRequest();
		userRequest.setRemovable(true);
		return userRequest;
	}

	public UserRequest prepareUserToUpdate(String userId) {
		UserResponse userResponse = userService.findUserById(userId);
		UserRequest userRequest = new UserRequest();
		userRequest.setUserId(userResponse.getUserId());
		userRequest.setUsername(userResponse.getUsername());
		RoleResponse role = userService.findRoleByName(userResponse.getAuthorization());
		userRequest.setRoleId(role.getRoleId());
		userRequest.setRemovable(userResponse.isRemovable());
		return userRequest;
	}

	public ChangePasswordRequest prepareChangePasswordRequest(String userId) {
		ChangePasswordRequest request = new ChangePasswordRequest();
		request.setUserId(userId);
		return request;
	}

}
