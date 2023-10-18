package id.go.lan.ikk.module.policy.repository;

import id.go.lan.ikk.module.policy.entity.FormulasiKebijakanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormulasiKebijakanRepository extends JpaRepository<FormulasiKebijakanEntity, Long> {
}
