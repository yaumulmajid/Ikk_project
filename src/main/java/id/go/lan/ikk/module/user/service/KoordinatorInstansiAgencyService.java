package id.go.lan.ikk.module.user.service;

import id.go.lan.ikk.module.user.entity.KoordinatorInstansiAgencyEntity;

import java.util.List;

public interface KoordinatorInstansiAgencyService {
    List<KoordinatorInstansiAgencyEntity> getAllByKoordinatorInstansiId(Long id);
}
