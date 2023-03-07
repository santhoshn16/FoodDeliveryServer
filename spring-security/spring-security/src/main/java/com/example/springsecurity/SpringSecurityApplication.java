package com.example.springsecurity;

import com.example.springsecurity.models.Role;
import com.example.springsecurity.models.User;
import com.example.springsecurity.service.FileHandlingService;
import com.example.springsecurity.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.ArrayList;

@SpringBootApplication
public class SpringSecurityApplication implements  CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Resource
	FileHandlingService fileHandlingService;
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
//	@Bean
//	CommandLineRunner run(UserService userService){
//		return args -> {
//			userService.saveRole(new Role(null,"ROLE_USER"));
//			userService.saveRole(new Role(null,"ROLE_ADMIN"));
//			userService.saveRole(new Role(null,"ROLE_MANAGER"));
//			userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));
//
//			userService.saveUser(new User(null,"santhosh","santhu","Struggle",1231231231L,new ArrayList<>(), new ArrayList<>()));
//			userService.saveUser(new User(null,"manikanta","mani","Struggle",3213213213L,new ArrayList<>(), new ArrayList<>()));
//			userService.saveUser(new User(null,"saisri","sai","Struggle",1212121212L,new ArrayList<>(), new ArrayList<>()));
//			userService.saveUser(new User(null,"Ramadevi","rama","s",9403318966L,new ArrayList<>(), new ArrayList<>()));
//
//			userService.addRoleToUser("santhu","ROLE_USER");
//			userService.addRoleToUser("mani","ROLE_MANAGER");
//			userService.addRoleToUser("sai","ROLE_ADMIN");
//			userService.addRoleToUser("rama","ROLE_USER");
//			userService.addRoleToUser("rama","ROLE_ADMIN");
//			userService.addRoleToUser("rama","ROLE_SUPER_ADMIN");
//		};
//	}

	@Override
	public void run(String... arg) throws Exception {
		fileHandlingService.deleteAll();
		fileHandlingService.init();
	}

}
