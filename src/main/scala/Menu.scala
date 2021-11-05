
// Menu represents user menus
object Menu {
  def display_welcome_message(): Unit = {
    println("display_welcome_message()")
    println("US Presidential Election Analysis Tool\n")
    println("      A     .  '  .     A")
    println("     / \\ '           ' / \\")
    println("    |   \\     ___     /   |")
    println("  .  \\   )  L=   \\   (   /  .")
    println("    <   [    )    |   ]   >")
    println(" .   <   \\__/     \\__/   >   .")
    println("      <                 >")
    println(" .      \\=-_       _-=/      .")
    println("        D   \\     /   A")
    println("   '     DD /     \\ AA     '")
    println("      .    /_-   -_\\    .")
    println("         .    \\-/    .")
    println("            '  .  '\n")
    println("This tool loads presidential election data\nfrom 1976 to 2020 and finds trends in the data.\n")
    println("Welcome! To use this application, please make a select from the main menu below.")
    println("================================================================================")
  }

  def display_main_menu(): Unit = {
    println("display_main_menu()")

    var exit = false
    do {
      println("(1) Admin Login...")
      println("(2) User Login...")
      println("(0) Exit\n")

      val choice = scala.io.StdIn.readLine("Enter menu choice: ").trim().toLowerCase()
      choice match {
        case "1" | "a" | "ad" | "admin" => Menu.display_admin_login_prompt()
        case "2" | "u" | "user" => Menu.display_user_login_prompt()
        case "0" | "e" | "ex" | "exit" | "q" | "quit" => exit = true
        case "i" | "info" | "information" | "w" | "welcome" | "h" | "help" => Menu.display_welcome_message()
        case _ =>
          println("Error: unrecognized option. Please enter a valid option from the menu.")
          println("================================================================================")
        }
    } while (!exit)
  }

  def display_admin_menu(): Unit = {
    println("display_admin_menu()")
  }

  def display_user_menu(): Unit = {
    println("display_user_menu()")
  }

  def display_admin_login_prompt(): Unit = {
    println("display_admin_login_prompt()")
    Menu.display_admin_menu()
  }

  def display_user_login_prompt(): Unit = {
    println("display_user_login_prompt()")
    Menu.display_user_menu()
  }
}
