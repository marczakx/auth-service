package marczakx.auth.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import marczakx.auth.model.LoginRequest;
import marczakx.auth.model.LoginResponse;
import marczakx.auth.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

  private final AuthService authService;
  private final AuthenticationManager authenticationManager;

  @PostMapping("login")
  public LoginResponse login(@RequestBody LoginRequest loginRequest) {
    authenticationManager
      .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.user(), loginRequest.password()));
    return authService.login(loginRequest);
  }

  @GetMapping("verify")
  public void verify() {
  }

}
