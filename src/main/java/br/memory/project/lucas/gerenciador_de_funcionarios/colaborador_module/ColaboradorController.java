package br.memory.project.lucas.gerenciador_de_funcionarios.colaborador_module;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/colaborador")
public class ColaboradorController {

    private final ColaboradorService colaboradorService;

    @Autowired
    public ColaboradorController(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;
    }

    @PostMapping("/save")
    public String save(Colaborador colaborador) {
        colaboradorService.save(colaborador);
        return "redirect:/colaborador/lista";
    }

    @GetMapping("/cadastro")
    public String exibirCadastroColaborador(Model model){
        model.addAttribute("colaborador", new Colaborador());
        model.addAttribute("funcionariosMedios", colaboradorService.findAllByNivelHierarquico("Colaborador"));
        model.addAttribute("gerentes", colaboradorService.findAllByNivelHierarquico("Gerente"));
        model.addAttribute("supervisores", colaboradorService.findAllByNivelHierarquico("Supervisor"));
        model.addAttribute("presidente", colaboradorService.findByNivelHierarquico("Presidente"));
        model.addAttribute("isUpdate", false);
        return "colaborador-views/cadastro";
    }

    @GetMapping("/cadastro/{id}")
    public String exibirTelaUpdateColaborador(@PathVariable Integer id, Model model) {
        Colaborador colaborador = colaboradorService.findById(id);
        model.addAttribute("colaborador", colaborador);
        model.addAttribute("funcionariosMedios", colaboradorService.findAllByNivelHierarquico("Colaborador"));
        model.addAttribute("gerentes", colaboradorService.findAllByNivelHierarquico("Gerente"));
        model.addAttribute("supervisores", colaboradorService.findAllByNivelHierarquico("Supervisor"));
        model.addAttribute("presidente", colaboradorService.findByNivelHierarquico("Presidente"));
        model.addAttribute("isUpdate", true);
        return "colaborador-views/update";
    }

    @ResponseBody
    @DeleteMapping("/delete/{id}")
    public void deletarColaborador(@PathVariable Integer id){

        colaboradorService.delete(id);
    }

    @PutMapping("/update/{id}")
    public String atualizarColaborador(@PathVariable Integer id,Colaborador colaborador){
        colaboradorService.update(colaborador, id);
        return "redirect:/colaborador/lista";
    }

    @GetMapping("/lista")
    public String exibirListaColaboradores(Model model) {
        try {
            return exibirListaColaboradoresPaginada(model,1);
        } catch (Exception e) {
            List<Colaborador> colaboradores = (List<Colaborador>) colaboradorService.findAllByIdOrdered();
            model.addAttribute("listaColaboradores", colaboradores);
            return "colaborador-views/listagem-colaboradores";
        }
    }

    @GetMapping("/lista/{pagina}")
    public String exibirListaColaboradoresPaginada(Model model, @PathVariable Integer pagina) {
        int size = 8;
        Page<Colaborador> paginaColaboradores = colaboradorService.findAllPaginated(pagina, size);
        model.addAttribute("listaColaboradores", paginaColaboradores.getContent());
        model.addAttribute("paginaAtual", pagina);
        model.addAttribute("totalPaginas", paginaColaboradores.getTotalPages());
        model.addAttribute("totalItens", paginaColaboradores.getTotalElements());
        return "colaborador-views/listagem-colaboradores";
    }

    @PostMapping("/lista")
    public String exibirListaColaboradoresPorAno(Model model, String ano) {
        if (ano == null || ano.isEmpty()) {
            return "redirect:/colaborador/lista";
        }
        Collection<Colaborador> colaboradores = colaboradorService.findByYear(Integer.valueOf(ano));
        model.addAttribute("listaColaboradores", colaboradores);
        model.addAttribute("isFilter", true);
        return "colaborador-views/listagem-colaboradores";
    }

    @GetMapping("/solucaodeconflitos")
    public String exibirSolucaoDeConflitos(Model model) {
        List<Colaborador> colaboradores = (List<Colaborador>) colaboradorService.findAllByIdOrdered();

        if (colaboradores.size() == 1) {
            return "colaborador-views/resolucao-conflitos";
        }
        else {
            colaboradores.removeIf(colaborador -> colaborador.getNivelHierarquico().equals("Presidente"));
            model.addAttribute("listaTodosColaboradores", colaboradores);
            return "colaborador-views/resolucao-conflitos";
        }
    }

    @ResponseBody
    @GetMapping("/solucaodeconflitos/solucao")
    public String solucionarConflitos(
            @RequestParam(name = "colaborador1", required = false) Integer id,
            @RequestParam(name = "colaborador2", required = false) Integer id2) throws JsonProcessingException {

        return colaboradorService.encontrarMediador(id, id2);
    }
}
