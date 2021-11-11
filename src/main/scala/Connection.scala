// Connection object represents facade for database connections
object Connection {
  def connect() : Unit = {
    println("[INFO] Connecting to Hive and MySQL.")
    MySQLConnection.connect()
    HiveConnection.connect()
    println("[INFO] Connected to Hive and MySQL.")
  }

  def disconnect(): Unit = {
    println("[INFO] Disconnecting from Hive and MySQL.")
    MySQLConnection.disconnect()
    HiveConnection.disconnect()
    println("[INFO] Disconnected from Hive and MySQL.")
  }

  def verify_login(username: String, password: String, admin: Boolean): Boolean = {
    MySQLConnection.verify_login(username, password, admin)
  }

  def is_username_available(username: String): Boolean = {
    MySQLConnection.is_username_available(username)
  }

  def read_user_table(): Unit = {
    MySQLConnection.read_user_table()
  }

  def create_user(username: String, password: String, name: String, admin: Boolean): Boolean = {
    MySQLConnection.create_user(username, password, name, admin)
  }

  def update_user(username: String, property: String, value: Any): Boolean = {
    MySQLConnection.update_user(username, property, value)
  }

  def delete_user(username: String): Boolean = {
    MySQLConnection.delete_user(username)
  }

  def run_data_query(query_number: Int): Unit = {
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