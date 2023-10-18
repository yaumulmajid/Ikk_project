package id.go.lan.ikk.module.user.repository;

import id.go.lan.ikk.module.user.entity.KoordinatorInstansiAdminInstansiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KoordinatorInstansiAdminInstansiRepository extends JpaRepository<KoordinatorInstansiAdminInstansiEntity, Long> {
    KoordinatorInstansiAdminInstansiEntity findByKoordinatorInstansiIdAndAdminInstansiId(Long koordinatorInstansiId, Long adminInstansiId);
    List<KoordinatorInstansiAdminInstansiEntity> findByKoordinatorInstansiId(Long koordinatorInstansiId);
}
