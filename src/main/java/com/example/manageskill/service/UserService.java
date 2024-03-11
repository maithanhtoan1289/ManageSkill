package com.example.manageskill.service;

import com.example.manageskill.model.User;
import com.example.manageskill.repository.TeammemberRepository;
import com.example.manageskill.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeammemberRepository teammemberRepository;

    public List<String> findUsersWithoutTeam() {
        List<String> allUsers = userRepository.findAllUsernames();
        List<String> usersWithTeam = teammemberRepository.findUsersWithTeam();
        allUsers.removeAll(usersWithTeam);
        return allUsers;
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Phương thức để lấy thông tin người dùng dựa trên username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
