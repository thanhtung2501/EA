package miu.edu.apigateway;

import miu.edu.apigateway.domain.Role;
import miu.edu.apigateway.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication implements CommandLineRunner {
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role role = new Role("customer", "/customers");
//		roleRepository.save(role);
//
//		role = new Role("customer", "/customers/**");
//		roleRepository.save(role);
//
//		role = new Role("admin", "/customers");
//		roleRepository.save(role);
//
//		role = new Role("admin", "/customers/**");
//		roleRepository.save(role);
//
//		role = new Role("admin", "/products");
//		roleRepository.save(role);
//
//		role = new Role("admin", "/products/**");
//		roleRepository.save(role);
//
//		role = new Role("admin", "/carts");
//		roleRepository.save(role);
//
		role = new Role("customer", "/carts");
		roleRepository.save(role);
		role = new Role("customer", "/carts/**");
		roleRepository.save(role);

	}

}
