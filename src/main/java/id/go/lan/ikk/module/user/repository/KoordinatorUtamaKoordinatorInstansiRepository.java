package id.go.lan.ikk.module.user.repository;

import id.go.lan.ikk.module.user.entity.KoordinatorUtamaKoordinatorInstansiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KoordinatorUtamaKoordinatorInstansiRepository extends JpaRepository<KoordinatorUtamaKoordinatorInstansiEntity, Long> {
    List<KoordinatorUtamaKoordinatorInstansiEntity> findByKoordinatorUtamaId(Long koordinatorUtamaId);
    KoordinatorUtamaKoordinatorInstansiEntity findByKoordinatorUtamaIdAndKoordinatorInstansiId(Long koordinatorUtamaId, Long koordinatorInstansiId);
    KoordinatorUtamaKoordinatorInstansiEntity findByKoordinatorInstansiId(Long koordinatorInstansiId);
}