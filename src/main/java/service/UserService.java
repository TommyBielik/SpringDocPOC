package service;

import api.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private List<User> userList = new ArrayList<>();

    public UserService() {
        generateUsers();
    }

    public Optional<User> getUser(Integer id) {
        Optional<User> optionalUser = getUserById(id);
        return optionalUser;
    }

    public void registerUser(Integer id, String password) {
        Optional optional = getUserById(id);
        if(optional.isPresent()) {
            User user = (User) optional.get();
            user.setPassword(password);
        }
    }

    public boolean loginUser(Integer id, String password) {
        Optional optional = getUserById(id);
        if(optional.isPresent()) {
            User user = (User) optional.get();

            if(user.isRegistered()) {
                return user.getPassword().equals(password);
            }
        }
        return false;
    }

    private void generateUsers() {

        String[] names = {"Carlos", "Dante", "Gabrielle", "Lynette", "Robin", "Demetrius", "Cornell"};
        Random random = new Random();

        for(int i = 0; i < names.length; i++) {

            String currentName = names[i];
            String email = currentName.toLowerCase() + "@example.com";
            int age = random.nextInt(18, 100);

            User user = new User(i + 1, currentName, age, email, null);
            userList.add(user);
        }
    }

    private Optional<User> getUserById(Integer id) {
        Optional optional = Optional.empty();
        for(User user : userList) {
            if(id == user.getId()) {
                optional = Optional.of(user);
                return optional;
            }
        }
        System.out.println("User not found.");
        return optional;
    }
}
