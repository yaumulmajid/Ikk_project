package id.go.lan.ikk.module.user.service.implementation;

import id.go.lan.ikk.exception.CoordinatorTypeServiceFailedException;
import id.go.lan.ikk.exception.ResourceNotFoundException;
import id.go.lan.ikk.module.user.entity.CoordinatorTypeEntity;
import id.go.lan.ikk.module.user.entity.UserEntity;
import id.go.lan.ikk.module.user.model.CoordinatorTypeDto;
import id.go.lan.ikk.module.user.presenter.model.AddUpdateCoordinateTypeRequest;
import id.go.lan.ikk.module.user.repository.CoordinatorTypeRepository;
import id.go.lan.ikk.module.user.repository.UserRepository;
import id.go.lan.ikk.module.user.service.CoordinatorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoordinatorTypeServiceImpl implements CoordinatorTypeService {

    @Autowired
    private CoordinatorTypeRepository coordinatorTypeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CoordinatorTypeDto> getAllCoordinatorTypes() {
        List<CoordinatorTypeEntity> coordinatorTypeEntityList = coordinatorTypeRepository.findAll();

        List<CoordinatorTypeDto> coordinatorTypeDtoList = new ArrayList<>();
        for (CoordinatorTypeEntity coordinatorTypeEntity : coordinatorTypeEntityList) {
            CoordinatorTypeDto coordinatorTypeDto = new CoordinatorTypeDto();
            coordinatorTypeDto.setId(coordinatorTypeEntity.getId());
            coordinatorTypeDto.setName(coordinatorTypeEntity.getName());
            coordinatorTypeDtoList.add(coordinatorTypeDto);
        }

        return coordinatorTypeDtoList;
    }

    @Override
    public CoordinatorTypeDto getCoordinatorTypeById(Long coordinatorTypeId) {
        CoordinatorTypeEntity coordinatorTypeEntity = coordinatorTypeRepository.findById(coordinatorTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Wilayah koordinasi tidak ditemukan"));

        CoordinatorTypeDto coordinatorTypeDto = new CoordinatorTypeDto();
        coordinatorTypeDto.setId(coordinatorTypeEntity.getId());
        coordinatorTypeDto.setName(coordinatorTypeEntity.getName());

        return coordinatorTypeDto;
    }

    @Override
    public void addCoordinatorType(AddUpdateCoordinateTypeRequest request) {
        if (coordinatorTypeRepository.existsCoordinatorTypeEntityByName(request.getNama())) {
            throw new CoordinatorTypeServiceFailedException("Nama wilayah koordinasi sudah digunakan");
        } else {
            CoordinatorTypeEntity coordinatorTypeEntity = new CoordinatorTypeEntity();
            coordinatorTypeEntity.setName(request.getNama());
            coordinatorTypeRepository.save(coordinatorTypeEntity);
        }
    }

    @Override
    public void updateCoordinatorType(Long coordinatorTypeId, AddUpdateCoordinateTypeRequest request) {
        CoordinatorTypeEntity coordinatorTypeEntity = coordinatorTypeRepository.findById(coordinatorTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Wilayah koordinasi tidak ditemukan"));

        if (coordinatorTypeRepository.existsCoordinatorTypeEntityByName(request.getNama())) {
            throw new CoordinatorTypeServiceFailedException("Nama wilayah koordinasi sudah digunakan");
        } else {
            coordinatorTypeEntity.setName(request.getNama());
            coordinatorTypeRepository.save(coordinatorTypeEntity);
        }
    }

    @Override
    public void deleteCoordinatorType(Long coordinatorTypeId) {
        CoordinatorTypeEntity coordinatorTypeEntity = coordinatorTypeRepository.findById(coordinatorTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Wilayah koordinasi tidak ditemukan"));

        List<UserEntity> userEntityList = userRepository.findByCoordinatorTypeEntity(coordinatorTypeEntity);

        if (userEntityList.size() > 0) {
            throw new CoordinatorTypeServiceFailedException("Wilayah koordinasi gagal dihapus karena sedang digunakan oleh satu atau lebih user");
        } else {
            coordinatorTypeRepository.delete(coordinatorTypeEntity);
        }
    }
}
