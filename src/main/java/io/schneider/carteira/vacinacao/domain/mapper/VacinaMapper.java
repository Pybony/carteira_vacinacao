package io.schneider.carteira.vacinacao.domain.mapper;

import io.schneider.carteira.vacinacao.controller.dto.ParametrosVacinaDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoVacinaDTO;
import io.schneider.carteira.vacinacao.domain.entity.VacinaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VacinaMapper {

    VacinaEntity paraEntity(final ParametrosVacinaDTO dto);

    RetornoVacinaDTO paraDTO(final VacinaEntity entity);
}
