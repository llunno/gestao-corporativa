package br.memory.project.lucas.gerenciador_de_funcionarios.utils;

import br.memory.project.lucas.gerenciador_de_funcionarios.colaborador_module.Colaborador;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IRepositoryMethods<T, ID> {
    T save(T entity);
    Colaborador update(T entity, ID id);

    void delete(ID id);
    T findById(ID id);
    T findByYear(String dataAdmissao);
    Iterable<T> findAll();
}
