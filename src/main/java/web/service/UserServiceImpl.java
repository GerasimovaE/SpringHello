package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{


    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {this.userDao = userDao;}

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void removeUser(User user) {
        userDao.removeUser(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

}
