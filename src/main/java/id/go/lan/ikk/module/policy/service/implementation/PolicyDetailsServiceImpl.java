package id.go.lan.ikk.module.policy.service.implementation;

import id.go.lan.ikk.exception.ResourceNotFoundException;
import id.go.lan.ikk.module.policy.entity.*;
import id.go.lan.ikk.module.policy.repository.*;
import id.go.lan.ikk.module.policy.service.PolicyDetailsService;
import id.go.lan.ikk.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyDetailsServiceImpl implements PolicyDetailsService {

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PolicyDetailsRepository policyDetailsRepository;

    @Autowired
    private UserService userService;

    @Override
    public void updatePolicyProgressByPolicyId(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        PolicyDetailsEntity policyDetailsEntity = policyEntity.getPolicyDetailsEntity();

        AgendaSettingEntity agendaSettingEntity = policyEntity.getAgendaSettingEntity();
        AgendaSettingFileEntity agendaSettingFileEntity = policyEntity.getAgendaSettingFileEntity();

        FormulasiKebijakanEntity formulasiKebijakanEntity = policyEntity.getFormulasiKebijakanEntity();
        FormulasiKebijakanFileEntity formulasiKebijakanFileEntity = policyEntity.getFormulasiKebijakanFileEntity();

        ImplementasiKebijakanEntity implementasiKebijakanEntity = policyEntity.getImplementasiKebijakanEntity();
        ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity = policyEntity.getImplementasiKebijakanFileEntity();

        EvaluasiKebijakanEntity evaluasiKebijakanEntity = policyEntity.getEvaluasiKebijakanEntity();
        EvaluasiKebijakanFileEntity evaluasiKebijakanFileEntity = policyEntity.getEvaluasiKebijakanFileEntity();

        double agendaSettingProgress = 0.0;
        double formulasiKebijakanProgress = 0.0;
        double implementasiKebijakanProgress = 0.0;
        double evaluasiKebijakanProgress = 0.0;
        double totalProgress;

        if (agendaSettingEntity.getA1A() != null) {
            if (agendaSettingFileEntity.getFilePathA1A() != null) {
                agendaSettingProgress += 1.0;
            } else {
                agendaSettingProgress += 0.5;
            }
        }

        if (agendaSettingEntity.getA1B() != null) {
            if (agendaSettingFileEntity.getFilePathA1B() != null) {
                agendaSettingProgress += 1.0;
            } else {
                agendaSettingProgress += 0.5;
            }
        }

        if (agendaSettingEntity.getA1C() != null) {
            if (agendaSettingFileEntity.getFilePathA1C() != null) {
                agendaSettingProgress += 1.0;
            } else {
                agendaSettingProgress += 0.5;
            }
        }

        if (agendaSettingEntity.getA1D() != null) {
            if (agendaSettingFileEntity.getFilePathA1D() != null) {
                agendaSettingProgress += 1.0;
            } else {
                agendaSettingProgress += 0.5;
            }
        }

        if (agendaSettingEntity.getA2A() != null) {
            if (agendaSettingFileEntity.getFilePathA2A() != null) {
                agendaSettingProgress += 1.0;
            } else {
                agendaSettingProgress += 0.5;
            }
        }

        if (agendaSettingEntity.getA2B() != null) {
            if (agendaSettingFileEntity.getFilePathA2B() != null) {
                agendaSettingProgress += 1.0;
            } else {
                agendaSettingProgress += 0.5;
            }
        }

        if (agendaSettingEntity.getA2C() != null) {
            if (!agendaSettingEntity.getA2C().isEmpty()) {
                if (agendaSettingFileEntity.getFilePathA2C() != null) {
                    agendaSettingProgress += 1.0;
                } else {
                    agendaSettingProgress += 0.5;
                }
            } else {
                agendaSettingProgress += 0.5;
            }
        }

        if (formulasiKebijakanEntity.getB1A() != null) {
            if (formulasiKebijakanFileEntity.getFilePathB1A() != null) {
                formulasiKebijakanProgress += 1.0;
            } else {
                formulasiKebijakanProgress += 0.5;
            }
        }

        if (formulasiKebijakanEntity.getB1B() != null) {
            if (formulasiKebijakanFileEntity.getFilePathB1B() != null) {
                formulasiKebijakanProgress += 1.0;
            } else {
                formulasiKebijakanProgress += 0.5;
            }
        }

        if (formulasiKebijakanEntity.getB2A() != null) {
            if (formulasiKebijakanFileEntity.getFilePathB2A() != null) {
                formulasiKebijakanProgress += 1.0;
            } else {
                formulasiKebijakanProgress += 0.5;
            }
        }

        if (formulasiKebijakanEntity.getB2B() != null) {
            if (formulasiKebijakanFileEntity.getFilePathB2B() != null) {
                formulasiKebijakanProgress += 1.0;
            } else {
                formulasiKebijakanProgress += 0.5;
            }
        }

        if (formulasiKebijakanEntity.getB3A() != null) {
            if (formulasiKebijakanFileEntity.getFilePathB3A() != null) {
                formulasiKebijakanProgress += 1.0;
            } else {
                formulasiKebijakanProgress += 0.5;
            }
        }

        if (formulasiKebijakanEntity.getB3B() != null) {
            if (formulasiKebijakanFileEntity.getFilePathB3B() != null) {
                formulasiKebijakanProgress += 1.0;
            } else {
                formulasiKebijakanProgress += 0.5;
            }
        }

        if (formulasiKebijakanEntity.getB3C() != null) {
            if (formulasiKebijakanFileEntity.getFilePathB3C() != null) {
                formulasiKebijakanProgress += 1.0;
            } else {
                formulasiKebijakanProgress += 0.5;
            }
        }

        if (formulasiKebijakanEntity.getB4A() != null) {
            if (formulasiKebijakanFileEntity.getFilePathB4A() != null) {
                formulasiKebijakanProgress += 1.0;
            } else {
                formulasiKebijakanProgress += 0.5;
            }
        }

        if (formulasiKebijakanEntity.getB4B() != null) {
            if (formulasiKebijakanFileEntity.getFilePathB4B() != null) {
                formulasiKebijakanProgress += 1.0;
            } else {
                formulasiKebijakanProgress += 0.5;
            }
        }

        if (formulasiKebijakanEntity.getB4C() != null) {
            if (formulasiKebijakanFileEntity.getFilePathB4C() != null) {
                formulasiKebijakanProgress += 1.0;
            } else {
                formulasiKebijakanProgress += 0.5;
            }
        }

        if (formulasiKebijakanEntity.getB5A() != null) {
            if (formulasiKebijakanFileEntity.getFilePathB5A() != null) {
                formulasiKebijakanProgress += 1.0;
            } else {
                formulasiKebijakanProgress += 0.5;
            }
        }

        if (formulasiKebijakanEntity.getB5B() != null) {
            if (formulasiKebijakanFileEntity.getFilePathB5B() != null) {
                formulasiKebijakanProgress += 1.0;
            } else {
                formulasiKebijakanProgress += 0.5;
            }
        }

        if (formulasiKebijakanEntity.getB5C() != null) {
            if (formulasiKebijakanFileEntity.getFilePathB5C() != null) {
                formulasiKebijakanProgress += 1.0;
            } else {
                formulasiKebijakanProgress += 0.5;
            }
        }

        if (implementasiKebijakanEntity.getC1A() != null) {
            if (implementasiKebijakanFileEntity.getFilePathC1A() != null) {
                implementasiKebijakanProgress += 1.0;
            } else {
                implementasiKebijakanProgress += 0.5;
            }
        }

        if (implementasiKebijakanEntity.getC1B() != null) {
            if (implementasiKebijakanFileEntity.getFilePathC1B() != null) {
                implementasiKebijakanProgress += 1.0;
            } else {
                implementasiKebijakanProgress += 0.5;
            }
        }

        if (implementasiKebijakanEntity.getC1C() != null) {
            if (implementasiKebijakanFileEntity.getFilePathC1C() != null) {
                implementasiKebijakanProgress += 1.0;
            } else {
                implementasiKebijakanProgress += 0.5;
            }
        }

        if (implementasiKebijakanEntity.getC1D() != null) {
            if (implementasiKebijakanFileEntity.getFilePathC1D() != null) {
                implementasiKebijakanProgress += 1.0;
            } else {
                implementasiKebijakanProgress += 0.5;
            }
        }

        if (implementasiKebijakanEntity.getC2A() != null) {
            if (implementasiKebijakanFileEntity.getFilePathC2A() != null) {
                implementasiKebijakanProgress += 1.0;
            } else {
                implementasiKebijakanProgress += 0.5;
            }
        }

        if (implementasiKebijakanEntity.getC2B() != null) {
            if (implementasiKebijakanFileEntity.getFilePathC2B() != null) {
                implementasiKebijakanProgress += 1.0;
            } else {
                implementasiKebijakanProgress += 0.5;
            }
        }

        if (implementasiKebijakanEntity.getC2C() != null) {
            if (implementasiKebijakanFileEntity.getFilePathC2C() != null) {
                implementasiKebijakanProgress += 1.0;
            } else {
                implementasiKebijakanProgress += 0.5;
            }
        }

        if (implementasiKebijakanEntity.getC3A() != null) {
            if (implementasiKebijakanFileEntity.getFilePathC3A() != null) {
                implementasiKebijakanProgress += 1.0;
            } else {
                implementasiKebijakanProgress += 0.5;
            }
        }

        if (implementasiKebijakanEntity.getC3B() != null) {
            if (implementasiKebijakanFileEntity.getFilePathC3B() != null) {
                implementasiKebijakanProgress += 1.0;
            } else {
                implementasiKebijakanProgress += 0.5;
            }
        }

        if (implementasiKebijakanEntity.getC3C() != null) {
            if (implementasiKebijakanFileEntity.getFilePathC3C() != null) {
                implementasiKebijakanProgress += 1.0;
            } else {
                implementasiKebijakanProgress += 0.5;
            }
        }

        if (evaluasiKebijakanEntity.getD1A() != null) {
            if (evaluasiKebijakanFileEntity.getFilePathD1A() != null) {
                evaluasiKebijakanProgress += 1.0;
            } else {
                evaluasiKebijakanProgress += 0.5;
            }
        }

        if (evaluasiKebijakanEntity.getD1B() != null) {
            if (evaluasiKebijakanFileEntity.getFilePathD1B() != null) {
                evaluasiKebijakanProgress += 1.0;
            } else {
                evaluasiKebijakanProgress += 0.5;
            }
        }

        if (evaluasiKebijakanEntity.getD2A() != null) {
            if (evaluasiKebijakanFileEntity.getFilePathD2A() != null) {
                evaluasiKebijakanProgress += 1.0;
            } else {
                evaluasiKebijakanProgress += 0.5;
            }
        }

        if (evaluasiKebijakanEntity.getD2B() != null) {
            if (evaluasiKebijakanFileEntity.getFilePathD2B() != null) {
                evaluasiKebijakanProgress += 1.0;
            } else {
                evaluasiKebijakanProgress += 0.5;
            }
        }

        if (evaluasiKebijakanEntity.getD3A() != null) {
            if (evaluasiKebijakanFileEntity.getFilePathD3A() != null) {
                evaluasiKebijakanProgress += 1.0;
            } else {
                evaluasiKebijakanProgress += 0.5;
            }
        }

        if (evaluasiKebijakanEntity.getD3B() != null) {
            if (evaluasiKebijakanFileEntity.getFilePathD3B() != null) {
                evaluasiKebijakanProgress += 1.0;
            } else {
                evaluasiKebijakanProgress += 0.5;
            }
        }

        if (evaluasiKebijakanEntity.getD3C() != null) {
            if (evaluasiKebijakanFileEntity.getFilePathD3C() != null) {
                evaluasiKebijakanProgress += 1.0;
            } else {
                evaluasiKebijakanProgress += 0.5;
            }
        }

        if (evaluasiKebijakanEntity.getD3D() != null) {
            if (evaluasiKebijakanFileEntity.getFilePathD3D() != null) {
                evaluasiKebijakanProgress += 1.0;
            } else {
                evaluasiKebijakanProgress += 0.5;
            }
        }

        if (evaluasiKebijakanEntity.getD3E() != null) {
            if (evaluasiKebijakanFileEntity.getFilePathD3E() != null) {
                evaluasiKebijakanProgress += 1.0;
            } else {
                evaluasiKebijakanProgress += 0.5;
            }
        }

        totalProgress = agendaSettingProgress + formulasiKebijakanProgress + implementasiKebijakanProgress + evaluasiKebijakanProgress;

        policyDetailsEntity.setProgress((totalProgress / 39.00) * 100.0);
        policyDetailsRepository.save(policyDetailsEntity);
    }

    @Override
    public void updatePolicyBaseScoreByPolicyId(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        PolicyDetailsEntity policyDetailsEntity = policyEntity.getPolicyDetailsEntity();

        policyDetailsEntity.setBaseScore(calculateTotalBaseScore(policyId));
        policyDetailsEntity.setModifiedBy(userService.getSignedInUserId());
        policyDetailsRepository.save(policyDetailsEntity);
    }

    @Override
    public void updatePolicyValidationKIScoreByPolicyId(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        PolicyDetailsEntity policyDetailsEntity = policyEntity.getPolicyDetailsEntity();

        policyDetailsEntity.setValidationKIScore(calculateTotalValidationKIScore(policyId));
        policyDetailsEntity.setModifiedBy(userService.getSignedInUserId());
        policyDetailsRepository.save(policyDetailsEntity);
    }

    @Override
    public void updatePolicyValidationKUScoreByPolicyId(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        PolicyDetailsEntity policyDetailsEntity = policyEntity.getPolicyDetailsEntity();

        policyDetailsEntity.setValidationKUScore(calculateTotalValidationKUScore(policyId));
        policyDetailsEntity.setModifiedBy(userService.getSignedInUserId());
        policyDetailsRepository.save(policyDetailsEntity);
    }

    @Override
    public String getPolicyValidationKINote(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        PolicyDetailsEntity policyDetailsEntity = policyEntity.getPolicyDetailsEntity();

        return policyDetailsEntity.getValidationKINote();
    }

    @Override
    public void updatePolicyValidationKINote(Long policyId, String note) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        PolicyDetailsEntity policyDetailsEntity = policyEntity.getPolicyDetailsEntity();

        policyDetailsEntity.setValidationKINote(note);
        policyDetailsEntity.setModifiedBy(userService.getSignedInUserId());
        policyDetailsRepository.save(policyDetailsEntity);
    }

    @Override
    public Double getAgendaSettingBaseScoreByPolicyId(Long policyId) {
        return calculateBaseA(policyId);
    }

    @Override
    public Double getAgendaSettingValidationKIScoreByPolicyId(Long policyId) {
        return calculateValidationKIA(policyId);
    }

    @Override
    public Double getAgendaSettingValidationKUScoreByPolicyId(Long policyId) {
        return calculateValidationKUA(policyId);
    }

    @Override
    public Double getFormulasiKebijakanBaseScoreByPolicyId(Long policyId) {
        return calculateBaseB(policyId);
    }

    @Override
    public Double getFormulasiKebijakanValidationKIScoreByPolicyId(Long policyId) {
        return calculateValidationKIB(policyId);
    }

    @Override
    public Double getFormulasiKebijakanValidationKUScoreByPolicyId(Long policyId) {
        return calculateValidationKUB(policyId);
    }

    @Override
    public Double getImplementasiKebijakanBaseScoreByPolicyId(Long policyId) {
        return calculateBaseC(policyId);
    }

    @Override
    public Double getImplementasiKebijakanValidationKIScoreByPolicyId(Long policyId) {
        return calculateValidationKIC(policyId);
    }

    @Override
    public Double getImplementasiKebijakanValidationKUScoreByPolicyId(Long policyId) {
        return calculateValidationKUC(policyId);
    }

    @Override
    public Double getEvaluasiKebijakanBaseScoreByPolicyId(Long policyId) {
        return calculateBaseD(policyId);
    }

    @Override
    public Double getEvaluasiKebijakanValidationKIScoreByPolicyId(Long policyId) {
        return calculateValidationKID(policyId);
    }

    @Override
    public Double getEvaluasiKebijakanValidationKUScoreByPolicyId(Long policyId) {
        return calculateValidationKUD(policyId);
    }

    private Double calculateTotalBaseScore(Long policyId) {
        double totalScore;

        double aBScore = calculateBaseAB(policyId);
        double cDScore = calculateBaseCD(policyId);

        totalScore = (aBScore * 45/100) + (cDScore * 55/100);
        return totalScore;
    }

    private Double calculateBaseAB(Long policyId) {
        double totalScore;

        double aScore = calculateBaseA(policyId);
        double bScore = calculateBaseB(policyId);

        totalScore = (aScore * 45/100) + (bScore * 55/100);
        return totalScore;
    }

    private Double calculateBaseCD(Long policyId) {
        double totalScore;

        double cScore = calculateBaseC(policyId);
        double dScore = calculateBaseD(policyId);

        totalScore = (cScore * 50/100) + (dScore * 50/100);
        return totalScore;
    }

    private Double calculateBaseA(Long policyId) {
        double totalScore;

        double a1Score = calculateBaseA1(policyId);
        double a2Score = calculateBaseA2(policyId);

        totalScore = (a1Score * 55/100) + (a2Score * 45/100);
        return totalScore;
    }

    private Double calculateBaseB(Long policyId) {
        double totalScore;

        double b1Score = calculateBaseB1(policyId);
        double b2Score = calculateBaseB2(policyId);
        double b3Score = calculateBaseB3(policyId);
        double b4Score = calculateBaseB4(policyId);
        double b5Score = calculateBaseB5(policyId);

        totalScore = (b1Score * 10/100) + (b2Score * 15/100) + (b3Score * 20/100) + (b4Score * 40/100) + (b5Score * 15/100);
        return totalScore;
    }

    private Double calculateBaseC(Long policyId) {
        double totalScore;

        double c1Score = calculateBaseC1(policyId);
        double c2Score = calculateBaseC2(policyId);
        double c3Score = calculateBaseC3(policyId);

        totalScore = (c1Score * 30/100) + (c2Score * 35/100) + (c3Score * 35/100);
        return totalScore;
    }

    private Double calculateBaseD(Long policyId) {
        double totalScore;

        double d1Score = calculateBaseD1(policyId);
        double d2Score = calculateBaseD2(policyId);
        double d3Score = calculateBaseD3(policyId);

        totalScore = (d1Score * 40/100) + (d2Score * 15/100) + (d3Score * 45/100);
        return totalScore;
    }

    private Double calculateBaseA1(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        AgendaSettingBaseScoreEntity agendaSettingBaseScoreEntity = policyEntity.getAgendaSettingBaseScoreEntity();

        double totalScore;

        double a1A = agendaSettingBaseScoreEntity.getA1A();
        double a1B = agendaSettingBaseScoreEntity.getA1B();
        double a1C = agendaSettingBaseScoreEntity.getA1C();
        double a1D = agendaSettingBaseScoreEntity.getA1D();

        totalScore = (a1A * 25/100) + (a1B * 25/100) + (a1C * 25/100) + (a1D * 25/100);
        return totalScore;
    }

    private Double calculateBaseA2(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        AgendaSettingBaseScoreEntity agendaSettingBaseScoreEntity = policyEntity.getAgendaSettingBaseScoreEntity();

        double totalScore;

        double a2A = agendaSettingBaseScoreEntity.getA2A();
        double a2B = agendaSettingBaseScoreEntity.getA2B();
        double a2C = agendaSettingBaseScoreEntity.getA2C();

        totalScore = (a2A * 30/100) + (a2B * 35/100) + (a2C * 35/100);
        return totalScore;
    }

    private Double calculateBaseB1(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();

        double totalScore;

        double b1A = formulasiKebijakanBaseScoreEntity.getB1A();
        double b1B = formulasiKebijakanBaseScoreEntity.getB1B();

        totalScore = (b1A * 50/100) + (b1B * 50/100);
        return totalScore;
    }

    private Double calculateBaseB2(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();

        double totalScore;

        double b2A = formulasiKebijakanBaseScoreEntity.getB2A();
        double b2B = formulasiKebijakanBaseScoreEntity.getB2B();

        totalScore = (b2A * 50/100) + (b2B * 50/100);
        return totalScore;
    }

    private Double calculateBaseB3(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();

        double totalScore;

        double b3A = formulasiKebijakanBaseScoreEntity.getB3A();
        double b3B = formulasiKebijakanBaseScoreEntity.getB3B();
        double b3C = formulasiKebijakanBaseScoreEntity.getB3C();

        totalScore = (b3A * 40/100) + (b3B * 40/100) + (b3C * 20/100);
        return totalScore;
    }

    private Double calculateBaseB4(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();

        double totalScore;

        double b4A = formulasiKebijakanBaseScoreEntity.getB4A();
        double b4B = formulasiKebijakanBaseScoreEntity.getB4B();
        double b4C = formulasiKebijakanBaseScoreEntity.getB4C();

        totalScore = (b4A * 30/100) + (b4B * 40/100) + (b4C * 30/100);
        return totalScore;
    }

    private Double calculateBaseB5(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();

        double totalScore;

        double b5A = formulasiKebijakanBaseScoreEntity.getB5A();
        double b5B = formulasiKebijakanBaseScoreEntity.getB5B();
        double b5C = formulasiKebijakanBaseScoreEntity.getB5C();

        totalScore = (b5A * 30/100) + (b5B * 35/100) + (b5C * 35/100);
        return totalScore;
    }

    private Double calculateBaseC1(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        ImplementasiKebijakanBaseScoreEntity implementasiKebijakanBaseScoreEntity = policyEntity.getImplementasiKebijakanBaseScoreEntity();

        double totalScore;

        double c1A = implementasiKebijakanBaseScoreEntity.getC1A();
        double c1B = implementasiKebijakanBaseScoreEntity.getC1B();
        double c1C = implementasiKebijakanBaseScoreEntity.getC1C();
        double c1D = implementasiKebijakanBaseScoreEntity.getC1D();

        totalScore = (c1A * 25/100) + (c1B * 25/100) + (c1C * 25/100) + (c1D * 25/100);
        return totalScore;
    }

    private Double calculateBaseC2(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        ImplementasiKebijakanBaseScoreEntity implementasiKebijakanBaseScoreEntity = policyEntity.getImplementasiKebijakanBaseScoreEntity();

        double totalScore;

        double c2A = implementasiKebijakanBaseScoreEntity.getC2A();
        double c2B = implementasiKebijakanBaseScoreEntity.getC2B();
        double c2C = implementasiKebijakanBaseScoreEntity.getC2C();

        totalScore = (c2A * 35/100) + (c2B * 30/100) + (c2C * 35/100);
        return totalScore;
    }

    private Double calculateBaseC3(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        ImplementasiKebijakanBaseScoreEntity implementasiKebijakanBaseScoreEntity = policyEntity.getImplementasiKebijakanBaseScoreEntity();

        double totalScore;

        double c3A = implementasiKebijakanBaseScoreEntity.getC3A();
        double c3B = implementasiKebijakanBaseScoreEntity.getC3B();
        double c3C = implementasiKebijakanBaseScoreEntity.getC3C();

        totalScore = (c3A * 30/100) + (c3B * 35/100) + (c3C * 35/100);
        return totalScore;
    }

    private Double calculateBaseD1(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        EvaluasiKebijakanBaseScoreEntity evaluasiKebijakanBaseScoreEntity = policyEntity.getEvaluasiKebijakanBaseScoreEntity();

        double totalScore;

        double d1A = evaluasiKebijakanBaseScoreEntity.getD1A();
        double d1B = evaluasiKebijakanBaseScoreEntity.getD1B();

        totalScore = (d1A * 50/100) + (d1B * 50/100);
        return totalScore;
    }

    private Double calculateBaseD2(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        EvaluasiKebijakanBaseScoreEntity evaluasiKebijakanBaseScoreEntity = policyEntity.getEvaluasiKebijakanBaseScoreEntity();

        double totalScore;

        double d2A = evaluasiKebijakanBaseScoreEntity.getD2A();
        double d2B = evaluasiKebijakanBaseScoreEntity.getD2B();

        totalScore = (d2A * 50/100) + (d2B * 50/100);
        return totalScore;
    }

    private Double calculateBaseD3(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        EvaluasiKebijakanBaseScoreEntity evaluasiKebijakanBaseScoreEntity = policyEntity.getEvaluasiKebijakanBaseScoreEntity();

        double totalScore;

        double d3A = evaluasiKebijakanBaseScoreEntity.getD3A();
        double d3B = evaluasiKebijakanBaseScoreEntity.getD3B();
        double d3C = evaluasiKebijakanBaseScoreEntity.getD3C();
        double d3D = evaluasiKebijakanBaseScoreEntity.getD3D();
        double d3E = evaluasiKebijakanBaseScoreEntity.getD3E();

        totalScore = (d3A * 20/100) + (d3B * 20/100) + (d3C * 20/100) + (d3D * 20/100) + (d3E * 20/100);
        return totalScore;
    }

    private Double calculateTotalValidationKIScore(Long policyId) {
        double totalScore;

        double aBScore = calculateValidationKIAB(policyId);
        double cDScore = calculateValidationKICD(policyId);

        totalScore = (aBScore * 45/100) + (cDScore * 55/100);
        return totalScore;
    }

    private Double calculateValidationKIAB(Long policyId) {
        double totalScore;

        double aScore = calculateValidationKIA(policyId);
        double bScore = calculateValidationKIB(policyId);

        totalScore = (aScore * 45/100) + (bScore * 55/100);
        return totalScore;
    }

    private Double calculateValidationKICD(Long policyId) {
        double totalScore;

        double cScore = calculateValidationKIC(policyId);
        double dScore = calculateValidationKID(policyId);

        totalScore = (cScore * 50/100) + (dScore * 50/100);
        return totalScore;
    }

    private Double calculateValidationKIA(Long policyId) {
        double totalScore;

        double a1Score = calculateValidationKIA1(policyId);
        double a2Score = calculateValidationKIA2(policyId);

        totalScore = (a1Score * 55/100) + (a2Score * 45/100);
        return totalScore;
    }

    private Double calculateValidationKIB(Long policyId) {
        double totalScore;

        double b1Score = calculateValidationKIB1(policyId);
        double b2Score = calculateValidationKIB2(policyId);
        double b3Score = calculateValidationKIB3(policyId);
        double b4Score = calculateValidationKIB4(policyId);
        double b5Score = calculateValidationKIB5(policyId);

        totalScore = (b1Score * 10/100) + (b2Score * 15/100) + (b3Score * 20/100) + (b4Score * 40/100) + (b5Score * 15/100);
        return totalScore;
    }

    private Double calculateValidationKIC(Long policyId) {
        double totalScore;

        double c1Score = calculateValidationKIC1(policyId);
        double c2Score = calculateValidationKIC2(policyId);
        double c3Score = calculateValidationKIC3(policyId);

        totalScore = (c1Score * 30/100) + (c2Score * 35/100) + (c3Score * 35/100);
        return totalScore;
    }

    private Double calculateValidationKID(Long policyId) {
        double totalScore;

        double d1Score = calculateValidationKID1(policyId);
        double d2Score = calculateValidationKID2(policyId);
        double d3Score = calculateValidationKID3(policyId);

        totalScore = (d1Score * 40/100) + (d2Score * 15/100) + (d3Score * 45/100);
        return totalScore;
    }

    private Double calculateValidationKIA1(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        AgendaSettingKIScoreEntity agendaSettingKIScoreEntity = policyEntity.getAgendaSettingKIScoreEntity();

        double totalScore;

        double a1A = agendaSettingKIScoreEntity.getA1A();
        double a1B = agendaSettingKIScoreEntity.getA1B();
        double a1C = agendaSettingKIScoreEntity.getA1C();
        double a1D = agendaSettingKIScoreEntity.getA1D();

        totalScore = (a1A * 25/100) + (a1B * 25/100) + (a1C * 25/100) + (a1D * 25/100);
        return totalScore;
    }

    private Double calculateValidationKIA2(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        AgendaSettingKIScoreEntity agendaSettingKIScoreEntity = policyEntity.getAgendaSettingKIScoreEntity();

        double totalScore;

        double a2A = agendaSettingKIScoreEntity.getA2A();
        double a2B = agendaSettingKIScoreEntity.getA2B();
        double a2C = agendaSettingKIScoreEntity.getA2C();

        totalScore = (a2A * 30/100) + (a2B * 35/100) + (a2C * 35/100);
        return totalScore;
    }

    private Double calculateValidationKIB1(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        FormulasiKebijakanKIScoreEntity formulasiKebijakanKIScoreEntity = policyEntity.getFormulasiKebijakanKIScoreEntity();

        double totalScore;

        double b1A = formulasiKebijakanKIScoreEntity.getB1A();
        double b1B = formulasiKebijakanKIScoreEntity.getB1B();

        totalScore = (b1A * 50/100) + (b1B * 50/100);
        return totalScore;
    }

    private Double calculateValidationKIB2(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        FormulasiKebijakanKIScoreEntity formulasiKebijakanKIScoreEntity = policyEntity.getFormulasiKebijakanKIScoreEntity();

        double totalScore;

        double b2A = formulasiKebijakanKIScoreEntity.getB2A();
        double b2B = formulasiKebijakanKIScoreEntity.getB2B();

        totalScore = (b2A * 50/100) + (b2B * 50/100);
        return totalScore;
    }

    private Double calculateValidationKIB3(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        FormulasiKebijakanKIScoreEntity formulasiKebijakanKIScoreEntity = policyEntity.getFormulasiKebijakanKIScoreEntity();

        double totalScore;

        double b3A = formulasiKebijakanKIScoreEntity.getB3A();
        double b3B = formulasiKebijakanKIScoreEntity.getB3B();
        double b3C = formulasiKebijakanKIScoreEntity.getB3C();

        totalScore = (b3A * 40/100) + (b3B * 40/100) + (b3C * 20/100);
        return totalScore;
    }

    private Double calculateValidationKIB4(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        FormulasiKebijakanKIScoreEntity formulasiKebijakanKIScoreEntity = policyEntity.getFormulasiKebijakanKIScoreEntity();

        double totalScore;

        double b4A = formulasiKebijakanKIScoreEntity.getB4A();
        double b4B = formulasiKebijakanKIScoreEntity.getB4B();
        double b4C = formulasiKebijakanKIScoreEntity.getB4C();

        totalScore = (b4A * 30/100) + (b4B * 40/100) + (b4C * 30/100);
        return totalScore;
    }

    private Double calculateValidationKIB5(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        FormulasiKebijakanKIScoreEntity formulasiKebijakanKIScoreEntity = policyEntity.getFormulasiKebijakanKIScoreEntity();

        double totalScore;

        double b5A = formulasiKebijakanKIScoreEntity.getB5A();
        double b5B = formulasiKebijakanKIScoreEntity.getB5B();
        double b5C = formulasiKebijakanKIScoreEntity.getB5C();

        totalScore = (b5A * 30/100) + (b5B * 35/100) + (b5C * 35/100);
        return totalScore;
    }

    private Double calculateValidationKIC1(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        ImplementasiKebijakanKIScoreEntity implementasiKebijakanKIScoreEntity = policyEntity.getImplementasiKebijakanKIScoreEntity();

        double totalScore;

        double c1A = implementasiKebijakanKIScoreEntity.getC1A();
        double c1B = implementasiKebijakanKIScoreEntity.getC1B();
        double c1C = implementasiKebijakanKIScoreEntity.getC1C();
        double c1D = implementasiKebijakanKIScoreEntity.getC1D();

        totalScore = (c1A * 25/100) + (c1B * 25/100) + (c1C * 25/100) + (c1D * 25/100);
        return totalScore;
    }

    private Double calculateValidationKIC2(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        ImplementasiKebijakanKIScoreEntity implementasiKebijakanKIScoreEntity = policyEntity.getImplementasiKebijakanKIScoreEntity();

        double totalScore;

        double c2A = implementasiKebijakanKIScoreEntity.getC2A();
        double c2B = implementasiKebijakanKIScoreEntity.getC2B();
        double c2C = implementasiKebijakanKIScoreEntity.getC2C();

        totalScore = (c2A * 35/100) + (c2B * 30/100) + (c2C * 35/100);
        return totalScore;
    }

    private Double calculateValidationKIC3(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        ImplementasiKebijakanKIScoreEntity implementasiKebijakanKIScoreEntity = policyEntity.getImplementasiKebijakanKIScoreEntity();

        double totalScore;

        double c3A = implementasiKebijakanKIScoreEntity.getC3A();
        double c3B = implementasiKebijakanKIScoreEntity.getC3B();
        double c3C = implementasiKebijakanKIScoreEntity.getC3C();

        totalScore = (c3A * 30/100) + (c3B * 35/100) + (c3C * 35/100);
        return totalScore;
    }

    private Double calculateValidationKID1(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        EvaluasiKebijakanKIScoreEntity evaluasiKebijakanKIScoreEntity = policyEntity.getEvaluasiKebijakanKIScoreEntity();

        double totalScore;

        double d1A = evaluasiKebijakanKIScoreEntity.getD1A();
        double d1B = evaluasiKebijakanKIScoreEntity.getD1B();

        totalScore = (d1A * 50/100) + (d1B * 50/100);
        return totalScore;
    }

    private Double calculateValidationKID2(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        EvaluasiKebijakanKIScoreEntity evaluasiKebijakanKIScoreEntity = policyEntity.getEvaluasiKebijakanKIScoreEntity();

        double totalScore;

        double d2A = evaluasiKebijakanKIScoreEntity.getD2A();
        double d2B = evaluasiKebijakanKIScoreEntity.getD2B();

        totalScore = (d2A * 50/100) + (d2B * 50/100);
        return totalScore;
    }

    private Double calculateValidationKID3(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        EvaluasiKebijakanKIScoreEntity evaluasiKebijakanKIScoreEntity = policyEntity.getEvaluasiKebijakanKIScoreEntity();

        double totalScore;

        double d3A = evaluasiKebijakanKIScoreEntity.getD3A();
        double d3B = evaluasiKebijakanKIScoreEntity.getD3B();
        double d3C = evaluasiKebijakanKIScoreEntity.getD3C();
        double d3D = evaluasiKebijakanKIScoreEntity.getD3D();
        double d3E = evaluasiKebijakanKIScoreEntity.getD3E();

        totalScore = (d3A * 20/100) + (d3B * 20/100) + (d3C * 20/100) + (d3D * 20/100) + (d3E * 20/100);
        return totalScore;
    }

    private Double calculateTotalValidationKUScore(Long policyId) {
        double totalScore;

        double aBScore = calculateValidationKUAB(policyId);
        double cDScore = calculateValidationKUCD(policyId);

        totalScore = (aBScore * 45/100) + (cDScore * 55/100);
        return totalScore;
    }

    private Double calculateValidationKUAB(Long policyId) {
        double totalScore;

        double aScore = calculateValidationKUA(policyId);
        double bScore = calculateValidationKUB(policyId);

        totalScore = (aScore * 45/100) + (bScore * 55/100);
        return totalScore;
    }

    private Double calculateValidationKUCD(Long policyId) {
        double totalScore;

        double cScore = calculateValidationKUC(policyId);
        double dScore = calculateValidationKUD(policyId);

        totalScore = (cScore * 50/100) + (dScore * 50/100);
        return totalScore;
    }

    private Double calculateValidationKUA(Long policyId) {
        double totalScore;

        double a1Score = calculateValidationKUA1(policyId);
        double a2Score = calculateValidationKUA2(policyId);

        totalScore = (a1Score * 55/100) + (a2Score * 45/100);
        return totalScore;
    }

    private Double calculateValidationKUB(Long policyId) {
        double totalScore;

        double b1Score = calculateValidationKUB1(policyId);
        double b2Score = calculateValidationKUB2(policyId);
        double b3Score = calculateValidationKUB3(policyId);
        double b4Score = calculateValidationKUB4(policyId);
        double b5Score = calculateValidationKUB5(policyId);

        totalScore = (b1Score * 10/100) + (b2Score * 15/100) + (b3Score * 20/100) + (b4Score * 40/100) + (b5Score * 15/100);
        return totalScore;
    }

    private Double calculateValidationKUC(Long policyId) {
        double totalScore;

        double c1Score = calculateValidationKUC1(policyId);
        double c2Score = calculateValidationKUC2(policyId);
        double c3Score = calculateValidationKUC3(policyId);

        totalScore = (c1Score * 30/100) + (c2Score * 35/100) + (c3Score * 35/100);
        return totalScore;
    }

    private Double calculateValidationKUD(Long policyId) {
        double totalScore;

        double d1Score = calculateValidationKUD1(policyId);
        double d2Score = calculateValidationKUD2(policyId);
        double d3Score = calculateValidationKUD3(policyId);

        totalScore = (d1Score * 40/100) + (d2Score * 15/100) + (d3Score * 45/100);
        return totalScore;
    }

    private Double calculateValidationKUA1(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        AgendaSettingKUScoreEntity agendaSettingKUScoreEntity = policyEntity.getAgendaSettingKUScoreEntity();

        double totalScore;

        double a1A = agendaSettingKUScoreEntity.getA1A();
        double a1B = agendaSettingKUScoreEntity.getA1B();
        double a1C = agendaSettingKUScoreEntity.getA1C();
        double a1D = agendaSettingKUScoreEntity.getA1D();

        totalScore = (a1A * 25/100) + (a1B * 25/100) + (a1C * 25/100) + (a1D * 25/100);
        return totalScore;
    }

    private Double calculateValidationKUA2(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        AgendaSettingKUScoreEntity agendaSettingKUScoreEntity = policyEntity.getAgendaSettingKUScoreEntity();

        double totalScore;

        double a2A = agendaSettingKUScoreEntity.getA2A();
        double a2B = agendaSettingKUScoreEntity.getA2B();
        double a2C = agendaSettingKUScoreEntity.getA2C();

        totalScore = (a2A * 30/100) + (a2B * 35/100) + (a2C * 35/100);
        return totalScore;
    }

    private Double calculateValidationKUB1(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        FormulasiKebijakanKUScoreEntity formulasiKebijakanKUScoreEntity = policyEntity.getFormulasiKebijakanKUScoreEntity();

        double totalScore;

        double b1A = formulasiKebijakanKUScoreEntity.getB1A();
        double b1B = formulasiKebijakanKUScoreEntity.getB1B();

        totalScore = (b1A * 50/100) + (b1B * 50/100);
        return totalScore;
    }

    private Double calculateValidationKUB2(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        FormulasiKebijakanKUScoreEntity formulasiKebijakanKUScoreEntity = policyEntity.getFormulasiKebijakanKUScoreEntity();

        double totalScore;

        double b2A = formulasiKebijakanKUScoreEntity.getB2A();
        double b2B = formulasiKebijakanKUScoreEntity.getB2B();

        totalScore = (b2A * 50/100) + (b2B * 50/100);
        return totalScore;
    }

    private Double calculateValidationKUB3(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        FormulasiKebijakanKUScoreEntity formulasiKebijakanKUScoreEntity = policyEntity.getFormulasiKebijakanKUScoreEntity();

        double totalScore;

        double b3A = formulasiKebijakanKUScoreEntity.getB3A();
        double b3B = formulasiKebijakanKUScoreEntity.getB3B();
        double b3C = formulasiKebijakanKUScoreEntity.getB3C();

        totalScore = (b3A * 40/100) + (b3B * 40/100) + (b3C * 20/100);
        return totalScore;
    }

    private Double calculateValidationKUB4(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        FormulasiKebijakanKUScoreEntity formulasiKebijakanKUScoreEntity = policyEntity.getFormulasiKebijakanKUScoreEntity();

        double totalScore;

        double b4A = formulasiKebijakanKUScoreEntity.getB4A();
        double b4B = formulasiKebijakanKUScoreEntity.getB4B();
        double b4C = formulasiKebijakanKUScoreEntity.getB4C();

        totalScore = (b4A * 30/100) + (b4B * 40/100) + (b4C * 30/100);
        return totalScore;
    }

    private Double calculateValidationKUB5(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        FormulasiKebijakanKUScoreEntity formulasiKebijakanKUScoreEntity = policyEntity.getFormulasiKebijakanKUScoreEntity();

        double totalScore;

        double b5A = formulasiKebijakanKUScoreEntity.getB5A();
        double b5B = formulasiKebijakanKUScoreEntity.getB5B();
        double b5C = formulasiKebijakanKUScoreEntity.getB5C();

        totalScore = (b5A * 30/100) + (b5B * 35/100) + (b5C * 35/100);
        return totalScore;
    }

    private Double calculateValidationKUC1(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        ImplementasiKebijakanKUScoreEntity implementasiKebijakanKUScoreEntity = policyEntity.getImplementasiKebijakanKUScoreEntity();

        double totalScore;

        double c1A = implementasiKebijakanKUScoreEntity.getC1A();
        double c1B = implementasiKebijakanKUScoreEntity.getC1B();
        double c1C = implementasiKebijakanKUScoreEntity.getC1C();
        double c1D = implementasiKebijakanKUScoreEntity.getC1D();

        totalScore = (c1A * 25/100) + (c1B * 25/100) + (c1C * 25/100) + (c1D * 25/100);
        return totalScore;
    }

    private Double calculateValidationKUC2(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        ImplementasiKebijakanKUScoreEntity implementasiKebijakanKUScoreEntity = policyEntity.getImplementasiKebijakanKUScoreEntity();

        double totalScore;

        double c2A = implementasiKebijakanKUScoreEntity.getC2A();
        double c2B = implementasiKebijakanKUScoreEntity.getC2B();
        double c2C = implementasiKebijakanKUScoreEntity.getC2C();

        totalScore = (c2A * 35/100) + (c2B * 30/100) + (c2C * 35/100);
        return totalScore;
    }

    private Double calculateValidationKUC3(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        ImplementasiKebijakanKUScoreEntity implementasiKebijakanKUScoreEntity = policyEntity.getImplementasiKebijakanKUScoreEntity();

        double totalScore;

        double c3A = implementasiKebijakanKUScoreEntity.getC3A();
        double c3B = implementasiKebijakanKUScoreEntity.getC3B();
        double c3C = implementasiKebijakanKUScoreEntity.getC3C();

        totalScore = (c3A * 30/100) + (c3B * 35/100) + (c3C * 35/100);
        return totalScore;
    }

    private Double calculateValidationKUD1(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        EvaluasiKebijakanKUScoreEntity evaluasiKebijakanKUScoreEntity = policyEntity.getEvaluasiKebijakanKUScoreEntity();

        double totalScore;

        double d1A = evaluasiKebijakanKUScoreEntity.getD1A();
        double d1B = evaluasiKebijakanKUScoreEntity.getD1B();

        totalScore = (d1A * 50/100) + (d1B * 50/100);
        return totalScore;
    }

    private Double calculateValidationKUD2(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        EvaluasiKebijakanKUScoreEntity evaluasiKebijakanKUScoreEntity = policyEntity.getEvaluasiKebijakanKUScoreEntity();

        double totalScore;

        double d2A = evaluasiKebijakanKUScoreEntity.getD2A();
        double d2B = evaluasiKebijakanKUScoreEntity.getD2B();

        totalScore = (d2A * 50/100) + (d2B * 50/100);
        return totalScore;
    }

    private Double calculateValidationKUD3(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        EvaluasiKebijakanKUScoreEntity evaluasiKebijakanKUScoreEntity = policyEntity.getEvaluasiKebijakanKUScoreEntity();

        double totalScore;

        double d3A = evaluasiKebijakanKUScoreEntity.getD3A();
        double d3B = evaluasiKebijakanKUScoreEntity.getD3B();
        double d3C = evaluasiKebijakanKUScoreEntity.getD3C();
        double d3D = evaluasiKebijakanKUScoreEntity.getD3D();
        double d3E = evaluasiKebijakanKUScoreEntity.getD3E();

        totalScore = (d3A * 20/100) + (d3B * 20/100) + (d3C * 20/100) + (d3D * 20/100) + (d3E * 20/100);
        return totalScore;
    }
}
