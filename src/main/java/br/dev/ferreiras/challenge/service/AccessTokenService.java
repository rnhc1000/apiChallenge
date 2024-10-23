package br.dev.ferreiras.challenge.service;

import br.dev.ferreiras.challenge.dto.AccessToken;
import br.dev.ferreiras.challenge.entity.User;
import br.dev.ferreiras.challenge.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccessTokenService {

        private final JwtEncoder jwtEncoder;
        private final BCryptPasswordEncoder bCryptPasswordEncoder;
        private final UserService userService;

        public AccessTokenService(
                final JwtEncoder jwtEncoder,
                final BCryptPasswordEncoder bCryptPasswordEncoder,
                final UserService userService) {
            this.jwtEncoder = jwtEncoder;
            this.bCryptPasswordEncoder = bCryptPasswordEncoder;
            this.userService = userService;
        }

        private static final Logger logger = LoggerFactory.getLogger(AccessTokenService.class);

        /**
         * @param username  as an object
         * @return accessToken object
         */
        public AccessToken generateToken(final String username) {

            AccessTokenService.logger.info("Username -> {}", username);

            final Optional<User> user = this.userService.getUsername(username);
            final var scopes = user.orElseThrow().getRoles()
                    .stream()
                    .map(Role::getRole)
                    .collect(Collectors.joining(" "));

            AccessTokenService.logger.info("scopes -> {}", scopes);

            final var expiresIn = 3600L;
            final var now = Instant.now();
            final var claims = JwtClaimsSet.builder()
                    .issuer("challengeJobsity")
                    .subject(user.orElseThrow().getId().toString())
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(expiresIn))
                    .claim("scope", scopes)
                    .claim("username", username)
                    .build();

            final String jwtValue = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            AccessTokenService.logger.info(jwtValue);

            return new AccessToken(jwtValue, expiresIn);
        }
}
