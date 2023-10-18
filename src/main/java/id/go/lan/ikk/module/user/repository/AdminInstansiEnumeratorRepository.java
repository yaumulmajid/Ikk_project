package id.go.lan.ikk.module.user.repository;

import id.go.lan.ikk.module.user.entity.AdminInstansiEnumeratorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminInstansiEnumeratorRepository extends JpaRepository<AdminInstansiEnumeratorEntity, Long> {
    AdminInstansiEnumeratorEntity findByEnumeratorId(Long enumeratorId);
    List<AdminInstansiEnumeratorEntity> findByAdminInstansiId(Long adminInstansiId);
}
