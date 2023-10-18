package id.go.lan.ikk.module.helpdesk.service.implementation;

import id.go.lan.ikk.module.helpdesk.model.HelpdeskDto;
import id.go.lan.ikk.module.helpdesk.service.HelpdeskService;
import id.go.lan.ikk.module.user.entity.RoleEntity;
import id.go.lan.ikk.module.user.entity.UserEntity;
import id.go.lan.ikk.module.user.repository.RoleRepository;
import id.go.lan.ikk.module.user.repository.UserRepository;
import id.go.lan.ikk.module.user.service.UserMasterService;
import id.go.lan.ikk.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HelpdeskServiceImpl implements HelpdeskService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMasterService userMasterService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<HelpdeskDto> getHelpdeskData() {
        UserEntity user = userMasterService.findById(userService.getSignedInUserId());
        List<HelpdeskDto> helpdeskDtoList = new ArrayList<>();

        switch (user.getRole().get(0).getName()) {
            case "role_admin_nasional":
                List<RoleEntity> roleKu = new ArrayList<>();
                roleKu.add(roleRepository.findRoleEntityByName("role_koordinator_utama"));

                List<RoleEntity> roleKi = new ArrayList<>();
                roleKi.add(roleRepository.findRoleEntityByName("role_koordinator_instansi"));

                List<UserEntity> userEntityListKu = userRepository.findByRoleIn(roleKu);
                List<UserEntity> userEntityListKi = userRepository.findByRoleIn(roleKi);

                for (UserEntity userEntity : userEntityListKu) {
                    HelpdeskDto helpdeskDto = new HelpdeskDto();
                    helpdeskDto.setNama(userEntity.getName());
                    helpdeskDto.setNumber(userEntity.getPhone());
                    helpdeskDto.setRole("Koordinator Utama");

                    helpdeskDtoList.add(helpdeskDto);
                }

                for (UserEntity userEntity : userEntityListKi) {
                    HelpdeskDto helpdeskDto = new HelpdeskDto();
                    helpdeskDto.setNama(userEntity.getName());
                    helpdeskDto.setNumber(userEntity.getPhone());
                    helpdeskDto.setRole(getFormatedRoleName(userEntity.getRole().get(0).getName()));

                    helpdeskDtoList.add(helpdeskDto);
                }

                break;
            case "role_koordinator_utama":
            case "role_koordinator_instansi":
            case "role_admin_instansi":
                List<UserEntity> userEntityList = getChildUserList(userService.getSignedInUserId());
                UserEntity userEntityParent = getParentUser(user.getCreatedBy());
                HelpdeskDto helpdeskDtoParent = new HelpdeskDto();

                helpdeskDtoParent.setNama(userEntityParent.getName());
                helpdeskDtoParent.setNumber(userEntityParent.getPhone());
                helpdeskDtoParent.setRole(getFormatedRoleName(userEntityParent.getRole().get(0).getName()));

                helpdeskDtoList.add(helpdeskDtoParent);

                for (UserEntity userEntity : userEntityList) {
                    HelpdeskDto helpdeskDto = new HelpdeskDto();
                    helpdeskDto.setNama(userEntity.getName());
                    helpdeskDto.setNumber(userEntity.getPhone());
                    helpdeskDto.setRole(getFormatedRoleName(userEntity.getRole().get(0).getName()));

                    helpdeskDtoList.add(helpdeskDto);
                }

                break;
            case "role_enumerator":
                UserEntity enumeratorParent = userMasterService.findById(user.getCreatedBy());
                HelpdeskDto helpdeskDto = new HelpdeskDto();

                helpdeskDto.setNama(enumeratorParent.getName());
                helpdeskDto.setNumber(enumeratorParent.getPhone());
                helpdeskDto.setRole(getFormatedRoleName(enumeratorParent.getRole().get(0).getName()));

                helpdeskDtoList.add(helpdeskDto);

                break;
        }

        return helpdeskDtoList;
    }

    private List<UserEntity> getChildUserList(Long parentUserId) {
        return userRepository.findByCreatedBy(parentUserId);
    }

    private UserEntity getParentUser(Long childUserId) {
        return userMasterService.findById(childUserId);
    }

    private String getFormatedRoleName(String role) {
        String formatedRole = "";

        switch (role) {
            case "role_koordinator_utama":
                formatedRole = "Koordinator Utama";
                break;
            case "role_koordinator_instansi":
                formatedRole = "Koordinator Instansi";
                break;
            case "role_admin_instansi":
                formatedRole = "Admin Instansi";
                break;
            case "role_enumerator":
                formatedRole = "Enumerator";
                break;
        }

        return formatedRole;
    }
}
