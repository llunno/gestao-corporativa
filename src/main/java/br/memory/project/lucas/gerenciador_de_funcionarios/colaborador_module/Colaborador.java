package br.memory.project.lucas.gerenciador_de_funcionarios.colaborador_module;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Locale;

@Setter
@Getter
@NoArgsConstructor
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
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Colaborador superiorDireto;
    @OneToMany(mappedBy = "superiorDireto", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Collection<Colaborador> subordinados;

    public String formattedDataAdmissao(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.dataAdmissao.format(formatter);
    }

    public String formattedRemuneracao() {
        try{
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
            return nf.format(this.remuneracao);
        } catch (NullPointerException e) {
            Locale.Builder localeBuilder = new Locale.Builder();
            localeBuilder.setLanguage("pt");
            localeBuilder.setRegion("BR");
            NumberFormat nf = NumberFormat.getCurrencyInstance(localeBuilder.build());
            return nf.format(this.remuneracao);
        }
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
                ", departamento='" + departamento + '\'' +
                ", nivelHierarquico='" + nivelHierarquico + '\'' +
                ", superiorDireto=" + (superiorDireto == null ? null : superiorDireto.getNome()) +
                ", subordinados=" + (subordinados == null ? null : subordinados.size()) +
                '}';
    }
}
