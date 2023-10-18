package id.go.lan.ikk.module.user.service.implementation;

import id.go.lan.ikk.exception.ResourceNotFoundException;
import id.go.lan.ikk.module.user.entity.RoleEntity;
import id.go.lan.ikk.module.user.entity.UserEntity;
import id.go.lan.ikk.module.user.repository.RoleRepository;
import id.go.lan.ikk.module.user.repository.UserRepository;
import id.go.lan.ikk.module.user.service.UserMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMasterServiceImpl implements UserMasterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan"));
    }

    @Override
    public List<UserEntity> findByRole(String roleName) {
        List<RoleEntity> roleEntityList = new ArrayList<>();
        roleEntityList.add(roleRepository.findRoleEntityByName(roleName));
        return userRepository.findByRoleIn(roleEntityList);
    }

    @Override
    public List<UserEntity> findByCreatedBy(Long id) {
        return userRepository.findByCreatedBy(id);
    }

    @Override
    public void add(UserEntity userEntity) {
        userRepository.save(userEntity);
    }
}
