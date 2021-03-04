package com.bolsadeideas.springboot.diabeticdiary.app.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.Role;
import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.Usuario;
import com.bolsadeideas.springboot.diabeticdiary.app.models.service.IUsuarioService;

@Controller
public class LoginController {

	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, 
			Principal principal, Model model, RedirectAttributes flash) {
		
		model.addAttribute("counterNumber", this.usuarioService.getAccountsNumber().toString());
		
		if (principal != null) {
			flash.addFlashAttribute("info", principal.getName() + ", ya tienes una sesion iniciada!");
			return "redirect:/home";
		}
		
		if(error != null) {
			model.addAttribute("error", "Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo!");
		}
		
		if(logout != null) {
			model.addAttribute("success", "Has cerrado sesion con exito!");
		}

		return "login";
	}

	@GetMapping("/sign-up")
	public String signUp(Model model) {
		
		model.addAttribute("counterNumber", this.usuarioService.getAccountsNumber().toString());

		Usuario usuario = new Usuario();

		model.addAttribute("usuario", usuario);

		return "sign-up";
	}

	@PostMapping("/sign-up")
	public String signUp(@Valid Usuario usuario, BindingResult result,
			RedirectAttributes flash) {

		usuario.setUsername(usuario.getUsername().toLowerCase());
		
		if (this.usuarioService.findByUsername(usuario.getUsername()) != null) {
			result.addError(new FieldError("username", "username", "El nombre de usuario '" + usuario.getUsername() + "' ya se encuentra en uso !"));
			return "sign-up";
		}

		if (result.hasErrors()) {
			return "sign-up";
		}
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role("ROLE_USER"));
		usuario.setRoles(roles);
		this.usuarioService.save(usuario);
		flash.addFlashAttribute("success", "Cuenta creada con exito!");
		return "redirect:/login";
	}

}
