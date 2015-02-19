package app

import org.springframework.stereotype.Service

/**
 * Created by danw on 2/18/15.
 */
@Service
class UserService {
  User save(User user) {
    // do some blocking op, like a db call
    user.id = new Random().nextLong()
    user
  }
}
