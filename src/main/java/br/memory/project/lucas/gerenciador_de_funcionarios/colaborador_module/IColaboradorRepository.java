package br.memory.project.lucas.gerenciador_de_funcionarios.colaborador_module;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface IColaboradorRepository extends JpaRepository<Colaborador, Integer> {

    Collection<Colaborador> findAllByNivelHierarquico(String nivelHierarquico);
    Colaborador findByNivelHierarquico(String nivelHierarquico);
    @Query("from Colaborador c where year (c.dataAdmissao) = :ano")
    Collection<Colaborador> getByYear(int ano);
    Collection<Colaborador> findAllByOrderByIdDesc();
    @Query("from Colaborador c where year (c.dataAdmissao) = :ano")
    Page<Colaborador> getByYearFiltered(int ano, Pageable pageable);
    Page<Colaborador> findAllByOrderByIdDesc(Pageable pageable);
}
