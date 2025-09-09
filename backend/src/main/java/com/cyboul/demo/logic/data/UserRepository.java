package com.cyboul.demo.logic.data;

import com.cyboul.demo.model.user.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
