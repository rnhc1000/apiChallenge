package br.dev.ferreiras.challenge.service;

import br.dev.ferreiras.challenge.contracts.IUserService;
import br.dev.ferreiras.challenge.entity.Role;
import br.dev.ferreiras.challenge.entity.User;
import br.dev.ferreiras.challenge.repository.RoleRepository;
import br.dev.ferreiras.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public UserService() {
    }

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * @param given username <example@example.com>
     * @return Optional<User>
     */
    @Override
    public Optional<User> getUsername(String username) {

        return this.userRepository.findByUsername(username);
    }

    @Override
    public Role getRole() {

        return this.roleRepository.findByRole(Role.Roles.ROLE_USER.name());
    }

    public List<String> checkSystem() {
        final String javaVersion = System.getProperty("java_version");
        final int numberOfCores = Runtime.getRuntime().availableProcessors();
        final List<String> requirements = new ArrayList<>();
        requirements.add(javaVersion);
        requirements.add(Integer.toString(numberOfCores));

        return requirements;
    }
}
