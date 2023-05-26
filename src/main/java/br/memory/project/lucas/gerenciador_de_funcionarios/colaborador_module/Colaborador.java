package br.memory.project.lucas.gerenciador_de_funcionarios.colaborador_module;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Colaborador {
    @Id
    @GenericGenerator(
            name = "funcionario_sequence",
            parameters = {
                    @Parameter(name = "sequence_name", value = "funcionario_sequence"),
                    @Parameter(name = "initial_value", value = "1000"),
                    @Parameter(name = "increment_size", value = "1")
            })
    @GeneratedValue(generator = "funcionario_sequence", strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String nome;
    private String cpf;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAdmissao;
    private String funcaoExercida;
    private Double remuneracao;
    private String departamento;
    private String nivelHierarquico;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Colaborador superiorDireto;
    @OneToMany(mappedBy = "superiorDireto", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Collection<Colaborador> subordinados;

    public Colaborador(){}

    public Integer getId() {
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

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public String getFuncaoExercida() {
        return funcaoExercida;
    }

    public void setFuncaoExercida(String funcaoExercida) {
        this.funcaoExercida = funcaoExercida;
    }

    public Double getRemuneracao() {
        return remuneracao;
    }

    public void setRemuneracao(Double remuneracao) {
        this.remuneracao = remuneracao;
    }

    public LocalDate convertDataAdmissao(String dataAdmissao){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dataAdmissao, formatter);
    }

    @Override
    public String toString() {
        return "Colaborador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", dataAdmissao=" + dataAdmissao +
                ", funcaoExercida='" + funcaoExercida + '\'' +
                ", remuneracao=" + remuneracao +
                ", nivelHierarquico='" + nivelHierarquico + '\'' +
                ", superiorDireto=" + superiorDireto +
                '}';
    }

    public String getNivelHierarquico() {
        return nivelHierarquico;
    }

    public void setNivelHierarquico(String nivelHierarquico) {
        this.nivelHierarquico = nivelHierarquico;
    }

    public Colaborador getSuperiorDireto() {
        return superiorDireto;
    }

    public void setSuperiorDireto(Colaborador superiorDireto) {
        this.superiorDireto = superiorDireto;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Collection<Colaborador> getSubordinados() {
        return subordinados;
    }

    public void setSubordinados(Collection<Colaborador> subordinados) {
        this.subordinados = subordinados;
    }
}
