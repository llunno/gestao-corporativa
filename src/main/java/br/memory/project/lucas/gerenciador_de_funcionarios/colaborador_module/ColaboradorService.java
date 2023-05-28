package br.memory.project.lucas.gerenciador_de_funcionarios.colaborador_module;

import br.memory.project.lucas.gerenciador_de_funcionarios.utils.IRepositoryMethods;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        if (entity.getNivelHierarquico().equals("Gerente") || entity.getSuperiorDireto() == null) {
            entity.setSuperiorDireto(iColaboradorRepository.findByNivelHierarquico("Presidente"));
        }

        if (entity.getSubordinados() != null) {
            entity.getSubordinados().forEach(subordinado -> {
                subordinado.setSuperiorDireto(entity);
            });
        }
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
        colaboradorInDB.setNivelHierarquico(entity.getNivelHierarquico());

        if (entity.getNivelHierarquico().equals("Gerente") || entity.getSuperiorDireto() == null) {
            entity.setSuperiorDireto(iColaboradorRepository.findByNivelHierarquico("Presidente"));
        }

        if (entity.getSubordinados() != null && entity.getSubordinados().size() != 0) {

            colaboradorInDB.getSubordinados().forEach(subordinado -> {
                subordinado.setSuperiorDireto(iColaboradorRepository.findByNivelHierarquico("Presidente"));
            });

            entity.getSubordinados().forEach(subordinado -> {
                subordinado.setSuperiorDireto(colaboradorInDB);
            });
        } else {
            colaboradorInDB.getSubordinados().forEach(subordinado -> {
                subordinado.setSuperiorDireto(iColaboradorRepository.findByNivelHierarquico("Presidente"));
            });
        }


        colaboradorInDB.setSuperiorDireto(entity.getSuperiorDireto());
        colaboradorInDB.setSubordinados(entity.getSubordinados());

        return iColaboradorRepository.save(colaboradorInDB);
    }

    @Override
    public void delete(Integer id) {

        iColaboradorRepository.findById(id).ifPresent(colaborador ->
            colaborador.getSubordinados().forEach(subordinado -> {
            subordinado.setSuperiorDireto(iColaboradorRepository.findByNivelHierarquico("Presidente"));
        }));
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

    public Collection<Colaborador> findAllByIdOrdered() {
        return iColaboradorRepository.findAllByOrderByIdDesc();
    }

    public Collection<Colaborador> findAllByNivelHierarquico(String nivelHierarquico) {
        return iColaboradorRepository.findAllByNivelHierarquico(nivelHierarquico);
    }

    public Colaborador findByNivelHierarquico(String nivelHierarquico) {
        return iColaboradorRepository.findByNivelHierarquico(nivelHierarquico);
    }

    public String encontrarMediador(Integer id, Integer id2) throws JsonProcessingException {

        ArrayList<Colaborador> response = new ArrayList<>();
        ArrayList<ColaboradorDTO> superioresIndicadosDTO = new ArrayList<>();

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

        if (colaborador0.getNivelHierarquico().equals("Presidente")
                || colaborador1.getNivelHierarquico().equals("Presidente")) {

            ColaboradorDTO presidenteDTO = modelMapper.colaboradorToColaboradorDTO(presidente);
            superioresIndicadosDTO.add(presidenteDTO);
            return jsonMapper.writeValueAsString(superioresIndicadosDTO);
        }

        List<Integer> grausHierarquicos = new ArrayList<>();

        List<Colaborador> colaboradoresToAnalyse = Arrays.asList(colaborador0, colaborador1);
        for (int i = 0; i < colaboradoresToAnalyse.size(); i++) {
            Colaborador colaborador = colaboradoresToAnalyse.get(i);
            if (colaborador != null) {
                Colaborador superiorColaborador = colaborador.getSuperiorDireto();
                if (superiorColaborador != null) {
                    switch (superiorColaborador.getNivelHierarquico()) {
                        case "Presidente" -> grausHierarquicos.add(1000);
                        case "Gerente" -> grausHierarquicos.add(500);
                        case "Supervisor" -> grausHierarquicos.add(250);
                        case "Colaborador" -> grausHierarquicos.add(125);
                        default -> grausHierarquicos.add(0);
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
