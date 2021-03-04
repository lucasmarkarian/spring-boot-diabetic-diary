package com.bolsadeideas.springboot.diabeticdiary.app.controllers;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.AccountId;
import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.ControlDiary;
import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.PasswordChange;
import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.Usuario;
import com.bolsadeideas.springboot.diabeticdiary.app.models.service.IControlDiaryService;
import com.bolsadeideas.springboot.diabeticdiary.app.models.service.IUsuarioService;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	IControlDiaryService controlDiaryService;
	
	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping(value = { "", "/", "/home" })
	public String home(Model model) {

		model.addAttribute("counterNumber", this.usuarioService.getAccountsNumber().toString());
		
		return "home2";
	}

	@GetMapping(value = "/form/{fullDate}")
	public String showDate(@PathVariable(value = "fullDate") String fullDate, Model model, RedirectAttributes flash)
			throws ParseException {
		
		model.addAttribute("counterNumber", this.usuarioService.getAccountsNumber().toString());

		String dateStr = fullDate;

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		Date date = new Date();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();

		try {
			date = simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			flash.addFlashAttribute("error", "Formato de fecha INVALIDO!");
			return "redirect:/home";
		}

		Date actualDate = Calendar.getInstance().getTime();

		if (date.compareTo(actualDate) <= 0) {
			ControlDiary controlDiary = this.controlDiaryService.findOne(new AccountId(username, date));

			if (controlDiary == null) {
				controlDiary = new ControlDiary();
				controlDiary.setDate(date);
			}

			simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			dateStr = simpleDateFormat.format(date);
			model.addAttribute("controlDiary", controlDiary);
			model.addAttribute("actDate", dateStr);
			model.addAttribute("origDate", fullDate);
			return "form";
		} else {
			flash.addFlashAttribute("error", "Fecha INVALIDA, superior a la fecha actual!");
			return "redirect:/home";
		}

	}

	@PostMapping(value = "/form")
	public String guardar(@Valid ControlDiary control, BindingResult result, Model model) {

		if (result.hasErrors()) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String dateStr = simpleDateFormat.format(control.getDate());
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String validDate = simpleDateFormat.format(control.getDate());
			model.addAttribute("actDate", dateStr);
			model.addAttribute("origDate", validDate);
			return "form";
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (control.hasInfo()) {

			control.setUsername_match(auth.getName());
			this.controlDiaryService.save(control);
		} else {
			AccountId account = new AccountId(auth.getName(), control.getDate());
			if (this.controlDiaryService.findOne(account) != null) {
				this.controlDiaryService.delete(account);
			}
		}
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		return "redirect:/form/" + simpleDateFormat.format(control.getDate());
	}

	@PostMapping(value = "/test")
	public String redirectControls(@RequestParam("desde") String desde, @RequestParam("hasta") String hasta,
			@RequestParam(value = "empty") Boolean empty, 
			RedirectAttributes flash) throws ParseException {
		
		String redirect = "redirect:test/".concat(desde).concat("/").concat(hasta).concat("?empty=");
		
		if (empty) {
			redirect = redirect.concat("true");
		} else {
			redirect = redirect.concat("false");
		}
		return redirect;
	}

	@GetMapping("test/{desde}/{hasta}")
	public String showControls(@PathVariable(value = "desde") String desde, @PathVariable(value = "hasta") String hasta,
			@RequestParam(value = "format", required = false) String format, @RequestParam(value = "empty") Boolean empty, 
			Model model, RedirectAttributes flash) throws ParseException {
		
		model.addAttribute("counterNumber", this.usuarioService.getAccountsNumber().toString());
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		Date desdeFormated = null;
		Date hastaFormated = null;
		try {
			desdeFormated = simpleDateFormat.parse(desde);
			hastaFormated = simpleDateFormat.parse(hasta);
			Date actualDate = Calendar.getInstance().getTime();

			if (desdeFormated.compareTo(actualDate) <= 0 && hastaFormated.compareTo(actualDate) <= 0) {
				int compareDate = hastaFormated.compareTo(desdeFormated);
				if (compareDate >= 0) {

					long diffInMillies = Math.abs(hastaFormated.getTime() - desdeFormated.getTime());
					long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
					if (diff <= 30) {
						simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
						String desdeTitulo = simpleDateFormat.format(desdeFormated);
						String hastaTitulo = simpleDateFormat.format(hastaFormated);
						List<ControlDiary> controles = new ArrayList<ControlDiary>();
						
						if (empty) {
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(desdeFormated);
							ControlDiary control = null;
							for (int i = 0; i <= diff; i++) {
								control = new ControlDiary();
								control.setDate(desdeFormated);
								controles.add(control);
								calendar.add(Calendar.DATE, 1);
								desdeFormated = calendar.getTime();
							}
						} else {
							Authentication auth = SecurityContextHolder.getContext().getAuthentication();
							controles = this.controlDiaryService
									.findByUsernameBetweenDates(auth.getName(), desdeFormated, hastaFormated);
							if (controles.isEmpty()) {
								flash.addFlashAttribute("warning", "Lo sentimos, no hay controles para las fechas dadas!");
								return "redirect:/home";
							}
						}
						model.addAttribute("empty", empty);
						model.addAttribute("desdeTitulo", desdeTitulo);
						model.addAttribute("hastaTitulo", hastaTitulo);
						model.addAttribute("desde", desde);
						model.addAttribute("hasta", hasta);
						model.addAttribute("controles", controles);
						return "test";
					} else {
						flash.addFlashAttribute("error",
								"Rango INVALIDO, la cantidad maxima de dias permitidos es 31 !");
					}
				} else {
					flash.addFlashAttribute("error", "Rango INVALIDO, fecha 'desde' superior a fecha 'hasta' !");
				}
			} else {
				flash.addFlashAttribute("error", "Rango INVALIDO, fecha 'desde' o fecha 'hasta' superior a la fecha actual !");
			}
		} catch (ParseException e) {
			flash.addFlashAttribute("error", "Formato de fecha incorrecto!");
		}

		return "redirect:/home";
	}
	
	@GetMapping("form/previous/{actDate}")
	public String anterior(@PathVariable(value = "actDate") String actDate) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date previousDate = simpleDateFormat.parse(actDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(previousDate);
		calendar.add(Calendar.DATE, -1);
		previousDate = calendar.getTime();
		
		String previousDateStr = simpleDateFormat.format(previousDate);
		
		return "redirect:/form/" + previousDateStr;
	}
	
	@GetMapping("form/next/{actDate}")
	public String siguiente(@PathVariable(value = "actDate") String actDate) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date nextDate = simpleDateFormat.parse(actDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nextDate);
		calendar.add(Calendar.DATE, 1);
		nextDate = calendar.getTime();
		
		String nextDateStr = simpleDateFormat.format(nextDate);
		
		return "redirect:/form/" + nextDateStr;
	}

	@GetMapping("/reiniciar/{actDate}")
	public String reiniciar(@PathVariable(value = "actDate") String actDate) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date deleteDate = simpleDateFormat.parse(actDate);
		AccountId account = new AccountId(auth.getName(), deleteDate);
		this.controlDiaryService.delete(account);
		return "redirect:/form/" + actDate;
	}

	@GetMapping(value = "/contact")
	public String contact(Model model) {
		model.addAttribute("counterNumber", this.usuarioService.getAccountsNumber().toString());
		return "contact";
	}
	
	@GetMapping(value = "/perfil")
	public String perfil(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = this.usuarioService.findByUsername(auth.getName());
		PasswordChange passwordChange = new PasswordChange();
		model.addAttribute("counterNumber", this.usuarioService.getAccountsNumber().toString());
		model.addAttribute("passwordChange", passwordChange);
		model.addAttribute("usuario", usuario);
		return "perfil";
	}
	
	@PostMapping(value = "/perfil")
	public String guardarPerfil(Usuario editedUser, RedirectAttributes flash) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = this.usuarioService.findByUsername(auth.getName());
		this.usuarioService.update(editedUser.getNombre(), editedUser.getApellido(), usuario.getId());
		flash.addFlashAttribute("success", "Datos guardados con exito!");
		return "redirect:/perfil";
	}
	
	@PostMapping(value = "/change-password")
	public String change_password(@Valid PasswordChange passwordChange, BindingResult result, 
			Model model, RedirectAttributes flash) {
		model.addAttribute("counterNumber", this.usuarioService.getAccountsNumber().toString());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = this.usuarioService.findByUsername(auth.getName());
		model.addAttribute("usuario", user);
		
		if (!this.passwordEncoder.matches(passwordChange.getOldPassword(), user.getPassword())) {
			result.addError(new FieldError("oldPassword", "oldPassword", "Contraseña incorrecta!"));
			return "perfil";
		}
		
		if (!passwordChange.getNewPassword().equals(passwordChange.getRepeatedNewPassword())) {
			result.addError(new FieldError("newPassword", "newPassword", "Las contraseñas no coinciden!"));
			result.addError(new FieldError("repeatedNewPassword", "repeatedNewPassword", "Las contraseñas no coinciden!"));
			return "perfil";
		}
		
		if (passwordChange.getOldPassword().equals(passwordChange.getNewPassword())) {
			result.addError(new FieldError("newPassword", "newPassword", "Debe ser diferente a la contraseña actual!"));
			return "perfil";
		}
		
		if (result.hasErrors()) {
			return "perfil";
		}
		
		user.setPassword(passwordChange.getNewPassword());
		this.usuarioService.save(user);
		flash.addFlashAttribute("success", "Contraseña modificada con exito!");
		return "redirect:/perfil";
	}

}
