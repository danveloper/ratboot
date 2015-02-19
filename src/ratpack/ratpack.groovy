import app.SpringConfig
import app.User
import app.UserService
import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContext
import ratpack.jackson.JacksonModule

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.fromJson
import static ratpack.jackson.Jackson.json
import static ratpack.spring.Spring.spring

ratpack {
  bindings {
    add(new JacksonModule())
    bindInstance(ApplicationContext, SpringApplication.run(SpringConfig))
  }
  handlers { ApplicationContext ctx ->
    register(spring(ctx))

    prefix("user") {
      handler { UserService userService ->
        byMethod {
          post {
            def user = parse(fromJson(User))
            blocking {
              userService.save(user)
            } then { User savedUser ->
              render(json(savedUser))
            }
          }
        }
      }
    }
    assets "assets"
  }
}
