// Trend represents the trend finding queries ran on the sample dataset
object Trend {
  private var _year: Int = 0
  private var _state: String = ""

  def set_year(): Int = {
    val year = scala.io.StdIn.readLine("Enter US Presidential election year (1976-2020): ").trim().toInt
    // Better to check against database
    if (year < 1976 || year > 2020 || year % 4 != 0) {
      println("Error: year invalid. Please enter a valid year.")
    } else {
      _year = year
    }
    println(s"The year is set to: ${_year}.")
    _year
  }

  def set_state(): String = {
    val state = scala.io.StdIn.readLine("Enter US state (full name): ").trim().toUpperCase()
    // Perform validation here
    _state = state
    println(s"The state is set to: ${_state}.")
    _state
  }

  def run_queries(): Unit = {
    println("run_queries()")
  }
}