package app

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringConfig {
  @Bean
  UserService userService() {
    new UserService()
  }
}
