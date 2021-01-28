package conocimiento.cultural.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import conocimiento.cultural.dto.ChangePasswordForm;
import conocimiento.cultural.entity.Imagenes;
import conocimiento.cultural.entity.User;
import conocimiento.cultural.repository.RoleRepository;
import conocimiento.cultural.repository.UserRepository;
import conocimiento.cultural.service.CiudadService;
import conocimiento.cultural.service.ImagenesService;
import conocimiento.cultural.service.UserService;


@Controller
public class UserController {
	 @Autowired
	   private CiudadService ciudadService;
	 
		@Autowired
		private ImagenesService imagenesService;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired 
	UserService userService;
	
	@GetMapping({"/login"})
	public String index() {
		return "index";
	}
	
	@GetMapping({"/"})
	public String inicio() {
		return "inicio";
	}
	@GetMapping("/destinos")
	public String getdestinos(Model model) {
		return "destinos";
	}
	@GetMapping("/destinos/ayabaca")
	public String getayabaca(Model model) {
		
		List<Imagenes> listadoImagenes =imagenesService.listarTodos();
		
		model.addAttribute("titulo", "Lista de Imagenes");
		model.addAttribute("imagenes", listadoImagenes);
		return"/destinos/ayabaca";
	
	}
	@GetMapping("/destinos/huancabamba")
	public String gethuancabamba(Model model) {
	List<Imagenes> listadoImagenes =imagenesService.listarTodos();
		
		model.addAttribute("titulo", "Lista de Imagenes");
		model.addAttribute("imagenes", listadoImagenes);
		
		return "destinos/huancabamba";
	}
	@GetMapping("/destinos/morropon")
	public String getmorropon(Model model) {
	List<Imagenes> listadoImagenes =imagenesService.listarTodos();
		
		model.addAttribute("titulo", "Lista de Imagenes");
		model.addAttribute("imagenes", listadoImagenes);
		
		return "destinos/morropon";
	}
	@GetMapping("/destinos/paita")
	public String getpaita(Model model) {
	List<Imagenes> listadoImagenes =imagenesService.listarTodos();
		
		model.addAttribute("titulo", "Lista de Imagenes");
		model.addAttribute("imagenes", listadoImagenes);
		
		return "destinos/paita";
	}
	@GetMapping("/destinos/piura")
	public String getpiura(Model model) {
	List<Imagenes> listadoImagenes =imagenesService.listarTodos();
		
		model.addAttribute("titulo", "Lista de Imagenes");
		model.addAttribute("imagenes", listadoImagenes);
		
		return "destinos/piura";
	}
	@GetMapping("/destinos/sechura")
	public String getsechura(Model model) {
	List<Imagenes> listadoImagenes =imagenesService.listarTodos();
		
		model.addAttribute("titulo", "Lista de Imagenes");
		model.addAttribute("imagenes", listadoImagenes);
		
		return "destinos/sechura";
	}
	@GetMapping("/destinos/talara")
	public String gettalara(Model model) {
	List<Imagenes> listadoImagenes =imagenesService.listarTodos();
		
		model.addAttribute("titulo", "Lista de Imagenes");
		model.addAttribute("imagenes", listadoImagenes);
	
		return "destinos/talara";
	}
	@GetMapping("/destinos/sullana")
	public String getsullana(Model model) {
	List<Imagenes> listadoImagenes =imagenesService.listarTodos();
		
		model.addAttribute("titulo", "Lista de Imagenes");
		model.addAttribute("imagenes", listadoImagenes);
		
		return "destinos/sullana";
	}
	
	@GetMapping("/userForm")
	public String userFom(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("roles",roleRepository.findAll());
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("listTab","active");
		return "user-view";
	}
	

	@PostMapping("/userForm")
	public String postUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
			if(result.hasErrors()) {
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
			}else {
				try {
					userService.createUser(user);
					model.addAttribute("userForm", new User());
					model.addAttribute("listTab","active");
					
				} catch (Exception e) {
					model.addAttribute("formErrorMessage",e.getMessage());
					model.addAttribute("userForm", user);
					model.addAttribute("formTab","active");
					model.addAttribute("userList", userService.getAllUsers());
					model.addAttribute("roles",roleRepository.findAll());
				}
			}
			model.addAttribute("userList", userService.getAllUsers());
			model.addAttribute("roles",roleRepository.findAll());
			return "user-view";
		}
	@GetMapping("/editUser/{id}")
	public String getEditUserForm(Model model, @PathVariable(name="id") Long id) throws Exception {
		User userToEdit = userService.getUserById(id);
		
		model.addAttribute("userForm", userToEdit);
		model.addAttribute("userList",userService.getAllUsers());
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("formTab","active");
		model.addAttribute("editMode","true");
		model.addAttribute("passwordForm",new ChangePasswordForm(id));
		
		return "/user-view";
	}
	@PostMapping("/editUser")
	public String postEditUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
			model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
		}else {
			try {
				userService.updateUser(user);
				model.addAttribute("userForm", new User());
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("roles",roleRepository.findAll());
				model.addAttribute("editMode","true");
				model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
			}
		}
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles",roleRepository.findAll());
		return "/user-view";
	}
	@GetMapping("/userForm/cancel")
	public String cancelEditUser(ModelMap model) {
		return "redirect:/userForm";
	}
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(Model model, @PathVariable(name="id") Long id){
		try {
			userService.deleteUser(id);
		} catch (Exception e) {
			model.addAttribute("listErrorMessage",e.getMessage());
		}
		return userFom(model);
	}
	@PostMapping("/editUser/changePassword")
	public ResponseEntity postEditUseChangePassword(@Valid @RequestBody ChangePasswordForm form, Errors errors) {
		try {
	        if (errors.hasErrors()) {
	            String result = errors.getAllErrors()
	                        .stream().map(x -> x.getDefaultMessage())
	                        .collect(Collectors.joining(""));

	            throw new Exception(result);
	        }
			userService.changePassword(form);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("success");
	}
	
}


