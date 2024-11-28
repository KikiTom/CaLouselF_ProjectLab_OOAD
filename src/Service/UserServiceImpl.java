package Service;

import Model.User;
import Repository.UserRepository;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean registerUser(User user) {
        return userRepository.saveUser(user);
    }

    @Override
    public boolean loginUser(String username, String password) {
        return userRepository.validateUser(username, password);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
}
