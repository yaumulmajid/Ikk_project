package id.go.lan.ikk.module.user.service;

import id.go.lan.ikk.module.user.entity.UserEntity;
import id.go.lan.ikk.module.user.model.GetUserDetailDto;
import id.go.lan.ikk.module.user.presenter.model.AddUpdateUserRequest;
import id.go.lan.ikk.module.user.presenter.model.UpdatePasswordUserRequest;
import id.go.lan.ikk.module.user.presenter.model.UpdatePhoneNumberRequest;
import io.undertow.util.BadRequestException;

import java.util.List;

public interface UserService {
    List<GetUserDetailDto> getAll(String role);
    GetUserDetailDto getById(Long id);

    void add(AddUpdateUserRequest request) throws BadRequestException;
    void delete(Long id);
    void update(AddUpdateUserRequest request, Long userId);

    void addAdminNasional(AddUpdateUserRequest request);
    void addKoordinatorInstansi(AddUpdateUserRequest request);
    void addAdminInstansi(AddUpdateUserRequest request);
    void deleteAdminInstansi(Long id);
    void updateAdminInstansi(AddUpdateUserRequest request, Long id);
    void addEnumerator(AddUpdateUserRequest request);
    void updatePassword(UpdatePasswordUserRequest request);
    String getAdminNasionalPhoneNumber();
    void updateAdminNasionalPhoneNumber(UpdatePhoneNumberRequest request);
    List<GetUserDetailDto> kiGetEnumeratorList(Long adminInstansiId);

    String getSignedInUser();
    Long getSignedInUserId();
    String getAgencyBySignedInUser();
    Long getAgencyIdBySignedInUser();

    List<UserEntity> getAllAdminsBySignedInKoordinatorInstansi();
}
