package br.memory.project.lucas.gerenciador_de_funcionarios.colaborador_module;

import br.memory.project.lucas.gerenciador_de_funcionarios.funcionario_module.Funcionario;
import br.memory.project.lucas.gerenciador_de_funcionarios.gerente_module.Gerente;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.Collection;

@Entity
public class Colaborador extends Funcionario {
    @ManyToOne(cascade = CascadeType.DETACH)
    private Gerente gerente;
    @OneToMany
    private Collection<Colaborador> subordinados;

    public Colaborador(){}

    public Colaborador(Gerente gerente, Collection<Colaborador> subordinados) {
        this.gerente = gerente;
        this.subordinados = subordinados;
    }

    public Gerente getGerente() {
        return gerente;
    }

    public void setGerente(Gerente gerente) {
        this.gerente = gerente;
    }

    public Collection<Colaborador> getSubordinados() {
        return subordinados;
    }

    public void setSubordinados(Collection<Colaborador> subordinados) {
        this.subordinados = subordinados;
    }
}
