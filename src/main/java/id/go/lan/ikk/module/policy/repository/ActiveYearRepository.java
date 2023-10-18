package id.go.lan.ikk.module.policy.repository;

import id.go.lan.ikk.module.policy.entity.ActiveYearEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActiveYearRepository extends JpaRepository<ActiveYearEntity, Long> {
    ActiveYearEntity findFirstByOrderByIdAsc();
}
