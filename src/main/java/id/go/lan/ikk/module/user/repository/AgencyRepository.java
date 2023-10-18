package id.go.lan.ikk.module.user.repository;

import id.go.lan.ikk.entity.AgencyCategoryEnum;
import id.go.lan.ikk.module.user.entity.AgencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgencyRepository extends JpaRepository<AgencyEntity, Long> {
    AgencyEntity findByName(String name);
    List<AgencyEntity> findByCategory(AgencyCategoryEnum agencyCategoryEnum);
}
