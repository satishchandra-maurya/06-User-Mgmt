package in.satish.rest;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.satish.constants.AppConstants;
import in.satish.props.AppProperties;
import in.satish.request.ActivateAccount;
import in.satish.request.LoginRequest;
import in.satish.request.SignupRequest;
import in.satish.response.SearchResponse;
import in.satish.service.UserService;

@RestController
public class UserRestController {
	String response = AppConstants.EMPTY_STR;
	private UserService userService;
	
	private Map<String, String> messages;
	
	public UserRestController(UserService userService, AppProperties appProp) {
		this.userService = userService;
		this.messages = appProp.getMessages(); 
	}
	
	@PostMapping("/user")
	public ResponseEntity<String> saveUser(@RequestBody SignupRequest sr){
		
		boolean insertUser = userService.insertUser(sr);
		if(insertUser) {
			response = messages.get(AppConstants.SAVE_SUCC);
			return new ResponseEntity<> (response, HttpStatus.CREATED);
		}else {
			response = messages.get(AppConstants.SAVE_FAIL);
			return new ResponseEntity<> (response, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/activateAccount")
	public ResponseEntity<String> activateUserAccount(@RequestBody ActivateAccount ac){
		
		boolean activatedUserAccount = userService.activateUserAccount(ac);
		if(activatedUserAccount) {
			response = messages.get(AppConstants.ACTIVATE_ACCOUNT_SUCC);
			return new ResponseEntity<> (response, HttpStatus.CREATED);
		}else {
			response = messages.get(AppConstants.ACTIVATE_ACCOUNT_FAIL);
			return new ResponseEntity<> (response, HttpStatus.BAD_REQUEST);
		}
			
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<SearchResponse>> getAllUser(){
		List<SearchResponse> findAllUser = userService.findAllUser();
		return new ResponseEntity<> (findAllUser, HttpStatus.OK);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<SignupRequest> editUser(@PathVariable Integer id){
		SignupRequest user = userService.getUserById(id);
		return new ResponseEntity<> (user, HttpStatus.OK);
	}
	
	@PutMapping("/user")
	public ResponseEntity<String> updateUser(@RequestBody SignupRequest sr){
		
		boolean updateUser = userService.updateUser(sr);
		
		if(updateUser) 
		   response = messages.get(AppConstants.UPDATE_SUCC);
		else
			response = messages.get(AppConstants.UPDATE_FAIL);
		
		return new ResponseEntity<> (response, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer id){
		
		boolean deleteUser = userService.deleteUser(id);
		if(deleteUser)
			response = messages.get(AppConstants.DELETE_SUCC);
		else
			response = messages.get(AppConstants.DELETE_FAIL);	
		
		return new ResponseEntity<> (response, HttpStatus.OK);
	}
	
	@PutMapping("/user/{id}/{status}")
	public ResponseEntity<String> changeAccountStatus(@PathVariable Integer id, @PathVariable String status){
		
		boolean activateUser = userService.changeAccountStatus(id, status);
		if(activateUser) {
			response = messages.get(AppConstants.CHANGE_SUCC);
			return new ResponseEntity<> (response, HttpStatus.OK);
		}else {
			response = messages.get(AppConstants.CHANGE_FAIL);
			return new ResponseEntity<> (response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> userLogin(@RequestBody LoginRequest lr){
		String loginUser = userService.loginUser(lr);
		return new ResponseEntity<> (loginUser, HttpStatus.OK);
	}
	
	@GetMapping("/forgetPassword/{email}")
	public ResponseEntity<String> recoverForgetPassword(@PathVariable String email){
		String status = userService.forgetPassword(email);
		return new ResponseEntity<> (status, HttpStatus.OK);
		
	}
}

