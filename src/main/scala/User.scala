// User represents all interactions with application users
object User {
  def read_user_list(): Unit = {
    println("read_user_list()")
    Connection.read_user_table()
  }

  def create_user(): Unit = {
    println("create_user()")
    val username = scala.io.StdIn.readLine("Enter an account username: ").trim()
    val password = scala.io.StdIn.readLine("Enter a password: ").trim()
    val name = scala.io.StdIn.readLine("Enter user's name: ").trim()
    val admin = scala.io.StdIn.readLine("Should this account be granted administrator rights? (yes/no): ").trim().toLowerCase()
    admin match {
      case "y" | "yes" =>
        Connection.create_user(username, password, name, true)
      case "n" | "no" =>
        Connection.create_user(username, password, name, false)
      case _ =>
        println("Error: unrecognized option.")
    }
  }

  def update_user(): Unit = {
    println("update_user()")
    val username = scala.io.StdIn.readLine("Enter an account username: ").trim()
    val property = scala.io.StdIn.readLine("Enter a property to update (password/name/admin): ").trim.toLowerCase()
    property match {
      case "p" | "w" | "pw" | "pass" | "word" | "password" =>
        val password = scala.io.StdIn.readLine("Enter a new password: ").trim()
        Connection.update_user(username, "password", password)
      case "n" | "name" =>
        val name = scala.io.StdIn.readLine("Enter a new name: ").trim()
        Connection.update_user(username, "name", name)
      case "a" | "ad" | "admin" =>
        val admin = scala.io.StdIn.readLine("Should this account be granted administrator rights? (yes/no): ").trim().toLowerCase()
        admin match {
          case "y" | "yes" =>
            Connection.update_user(username, "admin", true)
          case "n" | "no" =>
            Connection.update_user(username, "admin", false)
          case _ =>
            println("Error: unrecognized option.")
        }
      case _ =>
        println("Error: unrecognized option.")
    }
  }

  def delete_user(): Unit = {
    println("delete_user()")
    val username = scala.io.StdIn.readLine("Enter an account username to delete: ").trim()
    Connection.delete_user(username)
  }
}