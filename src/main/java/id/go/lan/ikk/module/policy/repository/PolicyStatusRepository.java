package id.go.lan.ikk.module.policy.repository;

import id.go.lan.ikk.module.policy.entity.PolicyStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyStatusRepository extends JpaRepository<PolicyStatusEntity, Long> {
}
