package utm.valeria.votelectronic.service;

import utm.valeria.votelectronic.exception.UserNotFoundException;
import utm.valeria.votelectronic.model.User;

public interface UserService {
    
    User getUserByFingerId(int fingerId) throws UserNotFoundException;
}
