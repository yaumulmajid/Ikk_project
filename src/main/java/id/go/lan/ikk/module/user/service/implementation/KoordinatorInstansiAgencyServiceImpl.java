package id.go.lan.ikk.module.user.service.implementation;

import id.go.lan.ikk.module.user.entity.KoordinatorInstansiAgencyEntity;
import id.go.lan.ikk.module.user.repository.KoordinatorInstansiAgencyRepository;
import id.go.lan.ikk.module.user.service.KoordinatorInstansiAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KoordinatorInstansiAgencyServiceImpl implements KoordinatorInstansiAgencyService {

    @Autowired
    private KoordinatorInstansiAgencyRepository koordinatorInstansiAgencyRepository;

    @Override
    public List<KoordinatorInstansiAgencyEntity> getAllByKoordinatorInstansiId(Long id) {
        return koordinatorInstansiAgencyRepository.findByKoordinatorInstansiId(id);
    }
}
