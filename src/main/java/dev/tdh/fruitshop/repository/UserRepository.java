package dev.tdh.fruitshop.repository;

import dev.tdh.fruitshop.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
    UserEntity findByUserName(String userName); // phai dung voi ten bien trong entity la userName
    UserEntity findByEmail(String email);
    UserEntity findByPhoneNumber(String phoneNumber);
    void deleteByIdIn(List<Long> ids);
    UserEntity findById(Long id);
    void deleteById(Long id);
}