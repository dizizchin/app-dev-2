package com.rosal.serviceimpl;

import com.rosal.entity.UserData;
import com.rosal.model.User;
import com.rosal.repostory.UserDataRepository;
import com.rosal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDataRepository userDataRepository;
    
    @Override
    public List<User> getUsers() {
        List<UserData> usersData = new ArrayList<>();
        List<User> users = new ArrayList<>();
        userDataRepository.findAll().forEach(usersData::add);
        Iterator<UserData> it = usersData.iterator();
        while(it.hasNext()) {
            UserData userData = it.next();
            User user = new User();

            //Credentials
            user.setId(userData.getId());
            user.setUserId(userData.getUserId());
            user.setUserEmail(userData.getUserEmail());
            user.setUserName(userData.getUserName());
            user.setUserPassword(userData.getUserPassword());

            //Personal Information
            user.setFirstName(userData.getFirstName());
            user.setLastName(userData.getLastName());
            user.setUserGender(userData.getUserGender());
            user.setDateOfBirth(userData.getDateOfBirth());
            user.setPhoneNumber(userData.getPhoneNumber());
            user.setUserAddress(userData.getUserAddress());

            users.add(user);
        }
        return users;
    }

    @Override
    public User create(User user) {
        log.info(" add:Input " + user.toString());
        UserData userData = new UserData();


        //Credentials
        userData.setUserId(user.getUserId());
        userData.setUserEmail(user.getUserEmail());
        userData.setUserName(user.getUserName());
        userData.setUserPassword(user.getUserPassword());

        //Personal Information
        userData.setFirstName(user.getFirstName());
        userData.setLastName(user.getLastName());
        userData.setUserGender(user.getUserGender());
        userData.setDateOfBirth(user.getDateOfBirth());
        userData.setPhoneNumber(user.getPhoneNumber());
        userData.setUserAddress(user.getUserAddress());



        userData = userDataRepository.save(userData);
        log.info(" add:Input " + userData.toString());
        User newUser = new User();

        //Credentials
        newUser.setId(userData.getId());
        newUser.setUserId(userData.getId()); //so that cusid = id
        newUser.setUserEmail(userData.getUserEmail());
        newUser.setUserName(userData.getUserName());
        newUser.setUserPassword(userData.getUserPassword());

        //Personal Information
        newUser.setFirstName(userData.getFirstName());
        newUser.setLastName(userData.getLastName());
        newUser.setUserGender(userData.getUserGender());
        newUser.setDateOfBirth(userData.getDateOfBirth());
        newUser.setPhoneNumber(userData.getPhoneNumber());
        newUser.setUserAddress(userData.getUserAddress());


        return newUser;
    }
    
    @Override
    public User update(int id, User user) {
        Optional<UserData> optional = userDataRepository.findById(id);

        if (optional.isPresent()) {
            UserData originalUserData = optional.get();

            // Updating fields with new values
            
            //Credentials
            originalUserData.setUserId(user.getUserId());
            originalUserData.setUserEmail(user.getUserEmail());
            originalUserData.setUserName(user.getUserName());
            originalUserData.setUserPassword(user.getUserPassword());
                
            //Personal Information
            originalUserData.setFirstName(user.getFirstName());
            originalUserData.setLastName(user.getLastName());
            originalUserData.setUserGender(user.getUserGender());
            originalUserData.setDateOfBirth(user.getDateOfBirth());
            originalUserData.setPhoneNumber(user.getPhoneNumber());
            originalUserData.setUserAddress(user.getUserAddress());

            // Save the updated entity
            originalUserData = userDataRepository.save(originalUserData);

            // Convert the updated UserData back to User, if necessary
            User updatedUser = new User();
            updatedUser.setId(originalUserData.getId());
            
            //Credentials
            updatedUser.setUserId(originalUserData.getUserId());
            updatedUser.setUserEmail(originalUserData.getUserEmail());
            updatedUser.setUserName(originalUserData.getUserName());
            updatedUser.setUserPassword(originalUserData.getUserPassword());
            
            //Personal Information
            updatedUser.setFirstName(originalUserData.getFirstName());
            updatedUser.setLastName(originalUserData.getLastName());
            updatedUser.setUserGender(originalUserData.getUserGender());
            updatedUser.setDateOfBirth(originalUserData.getDateOfBirth());
            updatedUser.setPhoneNumber(originalUserData.getPhoneNumber());
            updatedUser.setUserAddress(originalUserData.getUserAddress());
            

            return updatedUser;

        } else {
            log.error("User record with id: " + id + " does not exist");
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
    }




    @Override
    public void delete(Integer id) {
        User user = null;
        log.info(" Input >> " + Integer.toString(id));
        Optional<UserData> optional = userDataRepository.findById(id);
        if (optional.isPresent()) {
            UserData userDatum = optional.get();
            userDataRepository.delete(optional.get());
            log.info(" Successfully deleted User record with id: " + Integer.toString(id));
        } else {
            log.error(" Unable to locate user with id:" + Integer.toString(id));
        }
    }

    @Override
    public User findById(int id) {
        Optional<UserData> optionalUserData = userDataRepository.findById(id);
        if (optionalUserData.isPresent()) {
            UserData userData = optionalUserData.get();
            User user = new User();
            // Map fields from UserData to User...
            return user;
        } else {
            log.error("User with id {} not found", id);
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
    }

    
    
}

