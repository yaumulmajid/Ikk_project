package id.go.lan.ikk.module.user.repository;

import id.go.lan.ikk.module.user.entity.KoordinatorInstansiAgencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KoordinatorInstansiAgencyRepository extends JpaRepository<KoordinatorInstansiAgencyEntity, Long> {
    List<KoordinatorInstansiAgencyEntity> findByKoordinatorInstansiId(Long koordinatorInstansi);
    KoordinatorInstansiAgencyEntity findByKoordinatorInstansiIdAndAgencyId(Long koordinatorInstansiId, Long agencyId);
    KoordinatorInstansiAgencyEntity findByAgencyId(Long agencyId);
}
