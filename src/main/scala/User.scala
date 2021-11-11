// User represents all interactions with application users
object User {
  def read_user_list(): Unit = {
    println("[INFO] Printing table of users.")
    Connection.read_user_table()
  }

  def create_user(): Unit = {
    println("[INFO] Creating user...")
    val username = scala.io.StdIn.readLine("[INPUT] Enter an account username: ").trim()
    if (!Connection.is_username_available(username)) {
      println("[ERROR] Username is unavailable.")
    } else {
      val password = scala.io.StdIn.readLine("[INPUT] Enter a password: ").trim()
      val name = scala.io.StdIn.readLine("[INPUT] Enter user's name: ").trim()
      val admin = scala.io.StdIn.readLine("[INPUT] Should this account be granted administrator rights? (yes/no): ").trim().toLowerCase()
      admin match {
        case "y" | "yes" =>
          Connection.create_user(username, password, name, true)
        case "n" | "no" =>
          Connection.create_user(username, password, name, false)
        case _ =>
          println("[ERROR] Unrecognized option.")
      }
    }
  }

  def update_user(): Unit = {
    println("[INFO] Updating user...")
    val username = scala.io.StdIn.readLine("[INPUT] Enter an account username: ").trim()
    if (Connection.is_username_available(username)) {
      println("[ERROR] Username does not exist.")
    } else {
      val property = scala.io.StdIn.readLine("[INPUT] Enter a property to update (password/name/admin): ").trim.toLowerCase()
      property match {
        case "p" | "w" | "pw" | "pass" | "word" | "password" =>
          val password = scala.io.StdIn.readLine("[INPUT] Enter a new password: ").trim()
          Connection.update_user(username, "password", password)
        case "n" | "name" =>
          val name = scala.io.StdIn.readLine("[INPUT] Enter a new name: ").trim()
          Connection.update_user(username, "name", name)
        case "a" | "ad" | "admin" =>
          val admin = scala.io.StdIn.readLine("[INPUT] Should this account be granted administrator rights? (yes/no): ").trim().toLowerCase()
          admin match {
            case "y" | "yes" =>
              Connection.update_user(username, "admin", true)
            case "n" | "no" =>
              Connection.update_user(username, "admin", false)
            case _ =>
              println("[ERROR] Unrecognized option.")
          }
        case _ =>
          println("[ERROR] Unrecognized option.")
      }
    }
  }

  def delete_user(): Unit = {
    println("[INFO] Deleting user...")
    val username = scala.io.StdIn.readLine("[INPUT] Enter an account username to delete: ").trim()
    if (Connection.is_username_available(username)) {
      println("[ERROR] Username does not exist.")
    } else {
      Connection.delete_user(username)
    }
  }
}