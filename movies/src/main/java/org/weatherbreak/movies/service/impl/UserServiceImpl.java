package org.weatherbreak.movies.service.impl;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.weatherbreak.movies.entity.User;
import org.weatherbreak.movies.entity.impl.UserImpl;
import org.weatherbreak.movies.repository.UserRepository;
import org.weatherbreak.movies.service.UserService;
import org.weatherbreak.movies.service.exception.ErrorCode;
import org.weatherbreak.movies.service.exception.InvalidFieldException;
import org.weatherbreak.movies.service.exception.MoviesException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final int MAX_NAME_LENGTH = 45;
    private static final int MAX_PASSWORD_LENGTH = 10;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User addUser(User user) {
        if (StringUtils.isEmpty(user.getName()) || user.getName().length() > MAX_NAME_LENGTH) {
            throw new InvalidFieldException("name is invalid");
        }

        if (StringUtils.isEmpty(user.getPassword()) || user.getPassword().length() > MAX_PASSWORD_LENGTH) {
            throw new InvalidFieldException("password is invalid");
        }

        UserImpl impl = (UserImpl) user;
        impl.setPassword(md5base64(user.getPassword()));
        long id =  userRepository.addUser(user);
        return getUser(id);
    }

    @Override
    @Transactional
    public User getUser(long userId) {
        return userRepository.getUser(userId);
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    @Transactional
    public List<User> getUsersByName(String name) {
        List<User> users = new ArrayList<>();
        if (StringUtils.isEmpty(name)) {
            throw new MoviesException(ErrorCode.MISSING_DATA, "no search parameter provided");
        }
        else {
            users = userRepository.getUsersByName(name);
        }
        return users;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userRepository.update(user);
    }

    @Override
    @Transactional
    public boolean isValidPassword(long userId, String password) {
        User user = getUser(userId);
        if(user == null || password == null) {
            return false;
        }
        return user.getPassword() != null && user.getPassword().equals(md5base64(password));
    }

    private String md5base64(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(password.getBytes("UTF-8"));
            return Base64.encodeBase64String(digest);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error("failed to md5",e);
        }

        throw new IllegalArgumentException("Server fail");
    }
}
