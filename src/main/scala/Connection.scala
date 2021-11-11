// Connection object represents facade for database connections
object Connection {
  def connect() : Unit = {
    println("connect()")
    MySQLConnection.connect()
    HiveConnection.connect()
  }

  def disconnect(): Unit = {
    println("disconnect()")
    MySQLConnection.disconnect()
    HiveConnection.disconnect()
  }

  def verify_login(username: String, password: String, admin: Boolean): Boolean = {
    println("verify_login()")
    MySQLConnection.verify_login(username, password, admin)
  }

  def read_user_table(): Unit = {
    println("read_user_table()")
    MySQLConnection.read_user_table()
  }

  def create_user(username: String, password: String, name: String, admin: Boolean): Boolean = {
    println("create_user()")
    MySQLConnection.create_user(username, password, name, admin)
  }

  def update_user(username: String, property: String, value: Any): Boolean = {
    println("update_user()")
    MySQLConnection.update_user(username, property, value)
  }

  def delete_user(username: String): Boolean = {
    println("delete_user()")
    MySQLConnection.delete_user(username)
  }

  def run_data_query(query_number: Int): Unit = {
    println("run_data_queries()")
    query_number match {
      case 1 =>
        HiveConnection.run_data_query_one()
      case 2 =>
        HiveConnection.run_data_query_two()
      case 3 =>
        HiveConnection.run_data_query_three()
      case 4 =>
        HiveConnection.run_data_query_four()
      case 5 =>
        HiveConnection.run_data_query_five()
      case 6 =>
        HiveConnection.run_data_query_six()
      case _ =>
    }
  }
}