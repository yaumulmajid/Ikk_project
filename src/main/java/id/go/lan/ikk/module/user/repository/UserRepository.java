package id.go.lan.ikk.module.user.repository;

import id.go.lan.ikk.module.user.entity.CoordinatorTypeEntity;
import id.go.lan.ikk.module.user.entity.RoleEntity;
import id.go.lan.ikk.module.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByCreatedBy(Long id);
    List<UserEntity> findByRoleIn(List<RoleEntity> role);
    UserEntity findUserEntityByUsername(String nip);
    List<UserEntity> findByCoordinatorTypeEntity(CoordinatorTypeEntity coordinatorTypeEntity);
    boolean existsUserEntityByCoordinatorTypeEntity(CoordinatorTypeEntity coordinatorTypeEntity);
}
