package conocimiento.cultural.service;

import java.util.List;
import conocimiento.cultural.repository.ImagenesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conocimiento.cultural.entity.Imagenes;
@Service
public class ImagenesServiceImpl implements ImagenesService {//implementamos nuestras operaciones
	@Autowired
    private ImagenesRepository imagenesRepository;//inyeccion de dependencias hacia el repositorio
 
	@Override
	public List<Imagenes> listarTodos() {
		
		return (List<Imagenes>) imagenesRepository.findAll();
	}

	@Override
	public void guardar(Imagenes imagenes) {
		imagenesRepository.save(imagenes);

	}

	@Override
	public Imagenes buscarPorId(Long id) {//buscar por id 
		
		return imagenesRepository.findById(id).orElse(null);//si hay error retorna nulo
	}

	@Override
	public void eliminar(Long id) {
		imagenesRepository.deleteById(id);

	}

}
