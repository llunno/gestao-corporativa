package br.memory.project.lucas.gerenciador_de_funcionarios.colaborador_module;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModelMapper {
    ColaboradorDTO colaboradorToColaboradorDTO(Colaborador colaborador);
    Colaborador colaboradorDTOToColaborador(ColaboradorDTO colaboradorDTO);
}
