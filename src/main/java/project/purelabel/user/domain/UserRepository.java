package project.purelabel.user.domain;

import project.purelabel.user.domain.entity.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Boolean existsById(String id);
    Optional<User> findById(String id);
    Optional<User> findByPk(Long id);
}