package br.memory.project.lucas.gerenciador_de_funcionarios.colaborador_module;

import br.memory.project.lucas.gerenciador_de_funcionarios.utils.IRepositoryMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Service
public class ColaboradorService implements IRepositoryMethods<Colaborador, Integer> {

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
    public Colaborador update(Colaborador entity, Integer id) {

        Colaborador colaboradorInDB = iColaboradorRepository.getReferenceById(id);
        colaboradorInDB.setNome(entity.getNome());
        colaboradorInDB.setCpf(entity.getCpf());
        colaboradorInDB.setDataAdmissao(entity.getDataAdmissao());
        colaboradorInDB.setFuncaoExercida(entity.getFuncaoExercida());
        colaboradorInDB.setRemuneracao(entity.getRemuneracao());
        colaboradorInDB.setDepartamento(entity.getDepartamento());

        return iColaboradorRepository.save(colaboradorInDB);
    }

    @Override
    public void delete(Integer id) {
        iColaboradorRepository.deleteById(id);
    }

    @Override
    public Colaborador findById(Integer id) {
        return iColaboradorRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Colaborador> findAll() {
        return iColaboradorRepository.findAll();
    }

    public String formatData(String data) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(DateTimeFormatter.ISO_LOCAL_DATE.parse(data));
    }

    public Collection<Colaborador> findByNivelHierarquico(String nivelHierarquico) {
        return iColaboradorRepository.findAllByNivelHierarquico(nivelHierarquico);
    }

    @Override
    public Collection<Colaborador> findByYear(Integer year) {
        return iColaboradorRepository.getByYear(year);
    }
}
