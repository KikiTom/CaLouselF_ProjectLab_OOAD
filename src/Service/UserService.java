
package Service;

import Model.User;

public interface UserService {
    boolean registerUser(User user);
    boolean loginUser(String username, String password);
    User getUserByUsername(String username);
    
    // New methods for retrieving user details
    int getUserID(String username);
    String getUserName(String username);
    String getUserAddress(String username);
    String getUserPhone(String username);
    String getUserRole(String username);
}

