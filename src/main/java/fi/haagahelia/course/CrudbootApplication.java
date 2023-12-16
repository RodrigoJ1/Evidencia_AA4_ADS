package fi.haagahelia.course;

import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.course.domain.Motivo;
import fi.haagahelia.course.domain.MotivoRepository;
import fi.haagahelia.course.domain.Citas;
import fi.haagahelia.course.domain.StudentRepository;
import fi.haagahelia.course.domain.User;
import fi.haagahelia.course.domain.UserRepository;

@SpringBootApplication
public class CrudbootApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CrudbootApplication.class, args);
	}
	
	/**
	 * Save demo users, courses and students to H2 DB
	 * @param repository
	 * @return
	 */
	@Bean
	public CommandLineRunner demo(StudentRepository repository, MotivoRepository mrepository, UserRepository urepository) {
		return (args) -> {
			// save students
			repository.save(new Citas("Juan", "Carrillo", "Medicina general", "juan@gmail.com")); 
			repository.save(new Citas("Raul", "Quispe", "Psicologia", "quispe@gmail.com"));
			repository.save(new Citas("Freddy", "Flores", "Medicina General", "Flores@gmail.com"));
			repository.save(new Citas("Pedro", "Yupanqui", "Nutricion","yunqui@gmail.com"));
			repository.save(new Citas("Diana", "Luque", "Pediatria","luq@gmail.com"));
			
			Stream.of("Programming Java", "Spring Boot basics", "Marketing 1", "Marketing 2").forEach(name -> {
				mrepository.save(new Motivo(name));
			});

			// Create users with BCrypt encoded password (user/user, admin/admin)
			User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			User user2 = new User("admin", "$2a$08$bCCcGjB03eulCWt3CY0AZew2rVzXFyouUolL5dkL/pBgFkUH9O4J2", "ADMIN");
			urepository.saveAll(Arrays.asList(user1, user2));
		};
	}
}
