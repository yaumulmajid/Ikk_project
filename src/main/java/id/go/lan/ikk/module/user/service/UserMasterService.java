package id.go.lan.ikk.module.user.service;

import id.go.lan.ikk.module.user.entity.UserEntity;

import java.util.List;

public interface UserMasterService {
    UserEntity findById(Long id);
    List<UserEntity> findByRole(String roleName);
    List<UserEntity> findByCreatedBy(Long id);
    void add(UserEntity userEntity);
}
