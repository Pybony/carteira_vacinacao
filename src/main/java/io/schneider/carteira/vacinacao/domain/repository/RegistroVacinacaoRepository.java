package io.schneider.carteira.vacinacao.domain.repository;

import io.schneider.carteira.vacinacao.domain.entity.PessoaEntity;
import io.schneider.carteira.vacinacao.domain.entity.RegistroVacinacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RegistroVacinacaoRepository extends JpaRepository<RegistroVacinacaoEntity, Long> {

    Collection<RegistroVacinacaoEntity> findByPessoaId(final Long pessoaId);

    List<RegistroVacinacaoEntity> findByPessoaIdAndVacinaId(final Long pessoaId, final Long vacinaId);

    void deleteByPessoa(final PessoaEntity pessoa);
}
