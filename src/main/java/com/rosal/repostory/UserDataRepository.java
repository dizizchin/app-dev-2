package com.rosal.repostory;

import com.rosal.entity.UserData;
import org.springframework.data.repository.CrudRepository;

public interface UserDataRepository extends CrudRepository<UserData,Integer> {
}
