## _API Aggregator Challenge_ <br />
Goal: Develop an API to consume a contacts endpoint 

## _Table of contents_

- [_Overview_](#overview)
- [_Requirements_](#requirements)
- [_Project Structure_](#requirements)
- [_Howto Build and Run_](#requirements)
- [_Screenshot_](#screenshot)
- [_Links_](...)
- [_Built with_](#built-with)
- [_Code Snippet_](#requirements)
- [_Continued development_](#continued-development)
- [_Useful resources_](#useful-resources)
- [_Author_](#requirements)
- [_Portfolio_](#requirements)

## _Overview_

This challenge delivers an API to consume contacts endpoint. 
The goal is to evaluate how a dev face the challenge of
building a RESTful API,according to beast practices.
<br />

## _Requirements_

There are some specific requirements to be met, paginated data recovery, 
consuming services of other api -> <a href="https://k-messages-api.herokuapp.com" target="_blank">Random.org API</a>, authenticated access to endpoints and some other requirements.<br />
<br />
The app has been coded using Java 17, Spring Boot 3.3.4, Gradle, Javadoc, Spring Security, Spring JPA, Spring Webflux,
OpenAPI, MySQL, Docker and hosted in an AWS EC2 instance with secure access provided
by a NGINX SSL proxy reverse and being live at <a href="https://challenge.ferreiras.dev.br/swagger-ui/index.html" target="_blank">CalculatorWeb-API</a> <br />
<br />
Why don't you take a look at this short video....to see how it works...
<br />
<a href="https://youtu.be/CUdN-P-14So" target="_blank">Short Video</a>
<hr />

## _Project Structure_
- docs
   - javadocs
- src
    - main
    - java
        - br.dev.ferreiras.challenge
            - config
            - controller
              - handlers 
            - dto
            - entity
            - enums
            - repository
            - services
              - exceptions
    - resources
        - certs
    - test
-

## _Howto Build and Run_

  ```
  - MySQL Database : http://127.0.0.1:3306:challenge
  - profile active: dev or prod
  - service socket: 127.0.0.1:8095
  - tweak a few knobs to get it up and running
  """
  src.main.java.br.dev.ferreiras.challenge.config.OpenApiConfiguration
  ...
  public class OpenApiConfiguration {
  @Bean
  public OpenAPI defineOpenApi() {
    Server server = new Server();
    -> server.setUrl("http://127.0.0.1:8097"); <-
    server.setDescription("Development");
   ....
  """
  
  To have a docker image follow the instructions of the dockerBuild.sh,
  otherwise just Ctrl-Shift-F10 and voila...

```

## _Screenshot_

[![](./jobsity.png)]()

## _Links_

- Live Site URL: <a href="https://challenge.ferreiras.dev.br/swagger-ui/index.html" target="_blank">API Aggregator</a>

## _Built with_

[![My Skills](https://skillicons.dev/icons?i=java,spring,mysql,gradle,docker,redhat,aws,idea,git,github,)](https://skillicons.dev)

## _Code Snippet_

```java
import java.util.List;

/**
 * 
 * @author ricardo@ferreiras.dev.br
 * @version 1.1.10.23.01
 * @since 1.0
 *
 */

@Configuration
public class OpenApiConfiguration {
  @Bean
  public OpenAPI defineOpenApi() {
    Server server = new Server();
    server.setUrl("https://challenge.ferreiras.dev.br/");
    server.setDescription("Development");

    Contact myContact = new Contact();
    myContact.setName(":Ricardo Ferreira");
    myContact.setEmail("ricardo@ferreiras.dev.br");

    Info information = new Info()
            .title("Jobsity Challenge")
            .version("1.0")
            .description("API Aggregator")
            .contact(myContact);

    return new OpenAPI()
            .info(information)
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(
                    new Components()
                            .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")
                            )
            )
            .servers(List.of(server));
  }
}

``` 

## _Continued development_

- Unit Tests -

- Subscriber Authentication - OK
- Spring JWT-OAuth2 - OK
- Records Pagination - TBD

### _Useful resources_

- [https://spring.io/] Awesome Java framework!.
- [https://start.spring.io/]  Handy startup tool.
- [https://mvnrepository.com/] Tools that help tackle the beast

## _Author_
<a href="mailto:ricardo@ferreiras.dev.br">Ricardo Ferreira</a>

## - _Portfolio_
<a href="https://www.ferreiras.dev.br" target="_blank">My Portfolio...</a>

