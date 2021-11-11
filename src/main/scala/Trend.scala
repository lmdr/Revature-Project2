// Trend represents the trend finding queries ran on the sample dataset
object Trend {
  def run_query(query_number: Int): Unit = {
    println(s"[INFO] Running query $query_number.")
    Connection.run_data_query(query_number)
  }
}