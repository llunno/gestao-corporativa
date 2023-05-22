package br.memory.project.lucas.gerenciador_de_funcionarios.gerente_module;

import br.memory.project.lucas.gerenciador_de_funcionarios.colaborador_module.Colaborador;
import br.memory.project.lucas.gerenciador_de_funcionarios.funcionario_module.Funcionario;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.Collection;

@Entity
public class Gerente extends Funcionario {
    @OneToMany(mappedBy = "gerente")
    private Collection<Colaborador> colaboradores;

    public Gerente(){}

    public Gerente(Collection<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }

    public Collection<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(Collection<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }
}
