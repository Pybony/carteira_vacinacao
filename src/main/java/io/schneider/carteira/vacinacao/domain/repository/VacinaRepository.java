package io.schneider.carteira.vacinacao.domain.repository;

import io.schneider.carteira.vacinacao.domain.entity.PessoaEntity;
import io.schneider.carteira.vacinacao.domain.entity.VacinaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacinaRepository extends JpaRepository<VacinaEntity, Long> {
}
