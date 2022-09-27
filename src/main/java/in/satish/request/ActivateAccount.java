package in.satish.request;

import lombok.Data;

@Data
public class ActivateAccount {

	private String email;
	private String tempPass;
	private String newPass;
	private String confirmPass;
}
