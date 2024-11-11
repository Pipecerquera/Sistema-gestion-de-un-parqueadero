package com.AuthNode.auth.Service;


import java.util.List;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.AuthNode.auth.Entity.PersonaEntity;
import com.AuthNode.auth.IRepository.IPersonaRepository;
import com.AuthNode.auth.IService.IPersonaService;

@Service
public class PersonaService implements IPersonaService, UserDetailsService {

    @Autowired
    public IPersonaRepository personaRepository;

	@Override
	public List<PersonaEntity> getAll() throws Exception {
		return personaRepository.findAll();
	}
	
	@Override
	public Page<PersonaEntity> getAllPageable(Pageable pageable) throws Exception {
		return personaRepository.findAll(pageable);
	}

	@Override
	public PersonaEntity findById(int id) throws Exception {
		PersonaEntity login = personaRepository.findById(id).orElse(null);
		return login;
	}

	@Override
	public PersonaEntity save(PersonaEntity login) {	
		return personaRepository.save(login);
	}

	@Override
	public Optional<PersonaEntity> findByCorreo(String email) {
        return personaRepository.findByEmail(email);
    }
	
	@Override
	public void delete(int id) throws Exception {
		personaRepository.deleteById(id);
	}

	@Override
	public void update(int id, PersonaEntity login) throws Exception {
		
        ZonedDateTime zonedDateTimeInColombia = ZonedDateTime.now(ZoneId.of("America/Bogota"));
        LocalDateTime localDateTimeInColombia = zonedDateTimeInColombia.toLocalDateTime();

		PersonaEntity user = personaRepository.findById(id)
	            .orElseThrow(() -> new Exception("No se encontró el historial con el ID proporcionado"));

		user.setPhoto(login.getPhoto());
		user.setFullname(login.getFullname());
		user.setPassword(login.getPassword());
		user.setEmail(login.getEmail());
		user.setRole(login.getRole());
		user.setStatus(login.getStatus());
		user.setNotifications(login.getNotifications());
        user.setUpdated_at(localDateTimeInColombia);
		personaRepository.save(user);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		PersonaEntity personaEntity = this.personaRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User "+ username + "not found"));
		return User.builder()
				.username(personaEntity.getEmail())
				.password(personaEntity.getPassword())
				.roles(personaEntity.getRole())
				.build();
	}
}
