package id.go.lan.ikk.module.user.service.implementation;

import id.go.lan.ikk.exception.CoordinatorTypeServiceFailedException;
import id.go.lan.ikk.exception.RegisterFailedException;
import id.go.lan.ikk.exception.ResourceNotFoundException;
import id.go.lan.ikk.exception.UpdateFailedException;
import id.go.lan.ikk.module.user.entity.*;
import id.go.lan.ikk.module.user.model.CoordinatorTypeDto;
import id.go.lan.ikk.module.user.model.GetUserDetailDto;
import id.go.lan.ikk.module.user.presenter.model.AddUpdateUserRequest;
import id.go.lan.ikk.module.user.presenter.model.UpdatePasswordUserRequest;
import id.go.lan.ikk.module.user.presenter.model.UpdatePhoneNumberRequest;
import id.go.lan.ikk.module.user.repository.*;
import id.go.lan.ikk.module.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

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
    private AdminInstansiEnumeratorRepository adminInstansiEnumeratorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<GetUserDetailDto> getAll(String role) {
        List<UserEntity> userEntityList = new ArrayList<>();

        switch (userRepository.findById(getSignedInUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan"))
                .getRole().get(0).getName()) {
            case "role_admin_nasional":
                List<RoleEntity> roles = new ArrayList<>();
                roles.add(roleRepository.findRoleEntityByName(role));
                userEntityList = userRepository.findByRoleIn(roles);

                break;
            case "role_koordinator_utama":
                List<KoordinatorUtamaKoordinatorInstansiEntity> kuRelationList = koordinatorUtamaKoordinatorInstansiRepository
                        .findByKoordinatorUtamaId(getSignedInUserId());

                if (!kuRelationList.isEmpty()) {
                    for (KoordinatorUtamaKoordinatorInstansiEntity data : kuRelationList) {
                        userEntityList.add(userRepository.findById(data.getKoordinatorInstansiId())
                                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan")));
                    }
                }

                break;
            case "role_koordinator_instansi":
                List<KoordinatorInstansiAdminInstansiEntity> kiRelationList = koordinatorInstansiAdminInstansiRepository
                        .findByKoordinatorInstansiId(getSignedInUserId());

                if (!kiRelationList.isEmpty()) {
                    for (KoordinatorInstansiAdminInstansiEntity data : kiRelationList) {
                        userEntityList.add(userRepository.findById(data.getAdminInstansiId())
                                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan")));
                    }
                }

                break;
            case "role_admin_instansi":
                List<AdminInstansiEnumeratorEntity> aiRelationList = adminInstansiEnumeratorRepository
                        .findByAdminInstansiId(getSignedInUserId());

                if (!aiRelationList.isEmpty()) {
                    for (AdminInstansiEnumeratorEntity data : aiRelationList) {
                        userEntityList.add(userRepository.findById(data.getEnumeratorId())
                                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan")));
                    }
                }

                break;
        }

        List<GetUserDetailDto> getUserDetailDtoList = new ArrayList<>();

        for (UserEntity user : userEntityList) {
            GetUserDetailDto getUserDetailDto = new GetUserDetailDto();

            if (role.equals(user.getRole().get(0).getName())) {
                getUserDetailDto.setId(user.getId());
                getUserDetailDto.setNik(user.getNik());
                getUserDetailDto.setNama(user.getName());
                getUserDetailDto.setJabatan(user.getPosition());
                getUserDetailDto.setNamaInstansi(user.getAgency().getName());
                getUserDetailDto.setJenisKoordinator(
                        (user.getCoordinatorTypeEntity() == null) ? null : new CoordinatorTypeDto(
                                user.getCoordinatorTypeEntity().getId(),
                                user.getCoordinatorTypeEntity().getName()));
                getUserDetailDto.setUnitKerja(user.getWorkUnit());
                getUserDetailDto.setEmail(user.getEmail());
                getUserDetailDto.setTelpon(user.getPhone());
                getUserDetailDto.setNip(user.getUsername());
                getUserDetailDto.setRole(user.getRole().get(0).getName());
                getUserDetailDto.setStatus(user.getStatus());
                getUserDetailDtoList.add(getUserDetailDto);
            }
        }
        return getUserDetailDtoList;
    }

    @Override
    public GetUserDetailDto getById(Long id) {
        UserEntity user = userRepository.findById(id)
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
        userDetailDto.setJenisKoordinator(
                (user.getCoordinatorTypeEntity() == null) ? null : new CoordinatorTypeDto(
                        user.getCoordinatorTypeEntity().getId(),
                        user.getCoordinatorTypeEntity().getName()));
        userDetailDto.setTelpon(user.getPhone());
        userDetailDto.setStatus(user.getStatus());

        return userDetailDto;
    }

    @Override
    public void add(AddUpdateUserRequest request) {
        if (userRepository.findUserEntityByUsername(request.getNip()) == null) {
            AgencyEntity agencyEntity = agencyRepository.findById(request.getIdInstansi())
                    .orElseThrow(() -> new ResourceNotFoundException("Instansi tidak ditemukan"));

            CoordinatorTypeEntity coordinatorTypeEntity = null;
            if (request.getJenisKoordinator() != null) {
                coordinatorTypeEntity = coordinatorTypeRepository.findById(request.getJenisKoordinator())
                        .orElseThrow(() -> new ResourceNotFoundException("Wilayah koordinasi tidak ditemukan"));
            }

            if (coordinatorTypeEntity != null) {
                if (userRepository.existsUserEntityByCoordinatorTypeEntity(coordinatorTypeEntity)) {
                    throw new CoordinatorTypeServiceFailedException("Wilayah koordinasi telah digunakan");
                }
            }

            UserEntity user = new UserEntity();
            user.setUsername(request.getNip());
            user.setNik(request.getNik());
            user.setName(request.getNama());
            user.setPosition(request.getJabatan());
            user.setAgency(agencyEntity);
            user.setCoordinatorTypeEntity(coordinatorTypeEntity);
            user.setWorkUnit(request.getUnitKerja());
            user.setEmail(request.getEmail());
            user.setPhone(request.getTelpon());

            RoleEntity role = roleRepository.findRoleEntityByName(request.getRole());
            List<RoleEntity> roles = new ArrayList<>();
            roles.add(role);
            user.setRole(roles);

            user.setStatus(request.getStatus());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setCreatedBy(getSignedInUserId());
            userRepository.save(user);
        } else {
            throw new RegisterFailedException("Akun sudah ada");
        }
    }

    @Override
    public void delete(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Akun tidak ditemukan"));

        switch (user.getRole().get(0).getName()) {
            case "role_koordinator_utama":
                userRepository.delete(user);

                break;
            case "role_koordinator_instansi":
                koordinatorUtamaKoordinatorInstansiRepository
                        .delete(koordinatorUtamaKoordinatorInstansiRepository
                                .findByKoordinatorInstansiId(user.getId()));
                userRepository.delete(user);

                break;
            case "role_enumerator":
                adminInstansiEnumeratorRepository
                        .delete(adminInstansiEnumeratorRepository
                                .findByEnumeratorId(user.getId()));
                userRepository.delete(user);

                break;
        }
    }

    @Override
    public void update(AddUpdateUserRequest request, Long userId) {
        try {
            log.info("tes Update");
            UserEntity userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new UpdateFailedException("Akun tidak ditemukan"));

            if (!request.getNip().equals(userEntity.getUsername())) {
                if (userRepository.findUserEntityByUsername(request.getNip()) != null) {
                    throw new UpdateFailedException(String.format("Akun dengan NIP %s sudah terdaftar sebelumnya", request.getNip()));
                }
            }

            AgencyEntity agencyEntity = agencyRepository.findById(request.getIdInstansi())
                    .orElseThrow(() -> new UpdateFailedException("Instansi tidak ditemukan"));

            CoordinatorTypeEntity coordinatorTypeEntity = null;
            if (request.getJenisKoordinator() != null) {
                coordinatorTypeEntity = coordinatorTypeRepository.findById(request.getJenisKoordinator())
                        .orElseThrow(() -> new ResourceNotFoundException("Wilayah koordinasi tidak ditemukan"));
            }

            if (coordinatorTypeEntity != null) {
                if(coordinatorTypeEntity != userEntity.getCoordinatorTypeEntity()){
                    if (userRepository.existsUserEntityByCoordinatorTypeEntity(coordinatorTypeEntity)) {
                        throw new CoordinatorTypeServiceFailedException("Wilayah koordinasi telah digunakan");
                    }
                }
            }

            userEntity.setUsername(request.getNip());
            userEntity.setNik(request.getNik());
            userEntity.setName(request.getNama());
            userEntity.setPosition(request.getJabatan());
            userEntity.setAgency(agencyEntity);
            userEntity.setCoordinatorTypeEntity(coordinatorTypeEntity);
            userEntity.setWorkUnit(request.getUnitKerja());
            userEntity.setEmail(request.getEmail());
            userEntity.setPhone(request.getTelpon());
            userEntity.setStatus(request.getStatus());

            if (!request.getPassword().isEmpty()) {
                userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
            }

            userEntity.setModifiedBy(getSignedInUserId());

            userRepository.save(userEntity);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new UpdateFailedException(exception.getMessage());
        }
    }

    @Override
    public void addAdminNasional(AddUpdateUserRequest request) {
        if (userRepository.findUserEntityByUsername(request.getNip()) == null) {
            AgencyEntity agencyEntity = agencyRepository.findById(request.getIdInstansi())
                    .orElseThrow(() -> new ResourceNotFoundException("Instansi tidak ditemukan"));

            UserEntity user = new UserEntity();
            user.setUsername(request.getNip());
            user.setNik(request.getNik());
            user.setName(request.getNama());
            user.setPosition(request.getJabatan());
            user.setAgency(agencyEntity);
            user.setCoordinatorTypeEntity(null);
            user.setWorkUnit(request.getUnitKerja());
            user.setEmail(request.getEmail());
            user.setPhone(request.getTelpon());

            RoleEntity role = roleRepository.findRoleEntityByName(request.getRole());
            List<RoleEntity> roles = new ArrayList<>();
            roles.add(role);
            user.setRole(roles);

            user.setStatus(request.getStatus());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setCreatedBy(0L);
            userRepository.save(user);
        } else {
            throw new RegisterFailedException("Akun sudah ada");
        }
    }

    @Override
    public void addKoordinatorInstansi(AddUpdateUserRequest request) {
        if (userRepository.findUserEntityByUsername(request.getNip()) == null) {
            AgencyEntity agencyEntity = agencyRepository.findById(request.getIdInstansi())
                    .orElseThrow(() -> new ResourceNotFoundException("Instansi tidak ditemukan"));

            CoordinatorTypeEntity coordinatorTypeEntity = null;
            if (request.getJenisKoordinator() != null) {
                coordinatorTypeEntity = coordinatorTypeRepository.findById(request.getJenisKoordinator())
                        .orElseThrow(() -> new ResourceNotFoundException("Wilayah koordinasi tidak ditemukan"));
            }

            if (coordinatorTypeEntity != null) {
                if (userRepository.existsUserEntityByCoordinatorTypeEntity(coordinatorTypeEntity)) {
                    throw new CoordinatorTypeServiceFailedException("Wilayah koordinasi telah digunakan");
                }
            }

            UserEntity user = new UserEntity();
            user.setUsername(request.getNip());
            user.setNik(request.getNik());
            user.setName(request.getNama());
            user.setPosition(request.getJabatan());
            user.setAgency(agencyEntity);
            user.setCoordinatorTypeEntity(coordinatorTypeEntity);
            user.setWorkUnit(request.getUnitKerja());
            user.setEmail(request.getEmail());
            user.setPhone(request.getTelpon());

            RoleEntity role = roleRepository.findRoleEntityByName(request.getRole());
            List<RoleEntity> roles = new ArrayList<>();
            roles.add(role);
            user.setRole(roles);

            user.setStatus(request.getStatus());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setCreatedBy(getSignedInUserId());

            UserEntity savedUserEntity = userRepository.save(user);

            KoordinatorUtamaKoordinatorInstansiEntity koordinatorUtamaKoordinatorInstansiEntity = new KoordinatorUtamaKoordinatorInstansiEntity();
            koordinatorUtamaKoordinatorInstansiEntity.setKoordinatorUtamaId(getSignedInUserId());
            koordinatorUtamaKoordinatorInstansiEntity.setKoordinatorInstansiId(savedUserEntity.getId());

            koordinatorUtamaKoordinatorInstansiRepository.save(koordinatorUtamaKoordinatorInstansiEntity);
        } else {
            throw new RegisterFailedException("Akun telah terdaftar sebelumnya");
        }
    }

    @Override
    public void addAdminInstansi(AddUpdateUserRequest request) {
        if (userRepository.findUserEntityByUsername(request.getNip()) == null) {
            AgencyEntity agencyEntity = agencyRepository.findById(request.getIdInstansi())
                    .orElseThrow(() -> new ResourceNotFoundException("Instansi tidak ditemukan"));

            Long koordinatorInstansiId = getSignedInUserId();
            Long agencyId = agencyEntity.getId();

            if (koordinatorInstansiAgencyRepository.findByAgencyId(agencyId) == null) {
                UserEntity user = new UserEntity();
                user.setUsername(request.getNip());
                user.setNik(request.getNik());
                user.setName(request.getNama());
                user.setPosition(request.getJabatan());
                user.setAgency(agencyEntity);
                user.setCoordinatorTypeEntity(null);
                user.setWorkUnit(request.getUnitKerja());
                user.setEmail(request.getEmail());
                user.setPhone(request.getTelpon());

                RoleEntity role = roleRepository.findRoleEntityByName(request.getRole());
                List<RoleEntity> roles =new ArrayList<>();
                roles.add(role);
                user.setRole(roles);

                user.setStatus(request.getStatus());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setCreatedBy(getSignedInUserId());

                UserEntity savedUserEntity = userRepository.save(user);

                KoordinatorInstansiAgencyEntity koordinatorInstansiAgencyEntity = new KoordinatorInstansiAgencyEntity();
                koordinatorInstansiAgencyEntity.setKoordinatorInstansiId(koordinatorInstansiId);
                koordinatorInstansiAgencyEntity.setAgencyId(agencyId);
                koordinatorInstansiAgencyEntity.setCreatedBy(getSignedInUserId());
                koordinatorInstansiAgencyRepository.save(koordinatorInstansiAgencyEntity);

                KoordinatorInstansiAdminInstansiEntity koordinatorInstansiAdminInstansiEntity = new KoordinatorInstansiAdminInstansiEntity();
                koordinatorInstansiAdminInstansiEntity.setKoordinatorInstansiId(koordinatorInstansiId);
                koordinatorInstansiAdminInstansiEntity.setAdminInstansiId(savedUserEntity.getId());
                koordinatorInstansiAdminInstansiEntity.setCreatedBy(getSignedInUserId());
                koordinatorInstansiAdminInstansiRepository.save(koordinatorInstansiAdminInstansiEntity);
            } else {
                throw new RegisterFailedException("Instansi telah terdaftar sebelumnya");
            }

        } else {
            throw new RegisterFailedException("Akun telah terdaftar sebelumnya");
        }
    }

    @Override
    public void deleteAdminInstansi(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Akun tidak ditemukan"));
        AgencyEntity agencyEntity = user.getAgency();

        Long agencyId = agencyEntity.getId();

        KoordinatorInstansiAgencyEntity koordinatorInstansiAgencyEntity = koordinatorInstansiAgencyRepository
                .findByAgencyId(agencyId);

        KoordinatorInstansiAdminInstansiEntity koordinatorInstansiAdminInstansiEntity = koordinatorInstansiAdminInstansiRepository
                .findByKoordinatorInstansiIdAndAdminInstansiId(koordinatorInstansiAgencyEntity.getKoordinatorInstansiId(), user.getId());

        koordinatorInstansiAgencyRepository.delete(koordinatorInstansiAgencyEntity);
        koordinatorInstansiAdminInstansiRepository.delete(koordinatorInstansiAdminInstansiEntity);

        userRepository.delete(user);
    }

    @Override
    public void updateAdminInstansi(AddUpdateUserRequest request, Long id) {
        try {
            UserEntity userEntity = userRepository.findById(id)
                    .orElseThrow(() -> new UpdateFailedException("Akun tidak ditemukan"));

            if (!request.getNip().equals(userEntity.getUsername())) {
                if (userRepository.findUserEntityByUsername(request.getNip()) != null) {
                    throw new UpdateFailedException(String.format("Akun dengan NIP %s sudah terdaftar sebelumnya", request.getNip()));
                }
            }

            AgencyEntity agencyEntity = agencyRepository.findById(request.getIdInstansi())
                    .orElseThrow(() -> new UpdateFailedException("Instansi tidak ditemukan"));

            Long currentAgencyId = userEntity.getAgency().getId();

            KoordinatorInstansiAgencyEntity koordinatorInstansiAgencyEntity = koordinatorInstansiAgencyRepository
                    .findByAgencyId(currentAgencyId);

            koordinatorInstansiAgencyEntity.setAgencyId(agencyEntity.getId());
            koordinatorInstansiAgencyEntity.setModifiedBy(getSignedInUserId());
            koordinatorInstansiAgencyRepository.save(koordinatorInstansiAgencyEntity);

            userEntity.setUsername(request.getNip());
            userEntity.setNik(request.getNik());
            userEntity.setName(request.getNama());
            userEntity.setPosition(request.getJabatan());
            userEntity.setAgency(agencyEntity);
            userEntity.setCoordinatorTypeEntity(null);
            userEntity.setWorkUnit(request.getUnitKerja());
            userEntity.setEmail(request.getEmail());
            userEntity.setPhone(request.getTelpon());
            userEntity.setStatus(request.getStatus());

            if (!request.getPassword().isEmpty()) {
                userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
            }

            userEntity.setModifiedBy(getSignedInUserId());

            userRepository.save(userEntity);
        } catch (Exception exception) {
            log.error(exception.toString());
            throw new UpdateFailedException(exception.getMessage());
        }
    }

    @Override
    public void addEnumerator(AddUpdateUserRequest request) {
        add(request);
        UserEntity enumerator = userRepository.findUserEntityByUsername(request.getNip());
        AdminInstansiEnumeratorEntity adminInstansiEnumerator = new AdminInstansiEnumeratorEntity();
        adminInstansiEnumerator.setAdminInstansiId(getSignedInUserId());
        adminInstansiEnumerator.setEnumeratorId(enumerator.getId());
        adminInstansiEnumerator.setCreatedBy(getSignedInUserId());
        adminInstansiEnumeratorRepository.save(adminInstansiEnumerator);
    }

    @Override
    public void updatePassword(UpdatePasswordUserRequest request) {
        UserEntity user = userRepository.findById(getSignedInUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan"));

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    @Override
    public String getAdminNasionalPhoneNumber() {
        UserEntity user = userRepository.findById(getSignedInUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan"));

        return user.getPhone();
    }

    @Override
    public void updateAdminNasionalPhoneNumber(UpdatePhoneNumberRequest request) {
        UserEntity user = userRepository.findById(getSignedInUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan"));

        user.setPhone(request.getPhone());
        userRepository.save(user);
    }

    @Override
    public List<GetUserDetailDto> kiGetEnumeratorList(Long adminInstansiId) {
        List<UserEntity> userEntityList = new ArrayList<>();
        List<AdminInstansiEnumeratorEntity> aiRelationList = adminInstansiEnumeratorRepository
                .findByAdminInstansiId(adminInstansiId);

        if (!aiRelationList.isEmpty()) {
            for (AdminInstansiEnumeratorEntity data : aiRelationList) {
                userEntityList.add(userRepository.findById(data.getEnumeratorId())
                        .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan")));
            }
        }

        List<GetUserDetailDto> getUserDetailDtoList = new ArrayList<>();

        for (UserEntity user : userEntityList) {
            GetUserDetailDto getUserDetailDto = new GetUserDetailDto();
            getUserDetailDto.setId(user.getId());
            getUserDetailDto.setNik(user.getNik());
            getUserDetailDto.setNama(user.getName());
            getUserDetailDto.setJabatan(user.getPosition());
            getUserDetailDto.setNamaInstansi(user.getAgency().getName());
            getUserDetailDto.setJenisKoordinator(
                    (user.getCoordinatorTypeEntity() == null) ? null : new CoordinatorTypeDto(
                            user.getCoordinatorTypeEntity().getId(),
                            user.getCoordinatorTypeEntity().getName()));
            getUserDetailDto.setUnitKerja(user.getWorkUnit());
            getUserDetailDto.setEmail(user.getEmail());
            getUserDetailDto.setTelpon(user.getPhone());
            getUserDetailDto.setNip(user.getUsername());
            getUserDetailDto.setRole(user.getRole().get(0).getName());
            getUserDetailDto.setStatus(user.getStatus());

            getUserDetailDtoList.add(getUserDetailDto);
        }

        return getUserDetailDtoList;
    }

    @Override
    public String getSignedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getPrincipal().toString();
        } else {
            return "system";
        }
    }

    @Override
    public Long getSignedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findUserEntityByUsername(authentication.getPrincipal().toString());
        return userEntity.getId();
    }

    @Override
    public String getAgencyBySignedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findUserEntityByUsername(authentication.getPrincipal().toString());
        return userEntity.getAgency().getName();
    }

    @Override
    public Long getAgencyIdBySignedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findUserEntityByUsername(authentication.getPrincipal().toString());
        return userEntity.getAgency().getId();
    }

    @Override
    public List<UserEntity> getAllAdminsBySignedInKoordinatorInstansi() {
        return userRepository.findByCreatedBy(getSignedInUserId());
    }
}
