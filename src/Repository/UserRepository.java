package Repository;

import Model.User;

public interface UserRepository {
    boolean saveUser(User user);
    boolean validateUser(String username, String password);
    User getUserByUsername(String username);
    User getUserById(int userId);
}
