package org.f1soft.springcrud.repository;

import org.f1soft.springcrud.entity.User;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {
    User save(User user);
    User findById(Long id);
    User findByUsername(String username);
}
