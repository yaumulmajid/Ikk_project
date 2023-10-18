package id.go.lan.ikk.module.user.service;

import id.go.lan.ikk.module.user.model.CoordinatorTypeDto;
import id.go.lan.ikk.module.user.presenter.model.AddUpdateCoordinateTypeRequest;

import java.util.List;

public interface CoordinatorTypeService {
    List<CoordinatorTypeDto> getAllCoordinatorTypes();
    CoordinatorTypeDto getCoordinatorTypeById(Long coordinatorTypeId);
    void addCoordinatorType(AddUpdateCoordinateTypeRequest request);
    void updateCoordinatorType(Long coordinatorTypeId, AddUpdateCoordinateTypeRequest request);
    void deleteCoordinatorType(Long coordinatorTypeId);
}
