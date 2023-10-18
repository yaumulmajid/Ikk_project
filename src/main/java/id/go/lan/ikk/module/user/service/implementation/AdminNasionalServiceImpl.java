package id.go.lan.ikk.module.user.service.implementation;

import id.go.lan.ikk.exception.RegisterFailedException;
import id.go.lan.ikk.exception.ResourceNotFoundException;
import id.go.lan.ikk.module.user.entity.*;
import id.go.lan.ikk.module.user.model.CoordinatorTypeDto;
import id.go.lan.ikk.module.user.model.GetUserDetailDto;
import id.go.lan.ikk.module.user.presenter.model.AddUpdateUserRequest;
import id.go.lan.ikk.module.user.presenter.model.AdminNasionalAddUpdateUserRequest;
import id.go.lan.ikk.module.user.repository.*;
import id.go.lan.ikk.module.user.service.AdminNasionalService;
import id.go.lan.ikk.module.user.service.UserMasterService;
import id.go.lan.ikk.module.user.service.UserService;
import id.go.lan.ikk.utility.ModelMapperUtility;
import io.undertow.util.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminNasionalServiceImpl implements AdminNasionalService {

    @Autowired
    private UserMasterService userMasterService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private CoordinatorTypeRepository coordinatorTypeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private KoordinatorInstansiAgencyRepository koordinatorInstansiAgencyRepository;

    @Autowired
    private KoordinatorInstansiAdminInstansiRepository koordinatorInstansiAdminInstansiRepository;

    @Autowired
    private KoordinatorUtamaKoordinatorInstansiRepository koordinatorUtamaKoordinatorInstansiRepository;

    @Autowired
    private AdminInstansiEnumeratorRepository adminInstansiEnumeratorRepository;

    @Autowired
    private ModelMapperUtility modelMapperUtility;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<GetUserDetailDto> getAllUser(String role) {
        return userService.getAll(role);
    }

    @Override
    public GetUserDetailDto getUserById(Long id) {
        GetUserDetailDto userDetailDto = new GetUserDetailDto();
        UserEntity user = userMasterService.findById(id);

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
    public void addKoordinatorInstansi(AdminNasionalAddUpdateUserRequest request) {
        UserEntity parent = userMasterService.findById(request.getParentId());

        if (userRepository.findUserEntityByUsername(request.getNip()) == null) {
            AgencyEntity agencyEntity = agencyRepository.findById(request.getIdInstansi())
                    .orElseThrow(() -> new ResourceNotFoundException("Instansi tidak ditemukan"));

            CoordinatorTypeEntity coordinatorTypeEntity = coordinatorTypeRepository.findById(request.getJenisKoordinator())
                    .orElseThrow(() -> new ResourceNotFoundException("Wilayah koordinasi tidak ditemukan"));

            UserEntity user = new UserEntity();
            user.setUsername(request.getNip());
            user.setNik(request.getNik());
            user.setName(request.getNama());
            user.setPosition(request.getJabatan());
            user.setAgency(agencyEntity);
            user.setCoordinatorTypeEntity(coordinatorTypeEntity);
//            user.setCoordinatorType(request.getJenisKoordinator());
            user.setWorkUnit(request.getUnitKerja());
            user.setEmail(request.getEmail());
            user.setPhone(request.getTelpon());

            RoleEntity role = roleRepository.findRoleEntityByName(request.getRole());
            List<RoleEntity> roles = new ArrayList<>();
            roles.add(role);
            user.setRole(roles);

            user.setStatus(request.getStatus());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setCreatedBy(userService.getSignedInUserId());

            UserEntity savedUserEntity = userRepository.save(user);

            KoordinatorUtamaKoordinatorInstansiEntity koordinatorUtamaKoordinatorInstansiEntity = new KoordinatorUtamaKoordinatorInstansiEntity();
            koordinatorUtamaKoordinatorInstansiEntity.setKoordinatorUtamaId(parent.getId());
            koordinatorUtamaKoordinatorInstansiEntity.setKoordinatorInstansiId(savedUserEntity.getId());

            koordinatorUtamaKoordinatorInstansiRepository.save(koordinatorUtamaKoordinatorInstansiEntity);
        } else {
            throw new RegisterFailedException("Akun telah terdaftar sebelumnya");
        }
    }

    @Override
    public void addAdminInstansi(AdminNasionalAddUpdateUserRequest request) {
        UserEntity parent = userMasterService.findById(request.getParentId());

        if (userRepository.findUserEntityByUsername(request.getNip()) == null) {
            AgencyEntity agencyEntity = agencyRepository.findById(request.getIdInstansi())
                    .orElseThrow(() -> new ResourceNotFoundException("Instansi tidak ditemukan"));

            Long koordinatorInstansiId = parent.getId();
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
    public void addEnumerator(AdminNasionalAddUpdateUserRequest request) throws BadRequestException {
        UserEntity parent = userMasterService.findById(request.getParentId());
        AddUpdateUserRequest userRequest = modelMapperUtility.initialize().map(request, AddUpdateUserRequest.class);

        userService.add(userRequest);
        UserEntity enumerator = userRepository.findUserEntityByUsername(userRequest.getNip());
        AdminInstansiEnumeratorEntity adminInstansiEnumerator = new AdminInstansiEnumeratorEntity();
        adminInstansiEnumerator.setAdminInstansiId(parent.getId());
        adminInstansiEnumerator.setEnumeratorId(enumerator.getId());
        adminInstansiEnumerator.setCreatedBy(userService.getSignedInUserId());
        adminInstansiEnumeratorRepository.save(adminInstansiEnumerator);
    }

    @Override
    public void update(Long userId, AddUpdateUserRequest request) {
        UserEntity user = userMasterService.findById(userId);

        if (user.getRole().get(0).getName().equals("role_admin_instansi")) {
            userService.updateAdminInstansi(request, userId);
        } else {
            userService.update(request, userId);
        }

    }

    @Override
    public void delete(Long userId) {
        UserEntity user = userMasterService.findById(userId);

        if (user.getRole().get(0).getName().equals("role_admin_instansi")) {
            userService.deleteAdminInstansi(userId);
        } else {
            userService.delete(userId);
        }

    }
}
