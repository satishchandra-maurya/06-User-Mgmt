package in.satish.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="USER_REGISTER")
public class UserRegister {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USER_ID")
	private Integer id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="MOBILE")
	private Long mobile;
	
	@Column(name="SSN")
	private String ssn;
	
	@Column(name="DOB")
	private Date dob;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="ACTIVE_SW")
	private String activeSw;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;
	
	@CreationTimestamp
	@Column(name="CREATED_DATE", updatable = false)
	private LocalDate createdDate;
	
	@UpdateTimestamp
	@Column(name="UPDATED_DATE", insertable = false)
	private LocalDate updatedDate;
	
	
}
