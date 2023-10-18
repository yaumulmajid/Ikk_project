package id.go.lan.ikk.module.user.service;

import id.go.lan.ikk.module.user.model.GetUserDetailDto;
import id.go.lan.ikk.module.user.presenter.model.AddUpdateUserRequest;
import id.go.lan.ikk.module.user.presenter.model.AdminNasionalAddUpdateUserRequest;
import io.undertow.util.BadRequestException;

import java.util.List;

public interface AdminNasionalService {
    List<GetUserDetailDto> getAllUser(String role);
    GetUserDetailDto getUserById(Long userId);
    void addKoordinatorInstansi(AdminNasionalAddUpdateUserRequest request);
    void addAdminInstansi(AdminNasionalAddUpdateUserRequest request);
    void addEnumerator(AdminNasionalAddUpdateUserRequest request) throws BadRequestException;
    void update(Long userId, AddUpdateUserRequest request);
    void delete(Long userId);
}
