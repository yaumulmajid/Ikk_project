package id.go.lan.ikk.module.policy.repository;

import id.go.lan.ikk.module.policy.entity.AgendaSettingKUScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaSettingKUScoreRepository extends JpaRepository<AgendaSettingKUScoreEntity, Long> {
}
