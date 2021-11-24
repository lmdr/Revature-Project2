import scala.util.matching.Regex

// Utility is a container for miscellaneous program utilities
object Utility {
  def create_user_menu_option(numeric_number: Int, literal_number: String, option_text: String): scala.util.matching.Regex = {
    println(s"($numeric_number) Run Query $numeric_number... $option_text")
    (s"^(r|run)? ?(q|query)? ?($numeric_number|$literal_number)" + "$").r
  }
}