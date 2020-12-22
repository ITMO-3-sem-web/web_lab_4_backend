package ru.maxim.arina.web4.controller;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.maxim.arina.web4.model.User;
import ru.maxim.arina.web4.service.UserService;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin("*")
@RequestMapping("auth/")
@RestController
public class UsersController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    static String currentLogin;

    @GetMapping("users")
    public ResponseEntity<?> add(@RequestParam("login") String login, @RequestParam("password") String password, @RequestParam("type")String type) throws NoSuchAlgorithmException {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setType(type);
        if (user.getType().equals("authorisation")) {
            return new ResponseEntity(doLogin(user).toJSONString(), HttpStatus.OK);
        } else {
            return new ResponseEntity(doRegistration(user).toJSONString(), HttpStatus.OK);
        }
    }

    private JSONObject doLogin(User user) throws NoSuchAlgorithmException {
        List<User> users = userService.findAll();
        JSONObject jo = new JSONObject();
        if (!users.isEmpty()) {
            for (User u : users) {
                if (u.getLogin().equals(user.getLogin())) {
                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    String existingPassword = user.getPassword();
                    String dbPassword = u.getPassword();
                    if (passwordEncoder.matches(existingPassword, dbPassword)) {
                        currentLogin = user.getLogin();
                        jo.put("message", "Добро пожаловать");
                        jo.put("result", "OK");
                        return jo;
                    } else {
                        jo.put("message", "Неверный пароль");
                        jo.put("result", "ERROR");
                        return jo;
                    }
                }
            }
        }
        jo.put("message", "Пользователь с таким именем не найден");
        jo.put("result", "ERROR");
        return jo;
    }

    private JSONObject doRegistration(User user) throws NoSuchAlgorithmException {
        List<User> users = userService.findAll();
        JSONObject jo = new JSONObject();
        if (!users.isEmpty()) {
            for (User us : users) {
                if (us.getLogin().equals(user.getLogin())) {
                    jo.put("message", "Пользователь с таким именем уже существует");
                    jo.put("result", "ERROR");
                    return jo;
                }
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        currentLogin = user.getLogin();
        jo.put("message", "Добро пожаловать");
        jo.put("result", "OK");
        return jo;
    }
}
