package conocimiento.cultural.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.joran.conditional.ThenOrElseActionBase;
import conocimiento.cultural.dto.ChangePasswordForm;
import conocimiento.cultural.entity.User;
import conocimiento.cultural.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository repository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Iterable<User> getAllUsers(){
		return repository.findAll();
	}
	private boolean checkUsernamevailable(User user) throws Exception {
		Optional<User> userFound = repository.findByUsername(user.getUsername());
		if (userFound.isPresent()) {
			throw new Exception("Usuario no disponible");
		}
		return true;
	}

	private boolean checkPasswordValid(User user) throws Exception {
		if (user.getConfirmPassword()== null || user.getConfirmPassword().isEmpty()) {
			throw new Exception("la confirmancion del password es obligatorio");
		}
		
		if ( !user.getPassword().equals(user.getConfirmPassword())) {
			throw new Exception("Password y Confirmacion de Password no son iguales");
		}
		return true;
	}
	@Override
	public User createUser(User user) throws Exception{
		if (checkUsernamevailable(user) && checkPasswordValid(user)) {
			user = repository.save(user);
		
	}
		return user;
	}
	@Override
	public User getUserById(Long id) throws Exception {
		return repository.findById(id).orElseThrow(() -> new Exception("El usuario no existe."));
	}
	@Override
	public User updateUser(User fromuser) throws Exception {
		User toUser = getUserById(fromuser.getId());
		mapUser(fromuser,toUser);
		return repository.save(toUser);
	}
	
	protected void mapUser(User from,User to) {
		to.setUsername(from.getUsername());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
		
	}
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteUser(Long id) throws Exception {
		User user = getUserById(id);
		repository.delete(user);
		
	}
	@Override
	public User changePassword(ChangePasswordForm form) throws Exception {
		
		User user = getUserById(form.getId());
				
		if ( !isLoggedUserADMIN() && !user.getPassword().equals(form.getCurrentPassword())) {
			throw new Exception("Password actual incorrecto.");
		}
		
		if( !user.getPassword().equals(form.getCurrentPassword())) {
			throw new Exception("¡La nueva contraseña debe ser diferente a la contraseña actual!");
		}
		
		if( !form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception("El nuevo Password y la Confirmacion Password no coinciden !");
		}
	
		String encodePassword = bCryptPasswordEncoder.encode(form.getNewPassword());
		user.setPassword(encodePassword);
		return repository.save(user);
		
	}
	private boolean isLoggedUserADMIN() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = null;
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;

			loggedUser.getAuthorities().stream()
					.filter(x -> "ADMIN".equals(x.getAuthority() ))      
					.findFirst().orElse(null); //loggedUser = null;
		}
		return loggedUser != null ?true :false;
	}
		
			
		}



