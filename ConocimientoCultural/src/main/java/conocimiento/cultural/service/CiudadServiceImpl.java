package conocimiento.cultural.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conocimiento.cultural.entity.Ciudad;
import conocimiento.cultural.repository.CiudadRepository;
@Service
public class CiudadServiceImpl implements CiudadService<Ciudad> {
	@Autowired
	private CiudadRepository ciudadRepository;
	@Override
	public List<Ciudad> listaCiudades() {
		
		return (List<Ciudad>) ciudadRepository.findAll();
	}

}
