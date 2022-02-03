package com.springwebexample.SpringWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springwebexample.SpringWeb.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query("SELECT u from User u where u.email =?1")
	User findUserByEmail(String email);

}
