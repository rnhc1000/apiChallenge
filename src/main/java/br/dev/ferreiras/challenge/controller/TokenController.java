package br.dev.ferreiras.challenge.controller;

import br.dev.ferreiras.challenge.dto.AccessToken;
import br.dev.ferreiras.challenge.dto.LoginRequestDto;
import br.dev.ferreiras.challenge.dto.LoginResponseDto;
import br.dev.ferreiras.challenge.entity.User;
import br.dev.ferreiras.challenge.service.AccessTokenService;
import br.dev.ferreiras.challenge.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
/**
 * Generate tokens to access
 * endpoint securely.
 *
 * @author ricardo@ferreiras.dev.br
 * @version 1.1.10.23.01
 * @since 1.0
 */

@RestController
@RequestMapping (path = "api/v1")
public class TokenController {

  private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

  private final UserService userService;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private final AccessTokenService tokenService;

  public TokenController(
          final BCryptPasswordEncoder bCryptPasswordEncoder,
          final UserService userService, final AccessTokenService tokenService) {

    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userService = userService;
    this.tokenService = tokenService;
  }

  @Operation (summary = "Authenticate a user and return an access token and its expiration time")
  @ApiResponses ({
          @ApiResponse (responseCode = "200", description = "OK!", content = @Content (mediaType = "application/json",
                  schema = @Schema (implementation = AccessToken.class))),
          @ApiResponse (responseCode = "201", description = "Access Token created!", content = @Content (
                  mediaType = "application/json")),
          @ApiResponse (responseCode = "401", description = "Access Denied!", content = @Content (
                  mediaType = "application/json")),
          @ApiResponse (responseCode = "403", description = "Not Authorized!", content = @Content),
  })
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping ("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody final LoginRequestDto loginRequestDto) {

    final Optional<User> user = this.userService.getUsername(loginRequestDto.username());

    if (user.isEmpty() || !user.get().isLoginCorrect(loginRequestDto, this.bCryptPasswordEncoder)) {
      throw new BadCredentialsException("Try again with good credentials!");
    } else {
      final var accessToken = this.tokenService.generateToken((user.get().getUsername()));
      TokenController.logger.info("Access Token-> , {}", accessToken);
      final var jwtValue = accessToken.getToken();
      final var expiresIn = accessToken.getTimeout();

      return ResponseEntity.ok(new LoginResponseDto(jwtValue, expiresIn));
    }
  }
}
