package br.memory.project.lucas.gerenciador_de_funcionarios.utils;

import org.springframework.stereotype.Repository;

@Repository
public interface IRepositoryMethods<T, ID> {
    T save(T entity);
    T update(T entity);
    void delete(ID id);
    T findById(ID id);
    T findByYear(String dataAdmissao);
    Iterable<T> findAll();
}
