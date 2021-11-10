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
    println("Welcome! To use this application, please make a select from the menu below.")
    println("================================================================================")
  }

  def display_main_menu(): Unit = {
    var exit = false
    do {
      println("display_main_menu()")
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
        }
    } while (!exit)
  }

  def display_admin_menu(): Unit = {
    var exit = false
    do {
      println("display_admin_menu()")
      println("(1) Get User List...")
      println("(2) Create User...")
      println("(3) Update User...")
      println("(4) Delete User...")
      println("(0) Logout\n")

      val choice = scala.io.StdIn.readLine("Enter menu choice: ").trim().toLowerCase()
      choice match {
        case "1" | "g" | "get" | "user" | "users" | "list" | "get users" |
             "user list" | "get user list" | "get users list" =>
          User.read_user_list()
        case "2" | "c" | "create" | "create user" =>
          User.create_user()
        case "3" | "u" | "up" | "update" | "update user" =>
          User.update_user()
        case "4" | "d" | "del" | "delete" | "delete user" =>
          User.delete_user()
        case "0" | "e" | "ex" | "exit" | "q" | "quit" | "l" | "lo" | "log" | "out" | "logout" =>
          exit = true
        case "i" | "info" | "information" | "w" | "welcome" | "h" | "help" =>
          Menu.display_welcome_message()
        case _ =>
          println("Error: unrecognized option. Please enter a valid option from the menu.")
      }
    } while (!exit)
  }

  def display_user_menu(): Unit = {
    var exit = false
    do {
      println("display_user_menu()")
      println("(1) Set Year...")
      println("(2) Set State...")
      println("(3) Run Queries...")
      println("(0) Logout\n")

      val choice = scala.io.StdIn.readLine("Enter menu choice: ").trim().toLowerCase()
      choice match {
        case "1" | "sy" | "set y" | "y" | "yr" | "year" | "s year" | "set year" =>
          Trend.set_year()
        case "2" | "ss" | "set s" | "s" | "st" | "state" | "s state" | "set state" =>
          Trend.set_state()
        case "3" | "r" | "rq" | "run" | "run q" | "query" | "queries" |
             "r query" | "r queries" | "run query" | "run queries" =>
          Trend.run_queries()
        case "0" | "e" | "ex" | "exit" | "q" | "quit" | "l" | "lo" | "log" | "out" | "logout" =>
          exit = true
        case "i" | "info" | "information" | "w" | "welcome" | "h" | "help" =>
          Menu.display_welcome_message()
        case _ =>
          println("Error: unrecognized option. Please enter a valid option from the menu.")
      }
    } while (!exit)
  }

  def display_admin_login_prompt(): Unit = {
    println("display_admin_login_prompt()")
    val username = scala.io.StdIn.readLine("Enter your username: ").trim()
    val password = scala.io.StdIn.readLine("Enter your password: ").trim()
    if (!Connection.verify_login(username,password,true)) {
      println("Error: could not log you in.")
      val account_creation = scala.io.StdIn.readLine("Would you like to create an admin account with the information you provided? (yes/no): ").trim().toLowerCase()
      account_creation match {
        case "y" | "yes" =>
          val name = scala.io.StdIn.readLine("Enter your name: ").trim()
          Connection.create_user(username, password, name, true)
          val login_now = scala.io.StdIn.readLine("Would you like to login now? (yes/no): ").trim().toLowerCase()
          login_now match {
            case "y" | "yes" =>
              if (!Connection.verify_login(username,password,true)) {
                println("Error: could not log you in.")
              } else {
                Menu.display_admin_menu()
              }
            case _ =>
              println("Returning you to the main menu.")
          }
        case _ =>
          println("Returning you to the main menu.")
      }
    } else {
      Menu.display_admin_menu()
    }
  }

  def display_user_login_prompt(): Unit = {
    println("display_user_login_prompt()")
    val username = scala.io.StdIn.readLine("Enter your username: ").trim()
    val password = scala.io.StdIn.readLine("Enter your password: ").trim()
    if (!Connection.verify_login(username, password, false)) {
      println("Error: could not log you in.")
      val account_creation = scala.io.StdIn.readLine("Would you like to create an user account with the information you provided? (yes/no): ").trim().toLowerCase()
      account_creation match {
        case "y" | "yes" =>
          val name = scala.io.StdIn.readLine("Enter your name: ").trim()
          Connection.create_user(username, password, name, false)
          val login_now = scala.io.StdIn.readLine("Would you like to login now? (yes/no): ").trim().toLowerCase()
          login_now match {
            case "y" | "yes" =>
              if (!Connection.verify_login(username,password,false)) {
                println("Error: could not log you in.")
              } else {
                Menu.display_user_menu()
              }
            case _ =>
              println("Returning you to the main menu.")
          }
        case _ =>
          println("Returning you to the main menu.")
      }
    } else {
      Menu.display_user_menu()
    }
  }
}