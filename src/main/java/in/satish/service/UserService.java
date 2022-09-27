package in.satish.service;

import java.util.List;

import in.satish.request.ActivateAccount;
import in.satish.request.LoginRequest;
import in.satish.request.SignupRequest;
import in.satish.response.SearchResponse;

public interface UserService {

	public boolean insertUser(SignupRequest sr); // for  and updating inserting
	public boolean updateUser(SignupRequest sr); // for updating 
	public boolean deleteUser(Integer id); // for deleting
	public  SignupRequest getUserById(Integer id); // for getting single user
	public List<SearchResponse> findAllUser(); // for getting all user
	public boolean activateUserAccount(ActivateAccount ac);  // this is for activate account functionality
	public String forgetPassword(String email);  // this is for forget Password functionality
	public boolean changeAccountStatus(Integer id, String status); // for activated and deactivated
	public String loginUser(LoginRequest lr); // For User Login
}
