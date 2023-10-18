package id.go.lan.ikk.module.policy.repository;

import id.go.lan.ikk.module.policy.entity.AgendaSettingKIEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaSettingKIRepository extends JpaRepository<AgendaSettingKIEntity, Long> {
}
