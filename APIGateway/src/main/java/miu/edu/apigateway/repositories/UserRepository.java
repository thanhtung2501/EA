package miu.edu.apigateway.repositories;

import miu.edu.apigateway.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String userName, String password);

    User findByUsername(String userName);
}
