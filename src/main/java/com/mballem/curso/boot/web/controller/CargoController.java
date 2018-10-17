package com.mballem.curso.boot.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mballem.curso.boot.domain.Cargo;
import com.mballem.curso.boot.domain.Departamento;
import com.mballem.curso.boot.service.CargoService;
import com.mballem.curso.boot.service.DepartamentoService;

@Controller
@RequestMapping("/cargos")
public class CargoController {

	@Autowired
	private CargoService cargoService;
	
	@Autowired
	private DepartamentoService departamentoService;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Cargo cargo) {
		return "cargo/cadastro";
	}
	
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		System.out.println("Passou cargosTodos");
		model.addAttribute("cargos",cargoService.buscarTodos());
		return "cargo/lista";
	}
	
	
	@PostMapping("/salvar")
	public String salvar(@Valid Cargo cargo, BindingResult result ,RedirectAttributes attr) {
		
		if(result.hasErrors()) {
			return "cargo/cadastro";
		}
		
		cargoService.salvar(cargo);
		attr.addFlashAttribute("success","Inserido com sucesso!");
		return "redirect:/cargos/cadastrar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("cargo", cargoService.buscarPorId(id));
		System.out.println("ID "+id);
		return "cargo/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Cargo cargo, BindingResult result, RedirectAttributes attr) {
		
		if(result.hasErrors()) {
			return "cargo/cadastro";
		}
		
		cargoService.editar(cargo);
		attr.addFlashAttribute("success","Cargo editado com successo.");
		return "redirect:/cargos/cadastrar";
	}
	
	//@GetMapping("/excluir/{id}")
	@GetMapping("/delete/{id}")
	//@RequestMapping(path="/delete/{id}", method=RequestMethod.GET)
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		
		if(cargoService.CargoTemFuncionario(id)) {
			//model.addAttribute("fail","Cargo não excluido. Tem funcionário(s) vinculado(s).");
			attr.addFlashAttribute("fail","Cargo não removido. Possui cargos(s) vinculado(s).");	
			
		}else {
			cargoService.excluir(id);
			//model.addAttribute("success","Cargo não excluido. Tem funcionário(s) vinculado(s).");
			attr.addFlashAttribute("success","Cargo Excluído com sucesso!");	
		}
		
		return "redirect:/cargos/listar";
	}
	
	
	@ModelAttribute("departamentos")
	public List<Departamento> listaDeDepartamentos(){
		return departamentoService.buscarTodos();
	}
	
	
}
