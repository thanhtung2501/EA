package miu.edu.apigateway.service;

import miu.edu.apigateway.domain.User;
import miu.edu.apigateway.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserByNameAndPassword(String name, String password) throws Exception {
        User user = userRepository.findByUsernameAndPassword(name, password);

        if (user == null) {
            throw new Exception("Invalid username and password");
        }

        return user;
    }
}