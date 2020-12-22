package ru.maxim.arina.web4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maxim.arina.web4.model.User;
import ru.maxim.arina.web4.repository.UserRepository;


import java.util.List;

@Service
public class UserService implements UserDetailsService {
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean addUser(User user) {
        User userFromDb = userRepository.findByLogin(user.getLogin());
        if (userFromDb != null) {
            return false;
        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
//    public PasswordEncoder getPasswordEncoder(){
//        return passwordEncoder;
//    }

    @Transactional
    public List<User> findAll(){
        return userRepository.findAll();
    }

//    @Transactional
//    public User findById(Integer id){
//        return userRepository.findById(id).orElse(null);
//    }

    public User loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(name);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
