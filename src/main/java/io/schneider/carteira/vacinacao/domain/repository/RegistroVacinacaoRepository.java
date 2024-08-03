package io.schneider.carteira.vacinacao.domain.repository;

import io.schneider.carteira.vacinacao.domain.entity.RegistroVacinacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RegistroVacinacaoRepository extends JpaRepository<RegistroVacinacaoEntity, Long> {

    Collection<RegistroVacinacaoEntity> findByPessoaId(Long pessoaId);
}
