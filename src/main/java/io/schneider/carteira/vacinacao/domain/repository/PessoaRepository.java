package io.schneider.carteira.vacinacao.domain.repository;

import io.schneider.carteira.vacinacao.domain.entity.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<PessoaEntity, Long> {
}
