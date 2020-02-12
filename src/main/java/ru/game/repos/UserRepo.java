package ru.game.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.game.domain.ApplicationUser;

public interface UserRepo extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
    ApplicationUser findById(long id);
}
