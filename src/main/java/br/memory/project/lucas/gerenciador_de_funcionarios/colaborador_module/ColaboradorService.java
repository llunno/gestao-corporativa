package br.memory.project.lucas.gerenciador_de_funcionarios.colaborador_module;

import br.memory.project.lucas.gerenciador_de_funcionarios.utils.IRepositoryMethods;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ColaboradorService implements IRepositoryMethods<Colaborador, Integer> {

    private final IColaboradorRepository iColaboradorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ColaboradorService(IColaboradorRepository iColaboradorRepository, ModelMapper modelMapper1) {
        this.iColaboradorRepository = iColaboradorRepository;
        this.modelMapper = modelMapper1;
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

    @Override
    public Collection<Colaborador> findByYear(Integer year) {
        return iColaboradorRepository.getByYear(year);
    }

    public String formatData(String data) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(DateTimeFormatter.ISO_LOCAL_DATE.parse(data));
    }

    public Collection<Colaborador> findAllByNivelHierarquico(String nivelHierarquico) {
        return iColaboradorRepository.findAllByNivelHierarquico(nivelHierarquico);
    }

    public Colaborador findByNivelHierarquico(String nivelHierarquico) {
        return iColaboradorRepository.findByNivelHierarquico(nivelHierarquico);
    }

    public String alternateMethodencontrarMediador(Integer id, Integer id2) throws JsonProcessingException {

        ArrayList<Colaborador> response = new ArrayList<>();
        Colaborador colaborador0 = iColaboradorRepository.findById(id).orElse(null);
        Colaborador colaborador1 = iColaboradorRepository.findById(id2).orElse(null);
        Colaborador presidente = iColaboradorRepository.findByNivelHierarquico("Presidente");

        HashMap<String,String> mapresponses = new HashMap<>();
        mapresponses.put("Message", "Erro ao encontrar colaborador");


        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());

        try {
            Objects.requireNonNull(colaborador0);
            Objects.requireNonNull(colaborador1);
        } catch (NullPointerException e) {
            return jsonMapper.writeValueAsString(List.of(mapresponses));
        }

        List<Integer> grausHierarquicos = new ArrayList<>();

        List<Colaborador> colaboradoresToAnalyse = Arrays.asList(colaborador0, colaborador1);
        for (int i = 0; i < colaboradoresToAnalyse.size(); i++) {
            Colaborador colaborador = colaboradoresToAnalyse.get(i);
            if (colaborador != null) {
                Colaborador superiorColaborador = colaborador.getSuperiorDireto();
                if (superiorColaborador != null) {
                    switch (superiorColaborador.getNivelHierarquico()) {
                        case "Presidente":
                            grausHierarquicos.add(1000);
                            break;
                        case "Gerente":
                            grausHierarquicos.add(500);
                            break;
                        case "Supervisor":
                            grausHierarquicos.add(250);
                            break;
                        case "Colaborador":
                            grausHierarquicos.add(125);
                            break;
                        default:
                            grausHierarquicos.add(0);
                            break;
                    }
                }
                else {
                    grausHierarquicos.add(0);
                }
            }
            else {
                response.add(presidente);
            }
        }

        if (grausHierarquicos.get(0) == 0 && grausHierarquicos.get(1) == 0) {

            return jsonMapper.writeValueAsString(List.of(mapresponses));
        }


        if (grausHierarquicos.get(0).equals(grausHierarquicos.get(1))) {

            if (colaborador0.getSuperiorDireto() == colaborador1.getSuperiorDireto()) {
                response.add(colaborador0.getSuperiorDireto());
            }
            else {
                response.add(colaborador0.getSuperiorDireto());
                response.add(colaborador1.getSuperiorDireto());
            }
        }
        else if (grausHierarquicos.get(0) > grausHierarquicos.get(1)) {
            response.add(colaborador0.getSuperiorDireto());
        }
        else {
            response.add(colaborador1.getSuperiorDireto());
        }

        ArrayList<ColaboradorDTO> superioresIndicadosDTO = new ArrayList<>();
        for (Colaborador superiorIndicado : response) {
            ColaboradorDTO superiorIndicadoDTO = modelMapper.colaboradorToColaboradorDTO(superiorIndicado);
            superioresIndicadosDTO.add(superiorIndicadoDTO);
        }

        try {
            return jsonMapper.writeValueAsString(superioresIndicadosDTO);
        } catch (JsonProcessingException e) {
            mapresponses.replace("Message", "Erro ao converter para JSON");
            return jsonMapper.writeValueAsString(List.of(mapresponses));
        }
    }

}
