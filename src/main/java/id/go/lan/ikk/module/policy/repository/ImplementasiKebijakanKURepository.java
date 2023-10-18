package id.go.lan.ikk.module.policy.repository;

import id.go.lan.ikk.module.policy.entity.ImplementasiKebijakanKUEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImplementasiKebijakanKURepository extends JpaRepository<ImplementasiKebijakanKUEntity, Long> {
}
