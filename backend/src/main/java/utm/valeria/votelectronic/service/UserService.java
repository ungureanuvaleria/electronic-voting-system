package utm.valeria.votelectronic.service;

import utm.valeria.votelectronic.exception.UserNotFoundException;
import utm.valeria.votelectronic.model.User;

import java.util.List;

public interface UserService {
    
    User getUserByFingerId(int fingerId) throws UserNotFoundException;
    void addUser(User user);
    List<User> getAllUsers();
    void deleteUser(User user);
}
