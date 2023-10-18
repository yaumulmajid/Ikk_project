package id.go.lan.ikk.module.user.service.implementation;

import id.go.lan.ikk.exception.AddUserToListFailedException;
import id.go.lan.ikk.exception.DeleteUserFromListFailedException;
import id.go.lan.ikk.exception.RegisterFailedException;
import id.go.lan.ikk.exception.ResourceNotFoundException;
import id.go.lan.ikk.module.user.entity.*;
import id.go.lan.ikk.module.user.model.CoordinatorTypeDto;
import id.go.lan.ikk.module.user.model.GetUserDetailDto;
import id.go.lan.ikk.module.user.presenter.model.AddUpdateUserRequest;
import id.go.lan.ikk.module.user.presenter.model.KoordinatorUtamaAddUpdateUserRequest;
import id.go.lan.ikk.module.user.repository.*;
import id.go.lan.ikk.module.user.service.KoordinatorUtamaService;
import id.go.lan.ikk.module.user.service.UserMasterService;
import id.go.lan.ikk.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KoordinatorUtamaServiceImpl implements KoordinatorUtamaService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private CoordinatorTypeRepository coordinatorTypeRepository;

    @Autowired
    private KoordinatorUtamaKoordinatorInstansiRepository koordinatorUtamaKoordinatorInstansiRepository;

    @Autowired
    private KoordinatorInstansiAgencyRepository koordinatorInstansiAgencyRepository;

    @Autowired
    private KoordinatorInstansiAdminInstansiRepository koordinatorInstansiAdminInstansiRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMasterService userMasterService;

    @Override
    public List<GetUserDetailDto> getAllKoordinatorInstansi() {
        RoleEntity roleEntity = roleRepository.findRoleEntityByName("role_koordinator_instansi");

        List<RoleEntity> roleEntityList = new ArrayList<>();
        roleEntityList.add(roleEntity);
        List<UserEntity> userEntityList = userRepository.findByRoleIn(roleEntityList);

        List<GetUserDetailDto> getUserDetailDtoList = new ArrayList<>();
        for (UserEntity userEntity : userEntityList) {
            KoordinatorUtamaKoordinatorInstansiEntity koordinatorUtamaKoordinatorInstansiEntity = koordinatorUtamaKoordinatorInstansiRepository
                    .findByKoordinatorInstansiId(userEntity.getId());
            if (koordinatorUtamaKoordinatorInstansiEntity == null) {
                GetUserDetailDto getUserDetailDto = new GetUserDetailDto();
                getUserDetailDto.setId(userEntity.getId());
                getUserDetailDto.setNik(userEntity.getNik());
                getUserDetailDto.setNama(userEntity.getName());
                getUserDetailDto.setJabatan(userEntity.getPosition());
                getUserDetailDto.setNamaInstansi(userEntity.getAgency().getName());
                getUserDetailDto.setJenisKoordinator((userEntity.getCoordinatorTypeEntity() == null) ? null : new CoordinatorTypeDto(userEntity.getCoordinatorTypeEntity().getId(), userEntity.getCoordinatorTypeEntity().getName()));
                getUserDetailDto.setUnitKerja(userEntity.getWorkUnit());
                getUserDetailDto.setEmail(userEntity.getEmail());
                getUserDetailDto.setTelpon(userEntity.getPhone());
                getUserDetailDto.setNip(userEntity.getUsername());
                getUserDetailDto.setRole(userEntity.getRole().get(0).getName());
                getUserDetailDto.setStatus(userEntity.getStatus());
                if(userEntity.getStatus().equals("aktif")){
                    getUserDetailDtoList.add(getUserDetailDto);
                }
            }
        }

        return getUserDetailDtoList;
    }

    @Override
    public List<GetUserDetailDto> getKoordinatorUtamaKoordinatorInstansi() {
        Long koordinatorUtamaId = userService.getSignedInUserId();

        List<KoordinatorUtamaKoordinatorInstansiEntity> koordinatorUtamaKoordinatorInstansiEntityList =
                koordinatorUtamaKoordinatorInstansiRepository.findByKoordinatorUtamaId(koordinatorUtamaId);

        List<UserEntity> koordinatorInstansiEntityList = new ArrayList<>();
        for (KoordinatorUtamaKoordinatorInstansiEntity koordinatorUtamaKoordinatorEntity : koordinatorUtamaKoordinatorInstansiEntityList) {
            UserEntity koordinatorInstansiEntity = userRepository.findById(koordinatorUtamaKoordinatorEntity.getKoordinatorInstansiId())
                    .orElseThrow(() -> new ResourceNotFoundException("Koordinator Instansi tidak ditemukan"));
                koordinatorInstansiEntityList.add(koordinatorInstansiEntity);
        }

        List<GetUserDetailDto> getUserDetailDtoList = new ArrayList<>();
        for (UserEntity userEntity : koordinatorInstansiEntityList) {
            GetUserDetailDto getUserDetailDto = new GetUserDetailDto();
                getUserDetailDto.setId(userEntity.getId());
                getUserDetailDto.setNik(userEntity.getNik());
                getUserDetailDto.setNama(userEntity.getName());
                getUserDetailDto.setJabatan(userEntity.getPosition());
                getUserDetailDto.setNamaInstansi(userEntity.getAgency().getName());
                getUserDetailDto.setJenisKoordinator((userEntity.getCoordinatorTypeEntity() == null) ? null : new CoordinatorTypeDto(userEntity.getCoordinatorTypeEntity().getId(), userEntity.getCoordinatorTypeEntity().getName()));
                getUserDetailDto.setUnitKerja(userEntity.getWorkUnit());
                getUserDetailDto.setEmail(userEntity.getEmail());
                getUserDetailDto.setTelpon(userEntity.getPhone());
                getUserDetailDto.setNip(userEntity.getUsername());
                getUserDetailDto.setRole(userEntity.getRole().get(0).getName());
                getUserDetailDto.setStatus(userEntity.getStatus());
                getUserDetailDtoList.add(getUserDetailDto);
        }

        return getUserDetailDtoList;
    }

    @Override
    public void addKoordinatorInstansiToListById(Long userId) {
        Long koordinatorUtamaId = userService.getSignedInUserId();

        UserEntity koordinatorInstansiEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Koordinator Instansi tidak ditemukan"));

        KoordinatorUtamaKoordinatorInstansiEntity koordinatorUtamaKoordinatorInstansiEntity =
                koordinatorUtamaKoordinatorInstansiRepository.findByKoordinatorUtamaIdAndKoordinatorInstansiId(
                        koordinatorUtamaId,
                        koordinatorInstansiEntity.getId());

        if (koordinatorUtamaKoordinatorInstansiEntity != null) {
            throw new AddUserToListFailedException("Koordinator Instansi telah terdaftar sebelumnya");
        } else {
            KoordinatorUtamaKoordinatorInstansiEntity newKoordinatorUtamaKoordinatorInstansiEntity = new KoordinatorUtamaKoordinatorInstansiEntity();
            newKoordinatorUtamaKoordinatorInstansiEntity.setKoordinatorUtamaId(koordinatorUtamaId);
            newKoordinatorUtamaKoordinatorInstansiEntity.setKoordinatorInstansiId(koordinatorInstansiEntity.getId());
            newKoordinatorUtamaKoordinatorInstansiEntity.setCreatedBy(userService.getSignedInUserId());
            koordinatorUtamaKoordinatorInstansiRepository.save(newKoordinatorUtamaKoordinatorInstansiEntity);
        }
    }

    @Override
    public void deleteKooordinatorInstansiFromListById(Long userId) {
        Long koordinatorUtamaId = userService.getSignedInUserId();

        UserEntity koordinatorInstansiEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Koordinator Instansi tidak ditemukan"));

        KoordinatorUtamaKoordinatorInstansiEntity koordinatorUtamaKoordinatorInstansiEntity =
                koordinatorUtamaKoordinatorInstansiRepository.findByKoordinatorUtamaIdAndKoordinatorInstansiId(
                        koordinatorUtamaId,
                        koordinatorInstansiEntity.getId());

        if (koordinatorUtamaKoordinatorInstansiEntity == null) {
            throw new DeleteUserFromListFailedException("Koordinator Instansi tidak terdaftar");
        } else {
            koordinatorUtamaKoordinatorInstansiRepository.delete(koordinatorUtamaKoordinatorInstansiEntity);
        }
    }

    @Override
    public GetUserDetailDto getAdminInstansi(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Akun tidak ada"));
        GetUserDetailDto userDetailDto = new GetUserDetailDto();

        userDetailDto.setId(user.getId());
        userDetailDto.setEmail(user.getEmail());
        userDetailDto.setNip(user.getUsername());
        userDetailDto.setNama(user.getName());
        userDetailDto.setRole(user.getRole().get(0).getName());
        userDetailDto.setJabatan(user.getPosition());
        userDetailDto.setUnitKerja(user.getWorkUnit());
        userDetailDto.setNik(user.getNik());
        userDetailDto.setNamaInstansi(user.getAgency().getName());
        userDetailDto.setJenisKoordinator((user.getCoordinatorTypeEntity() == null) ? null : new CoordinatorTypeDto(user.getCoordinatorTypeEntity().getId(), user.getCoordinatorTypeEntity().getName()));
        userDetailDto.setTelpon(user.getPhone());
        userDetailDto.setStatus(user.getStatus());

        return userDetailDto;
    }

    @Override
    public List<GetUserDetailDto> getAllAdminInstansiByKoordinatorInstansiId(Long koordinatorInstansiId) {
        List<KoordinatorInstansiAdminInstansiEntity> koordinatorInstansiAdminInstansiEntityList = koordinatorInstansiAdminInstansiRepository
                .findByKoordinatorInstansiId(koordinatorInstansiId);

        List<GetUserDetailDto> getUserDetailDtoList = new ArrayList<>();
        for (KoordinatorInstansiAdminInstansiEntity koordinatorInstansiAdminInstansiEntity : koordinatorInstansiAdminInstansiEntityList) {
            UserEntity adminEntity = userMasterService.findById(koordinatorInstansiAdminInstansiEntity.getAdminInstansiId());

            GetUserDetailDto userDetailDto = new GetUserDetailDto();
            userDetailDto.setId(adminEntity.getId());
            userDetailDto.setEmail(adminEntity.getEmail());
            userDetailDto.setNip(adminEntity.getUsername());
            userDetailDto.setNama(adminEntity.getName());
            userDetailDto.setRole(adminEntity.getRole().get(0).getName());
            userDetailDto.setJabatan(adminEntity.getPosition());
            userDetailDto.setUnitKerja(adminEntity.getWorkUnit());
            userDetailDto.setNik(adminEntity.getNik());
            userDetailDto.setNamaInstansi(adminEntity.getAgency().getName());
            userDetailDto.setJenisKoordinator((adminEntity.getCoordinatorTypeEntity() == null) ? null : new CoordinatorTypeDto(adminEntity.getCoordinatorTypeEntity().getId(), adminEntity.getCoordinatorTypeEntity().getName()));
            userDetailDto.setTelpon(adminEntity.getPhone());
            userDetailDto.setStatus(adminEntity.getStatus());

            getUserDetailDtoList.add(userDetailDto);
        }

        return getUserDetailDtoList;
    }

    @Override
    public void addAdminInstansi(KoordinatorUtamaAddUpdateUserRequest request) {
        UserEntity parent = userMasterService.findById(request.getParentId());

        if (userRepository.findUserEntityByUsername(request.getNip()) == null) {
            AgencyEntity agencyEntity = agencyRepository.findById(request.getIdInstansi())
                    .orElseThrow(() -> new ResourceNotFoundException("Instansi tidak ditemukan"));

            CoordinatorTypeEntity coordinatorTypeEntity = coordinatorTypeRepository.findById(request.getJenisKoordinator())
                    .orElseThrow(() -> new ResourceNotFoundException("Wilayah koordinasi tidak ditemukan"));

            Long koordinatorInstansiId = parent.getId();
            Long agencyId = agencyEntity.getId();

            if (koordinatorInstansiAgencyRepository.findByAgencyId(agencyId) == null) {
                UserEntity user = new UserEntity();
                user.setUsername(request.getNip());
                user.setNik(request.getNik());
                user.setName(request.getNama());
                user.setPosition(request.getJabatan());
                user.setAgency(agencyEntity);
                user.setCoordinatorTypeEntity(coordinatorTypeEntity);
//                user.setCoordinatorType(request.getJenisKoordinator());
                user.setWorkUnit(request.getUnitKerja());
                user.setEmail(request.getEmail());
                user.setPhone(request.getTelpon());

                RoleEntity role = roleRepository.findRoleEntityByName(request.getRole());
                List<RoleEntity> roles =new ArrayList<>();
                roles.add(role);
                user.setRole(roles);

                user.setStatus(request.getStatus());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setCreatedBy(userService.getSignedInUserId());

                UserEntity savedUserEntity = userRepository.save(user);

                KoordinatorInstansiAgencyEntity koordinatorInstansiAgencyEntity = new KoordinatorInstansiAgencyEntity();
                koordinatorInstansiAgencyEntity.setKoordinatorInstansiId(koordinatorInstansiId);
                koordinatorInstansiAgencyEntity.setAgencyId(agencyId);
                koordinatorInstansiAgencyEntity.setCreatedBy(userService.getSignedInUserId());
                koordinatorInstansiAgencyRepository.save(koordinatorInstansiAgencyEntity);

                KoordinatorInstansiAdminInstansiEntity koordinatorInstansiAdminInstansiEntity = new KoordinatorInstansiAdminInstansiEntity();
                koordinatorInstansiAdminInstansiEntity.setKoordinatorInstansiId(koordinatorInstansiId);
                koordinatorInstansiAdminInstansiEntity.setAdminInstansiId(savedUserEntity.getId());
                koordinatorInstansiAdminInstansiEntity.setCreatedBy(userService.getSignedInUserId());
                koordinatorInstansiAdminInstansiRepository.save(koordinatorInstansiAdminInstansiEntity);
            } else {
                throw new RegisterFailedException("Instansi telah terdaftar sebelumnya");
            }

        } else {
            throw new RegisterFailedException("Akun telah terdaftar sebelumnya");
        }
    }

    @Override
    public void updateAdminInstansi(Long userId, AddUpdateUserRequest request) {
        userService.updateAdminInstansi(request, userId);
    }

    @Override
    public void deleteAdminInstansi(Long userId) {
        userService.deleteAdminInstansi(userId);
    }
}
