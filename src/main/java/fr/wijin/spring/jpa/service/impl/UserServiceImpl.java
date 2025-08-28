package fr.wijin.spring.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.wijin.spring.jpa.model.User;
import fr.wijin.spring.jpa.repository.UserRepository;
import fr.wijin.spring.jpa.service.UserService;
import jakarta.transaction.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService{

    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
		Optional<User> user = userRepository.findById(id);
		return user.isEmpty() ? null : user.get();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) throws Exception {
       	logger.debug("attempting to update customer {}...", user.getId());
		User userExistant = this.getUserById(user.getId());
		userExistant.setUsername(user.getUsername());
		userExistant.setPassword(user.getPassword());
		userExistant.setMail(user.getMail());
		userExistant.setGrants(user.getGrants());
		return userRepository.save(userExistant);
    }

    @Override
    public void deleteUser(Integer id) {
        User user = userRepository.getReferenceById(id);
        if (null != user) {
            userRepository.delete(user);
        } else {
            logger.error("impossible de supprimer car il n'existe pas d'user avec ce numéro", id);
        }

    }

    
    
}
