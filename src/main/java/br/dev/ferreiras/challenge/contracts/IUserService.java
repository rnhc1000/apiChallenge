package br.dev.ferreiras.challenge.contracts;

import br.dev.ferreiras.challenge.entity.Role;
import br.dev.ferreiras.challenge.entity.User;

import java.util.Optional;

/**
 * Username & role
 *
 * @author ricardo@ferreiras.dev.br
 * @version 1.1.10.23.01
 * @since 1.0
 */
public interface IUserService {
    Optional<User> getUsername(String username);
    Role getRole();

}
