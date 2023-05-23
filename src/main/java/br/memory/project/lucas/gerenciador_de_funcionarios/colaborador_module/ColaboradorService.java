package br.memory.project.lucas.gerenciador_de_funcionarios.colaborador_module;

import br.memory.project.lucas.gerenciador_de_funcionarios.utils.IRepositoryMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ColaboradorService implements IRepositoryMethods<Colaborador, UUID> {

    private final IColaboradorRepository iColaboradorRepository;

    @Autowired
    public ColaboradorService(IColaboradorRepository iColaboradorRepository) {
        this.iColaboradorRepository = iColaboradorRepository;
    }

    @Override
    public Colaborador save(Colaborador entity) {
        return iColaboradorRepository.save(entity);
    }

    @Override
    public Colaborador update(Colaborador entity, UUID id) {
        Colaborador colaboradorInDB = iColaboradorRepository.getReferenceById(id);
        colaboradorInDB.setNome(entity.getNome());
        colaboradorInDB.setCpf(entity.getCpf());
        colaboradorInDB.setDataAdmissao(entity.getDataAdmissao());
        colaboradorInDB.setFuncaoExercida(entity.getFuncaoExercida());
        colaboradorInDB.setRemuneracao(entity.getRemuneracao());
        colaboradorInDB.setGerente(entity.getGerente());
        colaboradorInDB.setSubordinados(entity.getSubordinados());

        return iColaboradorRepository.save(colaboradorInDB);
    }

    @Override
    public void delete(UUID uuid) {
        iColaboradorRepository.deleteById(uuid);
    }


    @Override
    public Colaborador findById(UUID uuid) {
        return iColaboradorRepository.findById(uuid).orElse(null);
    }

    @Override
    public Colaborador findByYear(String year) {
        return iColaboradorRepository.findByYear(year);
    }

    @Override
    public Iterable<Colaborador> findAll() {
        return iColaboradorRepository.findAll();
    }
}
