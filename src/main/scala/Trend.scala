// Trend represents the trend finding queries ran on the sample dataset
object Trend {
  def run_query(query_number: Int): Unit = {
    println("run_queries()")
    Connection.run_data_query(query_number)
  }
}