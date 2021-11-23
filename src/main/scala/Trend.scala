// Trend represents the trend finding queries ran on the sample dataset
object Trend {
  def run_query(query_number: Int): Unit = {
    println(s"[INFO] Running query $query_number.")
    Connection.run_data_query(query_number)
  }

  def run_alternative_presidential_nominees(): Unit = {
    Connection.run_alternative_presidential_nominees()
  }

  def run_top_two_nominees_by_year(): Unit = {
    val state = scala.io.StdIn.readLine("[INPUT] Enter a state: ").trim().toUpperCase()
    if (!Connection.is_valid_state(state)) {
      println(s"[ERROR] $state is not valid.")
    } else {
      Connection.run_top_two_nominees_by_year(state)
    }
  }

  def run_district_conversions(): Unit = {
    val state = scala.io.StdIn.readLine("[INPUT] Enter a state: ").trim().toUpperCase()
    if (!Connection.is_valid_state(state)) {
      println(s"[ERROR] $state is not valid.")
    } else {
      Connection.run_district_conversions(state)
    }
  }

  def run_district_eoe_participation(): Unit = {
    val state = scala.io.StdIn.readLine("[INPUT] Enter a state: ").trim().toUpperCase()
    if (!Connection.is_valid_state(state)) {
      println(s"[ERROR] $state is not valid.")
    } else {
      Connection.run_district_eoe_participation(state)
    }
  }

  def run_new_york_senators_party_sums(): Unit = {
    Connection.run_new_york_senators_party_sums()
  }
}