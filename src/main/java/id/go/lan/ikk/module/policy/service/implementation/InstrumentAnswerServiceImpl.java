package id.go.lan.ikk.module.policy.service.implementation;

import id.go.lan.ikk.exception.ResourceNotFoundException;
import id.go.lan.ikk.module.policy.entity.InstrumentAnswerEntity;
import id.go.lan.ikk.module.policy.repository.InstrumentAnswerRepository;
import id.go.lan.ikk.module.policy.service.InstrumentAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstrumentAnswerServiceImpl implements InstrumentAnswerService {

    @Autowired
    private InstrumentAnswerRepository instrumentAnswerRepository;

    @Override
    public String getInstrumentAnswerById(String id) {
        InstrumentAnswerEntity instrumentAnswerEntity = instrumentAnswerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jawaban tidak ditemukan"));
        return instrumentAnswerEntity.getAnswer();
    }
}
