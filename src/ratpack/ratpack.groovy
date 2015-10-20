import app.SpringConfig
import app.User
import app.UserService
import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContext

import ratpack.exec.Blocking;


import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.fromJson
import static ratpack.jackson.Jackson.json
import static ratpack.spring.Spring.spring

ratpack {
  bindings {
    bindInstance(ApplicationContext, SpringApplication.run(SpringConfig))
  }
  handlers { ApplicationContext ctx ->
    register(spring(ctx))

      post("user") { UserService userService ->
          parse(fromJson(User)).then({ user ->
              Blocking.get {
                  userService.save(user)
              } then { User savedUser ->
                  render(json(savedUser))
              }
          })
      }

      fileSystem("assets") {
          it.files()
      }
  }
}
