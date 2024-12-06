
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

    @Override
    public String getUserName(String username) {
        User user = getUserByUsername(username);
        return user != null ? user.getUsername() : "";
    }

    @Override
    public String getUserAddress(String username) {
        User user = getUserByUsername(username);
        return user != null ? user.getAddress() : "";
    }

    @Override
    public String getUserPhone(String username) {
        User user = getUserByUsername(username);
        return user != null ? user.getPhoneNumber() : "";
    }

    @Override
    public String getUserRole(String username) {
        User user = getUserByUsername(username);
        return user != null ? user.getRole() : "";
    }

	@Override
	public int getUserID(String username) {
		User user = getUserByUsername(username);
		return user.getId();
	}
}

