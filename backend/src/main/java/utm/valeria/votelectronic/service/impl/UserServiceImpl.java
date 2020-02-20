package utm.valeria.votelectronic.service.impl;

import org.springframework.stereotype.Service;
import utm.valeria.votelectronic.exception.UserNotFoundException;
import utm.valeria.votelectronic.model.User;
import utm.valeria.votelectronic.repository.UserRepository;
import utm.valeria.votelectronic.service.UserService;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    
    private UserRepository userRepository;
    
    @Inject
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public User getUserByFingerId(int fingerId) throws UserNotFoundException {
        Optional<User> userOptional = this.userRepository.findUserByFingerId(fingerId);
        
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UserNotFoundException("User with fingerprint " + fingerId + " was not found!");
        }
    }
}
