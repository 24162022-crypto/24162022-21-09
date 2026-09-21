// File: src/main/java/vn/iotstar/repository/UserRepository.java
package vn.iotstar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    /** Tìm theo username HOẶC email: WHERE username = ? OR email = ? */
    Optional<User> findByUsernameOrEmail(String username, String email);
}
