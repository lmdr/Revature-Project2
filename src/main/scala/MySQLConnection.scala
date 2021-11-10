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
        println("Error: SQL Timeout")
      case sql: java.sql.SQLException =>
        sql.printStackTrace()
        println("Error: SQL")
    }
  }

  def disconnect(): Unit = {
    _connection.close()
  }

  private def create_database(): Unit = {
    _statement.execute("CREATE DATABASE IF NOT EXISTS project1")
    _statement.execute("USE project1")
  }

  private def create_user_table(): Unit = {
    _statement.execute("CREATE TABLE IF NOT EXISTS users(username VARCHAR(255) NOT NULL PRIMARY KEY, password VARCHAR(255) NOT NULL, name VARCHAR(255) NOT NULL, state VARCHAR(255) NOT NULL, year INT, admin BOOLEAN NOT NULL)")
  }

  def read_user_table(): Unit = {
    _statement.execute("SELECT * FROM users")
    val users = _statement.getResultSet
    if (!users.next()) {
      println("No registered users.")
    } else {
      println("username | password | name | state | year | admin")
      do {
        print(s"${users.getString("username")} | " +
          s"${users.getString("password")} | " +
          s"${users.getString("name")} | " +
          s"${users.getString("state")} | " +
          s"${users.getInt("year")} | " +
          s"${users.getBoolean("admin")}\n")
      } while (users.next())
    }
  }

  def create_user(username: String, password: String, name: String, admin: Boolean): Boolean = {
    _statement.execute(s"INSERT INTO users VALUES ('$username', '$password', '$name', null, null, $admin)")
  }

  def update_user(username: String, property: String, value: Any): Boolean = {
    property match {
      case "password" | "name" | "state" =>
        _statement.execute(s"UPDATE users SET $property = '$value' WHERE username = '$username'")
      case "year" | "admin" =>
        _statement.execute(s"UPDATE users SET $property = $value WHERE username = '$username'")
      case _ =>
        false
    }
  }

  def delete_user(username: String): Boolean = {
    _statement.execute(s"DELETE FROM users WHERE username = '$username'")
  }
}