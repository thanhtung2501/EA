package miu.edu.apigateway.service;

import miu.edu.apigateway.domain.User;

public interface UserService {

    void saveUser(User user);

    User getUserByNameAndPassword(String name, String password) throws Exception;
}
