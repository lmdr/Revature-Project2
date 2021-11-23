import java.sql.{Connection, DriverManager, SQLException, SQLTimeoutException, Statement}

// MySQLConnection object represents MySQL Connection to MySQL through JDBC
object MySQLConnection {
  private var _connection: java.sql.Connection = _
  private var _statement: java.sql.Statement = _

  def connect(): Unit = {
    val uri = "jdbc:mysql://localhost"
    val user = sys.env("MYSQL_USER")
    val pass = sys.env("MYSQL_PASSWORD")

    try {
      _connection = java.sql.DriverManager.getConnection(uri, user, pass)
      _statement = _connection.createStatement()
      MySQLConnection.create_database()
      MySQLConnection.create_user_table()
    } catch {
      case timeout: java.sql.SQLTimeoutException =>
        timeout.printStackTrace()
        println("[ERROR] SQL Timeout")
      case sql: java.sql.SQLException =>
        sql.printStackTrace()
        println("[ERROR] SQL")
    }
  }

  def disconnect(): Unit = {
    _connection.close()
  }

  private def create_database(): Unit = {
    _statement.execute("CREATE DATABASE IF NOT EXISTS project2")
    _statement.execute("USE project2")
  }

  private def create_user_table(): Unit = {
    _statement.execute("CREATE TABLE IF NOT EXISTS users(username VARCHAR(255) NOT NULL PRIMARY KEY, password VARCHAR(255) NOT NULL, name VARCHAR(255) NOT NULL, admin BOOLEAN NOT NULL)")
  }

  def verify_login(username: String, password: String, admin: Boolean): Boolean = {
    if (admin) {
      _statement.execute(s"SELECT * FROM users WHERE username = '$username' AND password = '$password' AND admin = true")
    } else {
      _statement.execute(s"SELECT * FROM users WHERE username = '$username' AND password = '$password'")
    }
    val result = _statement.getResultSet
    if(!result.next()) {
      false
    } else {
      true
    }
  }

  def is_username_available(username: String): Boolean = {
    _statement.execute(s"SELECT * FROM users WHERE username = '$username'")
    val users = _statement.getResultSet
    if (!users.next()) {
      true
    } else {
      false
    }
  }

  def read_user_table(): Unit = {
    _statement.execute("SELECT * FROM users")
    val users = _statement.getResultSet
    if (!users.next()) {
      println("[ERROR] No registered users.")
    } else {
      println(f"${"username"}%-22s | ${"password"}%-22s | ${"name"}%-22s | admin")
      println("-----------------------+------------------------+------------------------+------")
      do {
        print(f"${users.getString("username")}%-22s | " +
          f"${users.getString("password")}%-22s | " +
          f"${users.getString("name")}%-22s | " +
          f"${users.getBoolean("admin")}\n")
      } while (users.next())
    }
  }

  def create_user(username: String, password: String, name: String, admin: Boolean): Boolean = {
    _statement.execute(s"INSERT INTO users VALUES ('$username', '$password', '$name', $admin)")
  }

  def update_user(username: String, property: String, value: Any): Boolean = {
    property match {
      case "password" | "name" =>
        _statement.execute(s"UPDATE users SET $property = '$value' WHERE username = '$username'")
      case "admin" =>
        _statement.execute(s"UPDATE users SET $property = $value WHERE username = '$username'")
      case _ =>
        false
    }
  }

  def delete_user(username: String): Boolean = {
    _statement.execute(s"DELETE FROM users WHERE username = '$username'")
  }
}