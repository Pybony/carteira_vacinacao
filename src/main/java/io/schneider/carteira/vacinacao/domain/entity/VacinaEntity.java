package io.schneider.carteira.vacinacao.domain.entity;

import io.schneider.carteira.vacinacao.shared.model.EsquemaVacinacaoEnum;
import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vacina")
public class VacinaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EsquemaVacinacaoEnum esquemaVacinacao;

}
