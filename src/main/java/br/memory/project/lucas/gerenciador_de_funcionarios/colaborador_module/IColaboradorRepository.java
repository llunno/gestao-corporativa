package br.memory.project.lucas.gerenciador_de_funcionarios.colaborador_module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IColaboradorRepository extends JpaRepository<Colaborador, UUID> {

    @Query("SELECT c FROM Colaborador c WHERE c.dataAdmissao = :dataAdmissao")
    Colaborador findByYear(String dataAdmissao);

}
