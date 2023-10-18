package id.go.lan.ikk.module.user.repository;

import id.go.lan.ikk.module.user.entity.CoordinatorTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatorTypeRepository extends JpaRepository<CoordinatorTypeEntity, Long> {
    boolean existsCoordinatorTypeEntityByName(String name);
}
