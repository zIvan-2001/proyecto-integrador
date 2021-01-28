package conocimiento.cultural.service;
import java.util.List;

import conocimiento.cultural.entity.Imagenes;
public interface ImagenesService {//las operaciones q usaremos 
	public List<Imagenes> listarTodos();//listar imagenes
	public void guardar(Imagenes imagenes);//guardar
	public Imagenes buscarPorId(Long id);//buscar por id
	public  void eliminar(Long id);//eliminar por id
}
