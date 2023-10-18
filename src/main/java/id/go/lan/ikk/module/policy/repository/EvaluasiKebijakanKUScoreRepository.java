package id.go.lan.ikk.module.policy.repository;

import id.go.lan.ikk.module.policy.entity.EvaluasiKebijakanKUScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluasiKebijakanKUScoreRepository extends JpaRepository<EvaluasiKebijakanKUScoreEntity, Long> {
}
