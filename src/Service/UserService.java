package Service;

import Model.User;

public interface UserService {
    boolean registerUser(User user);
    boolean loginUser(String username, String password);
    User getUserByUsername(String username);
}
