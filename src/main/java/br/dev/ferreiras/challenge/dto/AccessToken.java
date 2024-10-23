package br.dev.ferreiras.challenge.dto;

public class AccessToken {
  private String token;
  private Long timeout;

    public AccessToken(String token, Long timeout) {
        this.token = token;
        this.timeout = timeout;
    }

    public AccessToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }
}
