package id.go.lan.ikk.module.user.service;

import id.go.lan.ikk.module.user.model.GetUserDetailDto;
import id.go.lan.ikk.module.user.presenter.model.AddUpdateUserRequest;
import id.go.lan.ikk.module.user.presenter.model.KoordinatorUtamaAddUpdateUserRequest;

import java.util.List;

public interface KoordinatorUtamaService {
    List<GetUserDetailDto> getAllKoordinatorInstansi();
    List<GetUserDetailDto> getKoordinatorUtamaKoordinatorInstansi();
    void addKoordinatorInstansiToListById(Long userId);
    void deleteKooordinatorInstansiFromListById(Long userId);

    GetUserDetailDto getAdminInstansi(Long userId);
    List<GetUserDetailDto> getAllAdminInstansiByKoordinatorInstansiId(Long koordinatorInstansiId);
    void addAdminInstansi(KoordinatorUtamaAddUpdateUserRequest request);
    void updateAdminInstansi(Long userId, AddUpdateUserRequest request);
    void deleteAdminInstansi(Long userId);
}
