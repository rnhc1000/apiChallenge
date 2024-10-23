package br.dev.ferreiras.challenge.config;

import br.dev.ferreiras.challenge.entity.Role;
import br.dev.ferreiras.challenge.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import br.dev.ferreiras.challenge.repository.RoleRepository;
import br.dev.ferreiras.challenge.repository.UserRepository;

import java.time.Instant;
import java.util.Set;
/**
 * Set an admin to generate tokens
 *
 * @author ricardo@ferreiras.dev.br
 * @version 1.1.10.23.01
 * @since 1.0
 *
 * Generate credentials for admin to generate tokens
 * @params none
 * @return save the admin credentials to database
 */

@Configuration
public class AdministratorConfiguration implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(AdministratorConfiguration.class);

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdministratorConfiguration(final RoleRepository roleRepository,
                                      final UserRepository userRepository,
                                      final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Override run method from CommandLineRunner interface to create a
     * user with Role:Admin if it already does not exist persisted into the database
     *
     * @throws Exception Database Exception
     */

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        final Role roleAdmin = this.roleRepository.findByRole(Role.Roles.ROLE_ADMIN.name());

        AdministratorConfiguration.logger.info("RoleAdmin:-> {}", roleAdmin);

        final var userAdmin = this.userRepository.findByUsername("admin@challenge.com");

        AdministratorConfiguration.logger.info("UserAdmin:-> {}", userAdmin);

        userAdmin.ifPresentOrElse(

                user -> AdministratorConfiguration.logger.info("Administrator already exists!"),
                () -> {

                    final var user = new User();

                    user.setUsername("admin@challenge.com");
                    user.setPassword(this.bCryptPasswordEncoder.encode("@ch4ll3ng3@"));
                    user.setRoles(Set.of(roleAdmin));
                    user.setCreatedAt(Instant.now());

                    this.userRepository.save(user);
                    AdministratorConfiguration.logger.info("Administrator created");
                }
        );
    }
}

