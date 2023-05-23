package br.memory.project.lucas.gerenciador_de_funcionarios.funcionario_module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IFuncionarioRepository extends JpaRepository<Funcionario, UUID> {
    @Query("SELECT f FROM Funcionario f WHERE f.dataAdmissao = :dataAdmissao")
    Funcionario findByYear(String dataAdmissao);
}
