package br.memory.project.lucas.gerenciador_de_funcionarios.gerente_module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IGerenteRepository extends JpaRepository<Gerente, UUID> {

    @Query("SELECT g FROM Gerente g WHERE g.dataAdmissao = :dataAdmissao")
    Gerente findByYear(String dataAdmissao);
}
