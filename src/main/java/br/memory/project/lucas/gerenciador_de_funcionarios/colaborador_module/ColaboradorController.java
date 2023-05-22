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
        return "redirect:/colaborador/cadastro";
    }

    @GetMapping("/cadastro")
    public String exibirCadastroColaborador(Model model){
        model.addAttribute("colaborador", new Colaborador());
        return "colaborador-views/cadastro-colaborador";
    }

    @DeleteMapping("/delete/{id}")
    public String deletarColaborador(@PathVariable UUID id){
        colaboradorService.delete(id);
        return "colaborador-views/cadastro-colaborador";
    }

    @PutMapping("/update")
    public String atualizarColaborador(@RequestBody Colaborador colaborador){
        colaboradorService.update(colaborador);
        return "colaborador-views/cadastro-colaborador";
    }

    @GetMapping("/lista")
    public String exibirListaColaboradores(Model model) {
        List<Colaborador> colaboradores = (List<Colaborador>) colaboradorService.findAll();
        model.addAttribute("listaColaboradores", colaboradores);
        return "colaborador-views/listagem-colaboradores";
    }
}
