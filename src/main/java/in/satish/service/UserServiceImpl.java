package in.satish.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import in.satish.custom.EmailSender;
import in.satish.entity.UserRegister;
import in.satish.repo.UserRepository;
import in.satish.request.ActivateAccount;
import in.satish.request.LoginRequest;
import in.satish.request.SignupRequest;
import in.satish.response.SearchResponse;

@Service
public class UserServiceImpl implements UserService{
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private SecureRandom random = new SecureRandom();
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailSender emailSender;
	
	@Override
	public boolean insertUser(SignupRequest sr) {
		UserRegister u = new UserRegister();
		BeanUtils.copyProperties(sr, u);
		
		u.setPassword(generateRandomPassword());
		u.setActiveSw("In-Active");
		
		UserRegister save = userRepository.save(u);
		
		String subject= "Your Registration Success";
		String filename = "REG_EMAIL_BODY.txt";
		String body = readEmailBody(u.getName(), u.getPassword(), filename);
		emailSender.sentEmail(sr.getEmail(), subject, body);
		
		return save.getId()!=null;
	}

	@Override
	public boolean activateUserAccount(ActivateAccount ac) {
		UserRegister u = new UserRegister();
		
		u.setEmail(ac.getEmail());
		
		u.setPassword(ac.getTempPass());
		
		Example<UserRegister> of = Example.of(u);
		List<UserRegister> findAll = userRepository.findAll(of);
		
		if(findAll.isEmpty()) {
			return false;
		}else {
			
			UserRegister userRegister = findAll.get(0);
			userRegister.setPassword(ac.getNewPass());
			userRegister.setActiveSw("Active");
			userRepository.save(userRegister);
			return true;
			
			
		}
	}

	@Override
	public List<SearchResponse> findAllUser() {
		List<UserRegister> findAll = userRepository.findAll();
		List<SearchResponse> response = new ArrayList<>();
		for(UserRegister entity:findAll) {
			SearchResponse s = new SearchResponse();
			BeanUtils.copyProperties(entity, s);
			response.add(s);
		}
		return response;
	}
	
	@Override
	public SignupRequest getUserById(Integer id) {
		Optional<UserRegister> findById = userRepository.findById(id);
		if(findById.isPresent()) {
		SignupRequest sr = new SignupRequest();
		UserRegister userRegister = findById.get();
		BeanUtils.copyProperties(userRegister, sr);
		return sr;
		}
		return null;
	}
	
	@Override
	public boolean deleteUser(Integer id) {
		boolean status = false;
		try {
			userRepository.deleteById(id);
			status = true;
		}catch(Exception e) {
			logger.error("Exception occured ", e);
		}
		return status;
	}

	@Override
	public boolean changeAccountStatus(Integer id, String status) {
		Optional<UserRegister> findById = userRepository.findById(id);
		if(findById.isPresent()) {
			UserRegister userRegister = findById.get();
			userRegister.setActiveSw(status);
			userRepository.save(userRegister);
			return true;
		}
		return false;
	}


	@Override
	public String forgetPassword(String email) {
		
		UserRegister entity = userRepository.findByEmail(email);
		
		if(entity==null) {
			return "Invalid Email";
		}	
		String subject ="Forget Password";
		String filename = "RECOVER_PASS_EMAIl_BODY.txt";
		// this method is used to reading data form the filename and prepare the body
		String body = readEmailBody(entity.getName(), entity.getPassword(), filename);
		// this is method is used to sent email 
		boolean sentEmail = emailSender.sentEmail(email, subject, body);
		if(sentEmail) {
			return "Password Sent to your Registered Email";
		}
		return null;
	}


	@Override
	public boolean updateUser(SignupRequest sr) {
		UserRegister user = new UserRegister();
		BeanUtils.copyProperties(sr, user);
		userRepository.save(user);
		return user.getId()!=null;
	}
	
	
	@Override
	public String loginUser(LoginRequest lr) {
		UserRegister entity = 
				userRepository.findByEmailAndPassword(lr.getEmail(), lr.getPassword());
		
		if(entity == null) {
			return "Invalid Credentials..";
		}
		if(entity.getActiveSw().equals("Active")) {
			return "Login Successfull";
		}else {
			return "Account Not Activated ..";
		}
		
	}
	// this method is used to generated the random password 
	private String generateRandomPassword() {
		String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		String alphaNumeric = upperAlphabet+lowerAlphabet+ numbers;
		
		StringBuilder sb = new StringBuilder();
	
		int length = 8;
		for(int i=0; i<length; i++) {
			int index = this.random.nextInt(alphaNumeric.length());
			char randomChar = alphaNumeric.charAt(index);
			sb.append(randomChar);
			
		}
		return sb.toString();
	}
	// this method is used to reading data form the filename and prepare the body
	private String readEmailBody(String fullname, String password, String filename)	{
		String mailBody = null;
		String url = "";
		try (
				
				FileReader fr = new FileReader(filename);
				BufferedReader br = new BufferedReader(fr);
			)
		
		{
			
			
			StringBuilder buffer = new StringBuilder();
			String line = br.readLine();
			while(line != null) {
				buffer.append(line);
				line = br.readLine();
			}
			
			mailBody = buffer.toString();
			mailBody= mailBody.replace("{FULLNAME}", fullname);
			mailBody= mailBody.replace("{TEMP-PWD}", password);
			mailBody= mailBody.replace("{URL}", url);
			mailBody= mailBody.replace("{PASSWORD}", password);
			
		}catch(Exception e) {
			logger.error("Exception occured ", e);
		}
		
		return mailBody;
	}

	
	
	
}
