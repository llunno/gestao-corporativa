package br.memory.project.lucas.gerenciador_de_funcionarios.colaborador_module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/colaborador")
public class ColaboradorController {

    private final ColaboradorService colaboradorService;

    @Autowired
    public ColaboradorController(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;
    }

    @PostMapping("/save")
    public String save(Colaborador colaborador){
        colaboradorService.save(colaborador);
        return "redirect:/colaborador/lista";
    }

    @GetMapping("/cadastro")
    public String exibirCadastroColaborador(Model model){
        model.addAttribute("colaborador", new Colaborador());
        model.addAttribute("isUpdate", false);
        return "colaborador-views/cadastro-colaborador";
    }

    @GetMapping("/cadastro/{id}")
    public String exibirTelaUpdateColaborador(@PathVariable UUID id, Model model) {
        Colaborador colaborador = colaboradorService.findById(id);
        model.addAttribute("colaborador", colaborador);
        model.addAttribute("isUpdate", true);
        return "colaborador-views/cadastro-colaborador";
    }

    @ResponseBody
    @DeleteMapping("/delete/{id}")
    public void deletarColaborador(@PathVariable UUID id){
        colaboradorService.delete(id);
    }

    @PutMapping("/update/{id}")
    public String atualizarColaborador(@PathVariable UUID id,Colaborador colaborador){
        colaboradorService.update(colaborador, id);
        return "redirect:/colaborador/lista";
    }

    @GetMapping("/lista")
    public String exibirListaColaboradores(Model model) {
        List<Colaborador> colaboradores = (List<Colaborador>) colaboradorService.findAll();
        model.addAttribute("listaColaboradores", colaboradores);
        return "colaborador-views/listagem-colaboradores";
    }
}
