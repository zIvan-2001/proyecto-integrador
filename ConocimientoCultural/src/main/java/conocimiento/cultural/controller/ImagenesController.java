package conocimiento.cultural.controller;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import conocimiento.cultural.entity.Imagenes;
import conocimiento.cultural.entity.Ciudad;
import conocimiento.cultural.service.CiudadService;
import conocimiento.cultural.service.ImagenesService;
@Controller
@RequestMapping("/views/imagenes")
public class ImagenesController {
	
	  @Autowired
	   private CiudadService ciudadService;
	 
		@Autowired
		private ImagenesService imagenesService;
	
@GetMapping("/")
public String listarImagenes(Model model) {
		//inyectar el metodo de la clase imagenservice 
		List<Imagenes> listadoImagenes =imagenesService.listarTodos();
		
		model.addAttribute("titulo", "Lista de Imagenes");
		model.addAttribute("imagenes", listadoImagenes);
		return"/views/imagenes/listar";
	}

	
	@GetMapping("/create")
	public String crear(Model model) {
		
		Imagenes imagenes = new Imagenes ();
		List<Ciudad> listCiudades = ciudadService.listaCiudades();
		
		model.addAttribute("titulo", "Formulario: Nueva Imagen");
		model.addAttribute("imagenes", imagenes);
		model.addAttribute("ciudades", listCiudades);
		
		return "/views/imagenes/formCrear";
	}
	@PostMapping("/guardar")
	public String guardar(@Valid @ModelAttribute Imagenes imagenes, BindingResult result,
			Model model,@RequestParam("file") MultipartFile foto, RedirectAttributes attribute) throws IOException {
		List<Ciudad> listCiudades = ciudadService.listaCiudades();

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario: Nuevo Imagen");
			model.addAttribute("imagenes", imagenes);
			model.addAttribute("ciudades", listCiudades);
			System.out.println("Existieron errores en el formulario");			
			return "/views/imagenes/formCrear";
		}
		Path uploadPath = Paths.get("./provincias");

        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try {
            InputStream inputStream = foto.getInputStream();
            Path filePath = uploadPath.resolve(foto.getOriginalFilename());
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioException){
            throw new IOException("Could not save upload file gaaaa: ");
        }
		//if (!foto.isEmpty()) {
		//	Path directorioimagenes = Paths.get("./destinos");
		//	String rutaAbsoluta =directorioimagenes.toFile().getAbsolutePath();
			
		//	try {byte[] bytesImg = foto.getBytes();
			///	Path rutaCompleta = Paths.get(rutaAbsoluta + "//"+ foto.getOriginalFilename());
			
			//	System.out.println("ruta: "+rutaCompleta);
			//	Files.write(rutaCompleta, bytesImg);
			//	imagenes.setFoto(foto.getOriginalFilename());
		//	} catch (Exception e) {
				
			//	e.printStackTrace();
		//	}
	//	}

        imagenes.setFoto(foto.getOriginalFilename());
		imagenesService.guardar(imagenes);
		System.out.println("imagen guardado con exito!");
		attribute.addFlashAttribute("success", "imagen guardado con exito!");
		return "redirect:/views/imagenes/";
	}
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Long idImagenes, Model model, RedirectAttributes attribute) {
			
		Imagenes imagenes = null;
		
		if (idImagenes > 0) {
			imagenes = imagenesService.buscarPorId(idImagenes);
			
			if (imagenes == null) {
				System.out.println("Error: El ID de la imagen no existe!");
				attribute.addFlashAttribute("error", "ATENCION: El ID de la imagen no existe!");
				return "redirect:/views/imagenes/";
			}
		}else {
			System.out.println("Error: Error con el ID de la imagen");
			attribute.addFlashAttribute("error", "ATENCION: Error con el ID de la imagen");
			return "redirect:/views/imagenes/";
		}
		
		List<Ciudad> listCiudades = ciudadService.listaCiudades();

		model.addAttribute("titulo", "Formulario: Editar imagen");
		model.addAttribute("imagenes", imagenes);
		model.addAttribute("ciudades", listCiudades);

		return "/views/imagenes/formCrear";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idImagenes, RedirectAttributes attribute) {

		Imagenes imagenes = null;
		
		if (idImagenes > 0) {
			imagenes = imagenesService.buscarPorId(idImagenes);
			
			if (imagenes == null) {
				System.out.println("Error: El ID de la imagen no existe!");
				attribute.addFlashAttribute("error", "ATENCION: El ID de la imagen no existe!");
				return "redirect:/views/imagenes/";
			}
		}else {
			System.out.println("Error: Error con el ID de la imagen");
			attribute.addFlashAttribute("error", "ATENCION: Error con el ID de la imagen!");
			return "redirect:/views/imagenes/";
		}		
		
		imagenesService.eliminar(idImagenes);
		System.out.println("Registro Eliminado con Exito!");
		attribute.addFlashAttribute("warning", "Registro Eliminado con Exito!");

		return "redirect:/views/imagenes/";
	}

}
