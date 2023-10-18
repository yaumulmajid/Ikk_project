package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "instrument_answer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InstrumentAnswerEntity {
    @Id
    @Column(length = 12)
    private String id;
    private String answer;
}
