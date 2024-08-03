package io.schneider.carteira.vacinacao.domain.repository;

import io.schneider.carteira.vacinacao.domain.entity.RegistroVacinacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroVacinacaoRepository extends JpaRepository<RegistroVacinacaoEntity, Long> {
}
