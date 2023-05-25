package br.memory.project.lucas.gerenciador_de_funcionarios.colaborador_module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        model.addAttribute("colaboradores", colaboradorService.findByNivelHierarquico("Colaborador"));
        model.addAttribute("gerentes", colaboradorService.findByNivelHierarquico("Gerente"));
        model.addAttribute("supervisores", colaboradorService.findByNivelHierarquico("Supervisor"));
        model.addAttribute("isUpdate", false);
        return "colaborador-views/cadastro";
    }

    @GetMapping("/cadastro/{id}")
    public String exibirTelaUpdateColaborador(@PathVariable Integer id, Model model) {
        Colaborador colaborador = colaboradorService.findById(id);
        model.addAttribute("colaborador", colaborador);
        model.addAttribute("colaboradores", colaboradorService.findByNivelHierarquico("Colaborador"));
        model.addAttribute("gerentes", colaboradorService.findByNivelHierarquico("Gerente"));
        model.addAttribute("supervisor", colaboradorService.findByNivelHierarquico("Supervisor"));
        model.addAttribute("isUpdate", true);
        return "colaborador-views/cadastro";
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
        List<Colaborador> colaboradores = (List<Colaborador>) colaboradorService.findAll();
        model.addAttribute("listaColaboradores", colaboradores);
        return "colaborador-views/listagem-colaboradores";
    }

    @PostMapping("/lista")
    public String exibirListaColaboradoresPorAno(Model model, String ano) {
        List<Colaborador> colaboradores = (List<Colaborador>) colaboradorService.findByYear(Integer.valueOf(ano));
        model.addAttribute("listaColaboradores", colaboradores);
        return "colaborador-views/listagem-colaboradores";
    }
}
