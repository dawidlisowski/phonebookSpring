package pl.dawidlisowski.phonebookSpring.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dawidlisowski.phonebookSpring.models.UserSession;
import pl.dawidlisowski.phonebookSpring.models.entities.UserEntity;
import pl.dawidlisowski.phonebookSpring.models.forms.LoginForm;
import pl.dawidlisowski.phonebookSpring.models.forms.RegisterForm;
import pl.dawidlisowski.phonebookSpring.models.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    final UserRepository userRepository;
    final UserSession userSession;
    final PasswordHashingService passwordHashingService;

    @Autowired
    public UserService(UserRepository userRepository, UserSession userSession, PasswordHashingService passwordHashingService) {
        this.userRepository = userRepository;
        this.userSession = userSession;
        this.passwordHashingService = passwordHashingService;
    }

    public boolean checkIfLoginExists(String login) {
        return userRepository.existsByLogin(login);
    }

    public void addUser(RegisterForm registerForm) {
        UserEntity newUser = new UserEntity();
        newUser.setLogin(registerForm.getLogin());
        newUser.setPassword(passwordHashingService.hash(registerForm.getPassword()));

        userRepository.save(newUser);
    }

    public boolean tryLogin(LoginForm loginForm) {
        Optional<UserEntity> userOptional = userRepository.getUserByLogin(loginForm.getLogin());
        if (userOptional.isPresent()) {
            if (passwordHashingService.matches(loginForm.getPassword(), userOptional.get().getPassword())) {
                userSession.setLogin(true);
                userSession.setUserEntity(userOptional.get());
            }
        }
        return userOptional.isPresent();
    }

}
