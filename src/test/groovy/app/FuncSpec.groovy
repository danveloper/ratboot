package app

import com.fasterxml.jackson.databind.ObjectMapper
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.test.ApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.Shared
import spock.lang.Specification

import static groovy.json.JsonOutput.toJson

class FuncSpec extends Specification {
  @Shared ApplicationUnderTest aut = new GroovyRatpackMainApplicationUnderTest()
  @Shared ObjectMapper mapper = new ObjectMapper()
  @Delegate TestHttpClient client = aut.httpClient
  def "should parse and save user"() {
    given:
    def user = new User(username: "dan", email: "danielpwoods@gmail.com")

    when:
    requestSpec { spec ->
      spec.body { b ->
        b.type("application/json")
        b.text(toJson(user))
      }
    }
    post('user')

    then:
    def savedUser = mapper.readValue(response.body.text, User)

    and:
    savedUser.id
  }
}
