package io.schneider.carteira.vacinacao.domain.mapper;

import io.schneider.carteira.vacinacao.controller.dto.ParametrosPessoaDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoPessoaDTO;
import io.schneider.carteira.vacinacao.domain.entity.PessoaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    PessoaEntity paraEntity(ParametrosPessoaDTO pessoaDTO);

    RetornoPessoaDTO paraDTO(PessoaEntity pessoa);
}
