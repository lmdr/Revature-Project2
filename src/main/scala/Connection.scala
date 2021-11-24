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
        println("[INFO] All winners grouped by party.")
        HiveConnection.run_data_query_one()
      case 2 =>
        println("[INFO] All instances where a nominee has won twice.")
        HiveConnection.run_data_query_two()
      case 3 =>
        println("[INFO] All instances where the winner was from the same party as the previous winner.")
        HiveConnection.run_data_query_three()
      case 4 =>
        println("[INFO] Election over election change in overall voter participation.")
        HiveConnection.run_data_query_four()
      case 5 =>
        println("[INFO] All instances where a state has by popular vote switched party.")
        HiveConnection.run_data_query_five()
      case 6 =>
        println("[INFO] Election over election change in voter participation by party.")
        HiveConnection.run_data_query_six()
      case _ =>
    }
  }

  def is_valid_state(state: String): Boolean = {
    HiveConnection.is_valid_state(state)
  }

  def run_2020_presidential_vote_state_breakdown(): Unit = {
    println("[INFO] Breakdown of the vote count, state by state, during the 2020 Presidential Election.")
    HiveConnection.run_2020_presidential_vote_state_breakdown()
  }

  def run_alternative_presidential_nominees(): Unit = {
    println("[INFO] All presidential nominees not of Democrat or Republican parties.")
    HiveConnection.run_alternative_presidential_nominees()
  }

  def run_top_two_nominees_by_year(state: String): Unit = {
    println(s"[INFO] Top two nominees (presidents, representatives, senators) by year in $state.")
    HiveConnection.run_top_two_nominees_by_year(state)
  }

  def run_district_conversions(state: String): Unit = {
    println(s"[INFO] All instances a district has by popular vote switched party in $state.")
    HiveConnection.run_district_conversions(state)
  }

  def run_district_eoe_participation(state: String): Unit = {
    println(s"[INFO] Election over election change in overall voter participation in $state.")
    HiveConnection.run_district_eoe_participation(state)
  }

  def run_new_york_senators_party_sums(): Unit = {
    println("[INFO] Sum of votes received by all parties in New York.")
    HiveConnection.run_new_york_senators_party_sums()
  }
}