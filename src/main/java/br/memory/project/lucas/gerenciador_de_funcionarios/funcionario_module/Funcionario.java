package br.memory.project.lucas.gerenciador_de_funcionarios.funcionario_module;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Funcionario {
    @Id
    private UUID id = UUID.randomUUID();
    private String nome;
    private String cpf;
    private String dataAdmissao;
    private String funcaoExercida;
    private String remuneracao;

    public Funcionario(){}

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(String dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public String getFuncaoExercida() {
        return funcaoExercida;
    }

    public void setFuncaoExercida(String funcaoExercida) {
        this.funcaoExercida = funcaoExercida;
    }

    public String getRemuneracao() {
        return remuneracao;
    }

    public void setRemuneracao(String remuneracao) {
        this.remuneracao = remuneracao;
    }
}
