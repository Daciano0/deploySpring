package com.mballem.curso.boot.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mballem.curso.boot.domain.Departamento;
import com.mballem.curso.boot.service.DepartamentoService;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

	@Autowired
	private DepartamentoService service;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Departamento departamento) {
		
		 ModelMap model = new ModelMap();
		
		 System.out.println("Chamando metodo");
		 
		model.addAttribute("departamento",null);
		
		return "departamento/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("departamentos",service.buscarTodos());
		return "departamento/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Departamento departamento,  BindingResult result , RedirectAttributes attr) {
		
		if(result.hasErrors()) {
			return "departamento/cadastro";
		}
		
		
		service.salvar(departamento);
		attr.addFlashAttribute("success","Departamento inserido com successo.");
		System.out.println("Sucesso! "+departamento.getId());
		return "redirect:/departamentos/cadastrar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("departamento", service.buscarPorId(id));
		System.out.println("ID "+id);
		return "departamento/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Departamento departamento,  BindingResult result , RedirectAttributes attr) {
		
		if(result.hasErrors()) {
			return "departamento/cadastro";
		}
		
		
		service.editar(departamento);
		attr.addFlashAttribute("success","Departamento editado com successo.");
		System.out.println("Editado! "+departamento.getId());
		return "redirect:/departamentos/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		
		if(service.departamentoTemCargos(id)) {
			model.addAttribute("fail","Departamento não removido. Possui cargos(s) vinvulado(s).");	
		}else {
			service.excluir(id);
			model.addAttribute("success","Departamento Excluído com sucesso!");	

		}
		
		return listar(model);
	}
	
}
