package in.satish.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.satish.entity.UserRegister;

public interface UserRepository extends JpaRepository<UserRegister, Integer>{

	public UserRegister findByEmailAndPassword(String email, String password);
	public UserRegister findByEmail(String email);
}
