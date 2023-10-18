package id.go.lan.ikk.module.policy.repository;

import id.go.lan.ikk.module.policy.entity.AgendaSettingKUEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaSettingKURepository extends JpaRepository<AgendaSettingKUEntity,Long> {
}
