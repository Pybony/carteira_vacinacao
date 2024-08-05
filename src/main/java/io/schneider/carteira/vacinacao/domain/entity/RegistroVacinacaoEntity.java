package io.schneider.carteira.vacinacao.domain.entity;

import io.schneider.carteira.vacinacao.shared.model.DoseEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registros_vacinacao")
public class RegistroVacinacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false)
    private PessoaEntity pessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacina_id", nullable = false)
    private VacinaEntity vacina;

    @Column(nullable = false)
    private LocalDate dataAplicacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DoseEnum doseAplicada;

}
