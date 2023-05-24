package miu.edu.apigateway.service;

import miu.edu.apigateway.domain.User;

import java.util.Map;

public interface JwtGenerator {
    Map<String, String> generateToken(User user);
}
