package project.purelabel.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.purelabel.user.domain.UserRepository;
import project.purelabel.user.domain.entity.User;

public interface UserJpaRepository extends JpaRepository<User,Long>, UserRepository {

}