package io.schneider.carteira.vacinacao.domain.mapper;

import io.schneider.carteira.vacinacao.controller.dto.ParametrosRegistroVacinacaoDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoRegistroVacinacaoDTO;
import io.schneider.carteira.vacinacao.domain.entity.RegistroVacinacaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegistroVacinacaoMapper {

    RetornoRegistroVacinacaoDTO paraDTO(final RegistroVacinacaoEntity entity);

    @Mapping(source = "pessoaId", target = "pessoa.id")
    @Mapping(source = "vacinaId", target = "vacina.id")
    RegistroVacinacaoEntity paraEntity(final ParametrosRegistroVacinacaoDTO dto);

}
