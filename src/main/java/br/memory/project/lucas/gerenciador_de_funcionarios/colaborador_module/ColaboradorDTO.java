package br.memory.project.lucas.gerenciador_de_funcionarios.colaborador_module;

public class ColaboradorDTO {

    private Integer id;
    private String nome;
    private String cpf;
    private String funcaoExercida;
    private String nivelHierarquico;

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getFuncaoExercida() {
        return funcaoExercida;
    }

    public String getNivelHierarquico() {
        return nivelHierarquico;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setFuncaoExercida(String funcaoExercida) {
        this.funcaoExercida = funcaoExercida;
    }

    public void setNivelHierarquico(String nivelHierarquico) {
        this.nivelHierarquico = nivelHierarquico;
    }
}