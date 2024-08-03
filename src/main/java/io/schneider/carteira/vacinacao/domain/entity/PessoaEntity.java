package io.schneider.carteira.vacinacao.domain.entity;

import io.schneider.carteira.vacinacao.shared.model.SexoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pessoa")
public class PessoaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SexoEnum sexo;

}
