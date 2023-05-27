package br.memory.project.lucas.gerenciador_de_funcionarios.colaborador_module;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColaboradorDTO {

    private Integer id;
    private String nome;
    private String cpf;
    private String funcaoExercida;
    private String nivelHierarquico;

}