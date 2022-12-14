package in.satish.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

	private String name;

	private String email;
	
	private Long mobile;
	
	private String ssn;
	
	private Date dob;
	
	private String gender;
}
