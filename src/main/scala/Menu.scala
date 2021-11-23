// Menu represents user menus
object Menu {
  def display_welcome_message(): Unit = {
    println("[INFO] US Presidential Election Analysis Tool\n")
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
    println("[INFO] This tool loads presidential election data\nfrom 1976 to 2020 and finds trends in the data.\n")
    println("[INFO] Welcome! To use this application, please make a select from the menu below.")
    println("================================================================================")
  }

  def display_main_menu(): Unit = {
    var exit = false
    do {
      println("[MENU] Main Menu")
      println("================================================================================")
      println("(1) Admin Login...")
      println("(2) User Login...")
      println("(0) Exit")

      val choice = scala.io.StdIn.readLine("[INPUT] Enter menu choice: ").trim().toLowerCase()
      choice match {
        case "1" | "a" | "ad" | "admin" => Menu.display_admin_login_prompt()
        case "2" | "u" | "user" => Menu.display_user_login_prompt()
        case "0" | "e" | "ex" | "exit" | "q" | "quit" => exit = true
        case "i" | "info" | "information" | "w" | "welcome" | "h" | "help" => Menu.display_welcome_message()
        case _ =>
          println("[ERROR] Unrecognized option. Please enter a valid option from the menu.")
        }
    } while (!exit)
  }

  def display_admin_menu(): Unit = {
    var exit = false
    do {
      println("[MENU] Admin Menu")
      println("================================================================================")
      println("(1) Get User List...")
      println("(2) Create User...")
      println("(3) Update User...")
      println("(4) Delete User...")
      println("(0) Logout")

      val choice = scala.io.StdIn.readLine("[INPUT] Enter menu choice: ").trim().toLowerCase()
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
          println("[ERROR] Unrecognized option. Please enter a valid option from the menu.")
      }
    } while (!exit)
  }

  def display_user_menu(): Unit = {
    var exit = false
    do {
      println("[MENU] User Menu")
      println("================================================================================")
      println("(1) Project One Queries...")
      println("(2) Project Two Queries...")
      println("(0) Logout")

      val choice = scala.io.StdIn.readLine("[INPUT] Enter menu choice: ").trim().toLowerCase()
      val query_menu_one = "^(p|pj|pro|project)? ?(1|one) ?(q|query|queries)?$".r
      val query_menu_two = "^(p|pj|pro|project)? ?(2|two) ?(q|query|queries)?$".r
      choice match {
        case query_menu_one(_*) => Menu.display_user_menu_project_one()
        case query_menu_two(_*) => Menu.display_user_menu_project_two()
        case "0" | "e" | "ex" | "exit" | "q" | "quit" | "l" | "lo" | "log" | "out" | "logout" =>
          exit = true
        case "i" | "info" | "information" | "w" | "welcome" | "h" | "help" =>
          Menu.display_welcome_message()
        case _ =>
          println("[ERROR] Unrecognized option. Please enter a valid option from the menu.")
      }
    } while (!exit)
  }

  def display_user_menu_project_one(): Unit = {
    var exit = false
    do {
      println("[MENU] Project 1 User Menu")
      println("================================================================================")
      println("(1) Run Query 1... All winners grouped by party.")
      println("(2) Run Query 2... All instances where a nominee has won twice.")
      println("(3) Run Query 3... All instances where the winner was from the same party as the previous winner.")
      println("(4) Run Query 4... Election over election change of overall voter participation.")
      println("(5) Run Query 5... All instances where a state has by popular vote switched party.")
      println("(6) Run Query 6... Election over election change in voter participation by party.")
      println("(0) Logout")

      val choice = scala.io.StdIn.readLine("[INPUT] Enter menu choice: ").trim().toLowerCase()
      val query_one = "^(r|run)? ?(q|query)? ?(1|one)$".r
      val query_two = "^(r|run)? ?(q|query)? ?(2|two)$".r
      val query_three = "^(r|run)? ?(q|query)? ?(3|three)$".r
      val query_four = "^(r|run)? ?(q|query)? ?(4|four)$".r
      val query_five = "^(r|run)? ?(q|query)? ?(5|five)$".r
      val query_six = "^(r|run)? ?(q|query)? ?(6|six)$".r
      choice match {
        case query_one(_*) => Trend.run_query(1)
        case query_two(_*) => Trend.run_query(2)
        case query_three(_*) => Trend.run_query(3)
        case query_four(_*) => Trend.run_query(4)
        case query_five(_*) => Trend.run_query(5)
        case query_six(_*) => Trend.run_query(6)
        case "0" | "e" | "ex" | "exit" | "q" | "quit" | "l" | "lo" | "log" | "out" | "logout" =>
          exit = true
        case "i" | "info" | "information" | "w" | "welcome" | "h" | "help" =>
          Menu.display_welcome_message()
        case _ =>
          println("[ERROR] Unrecognized option. Please enter a valid option from the menu.")
      }
    } while (!exit)
  }

  def display_user_menu_project_two(): Unit = {
    var exit = false
    do {
      println("[MENU] Project 2 User Menu")
      println("================================================================================")
      println("(1) Run Query 1... Top two nominees (presidents, representatives, senators) by year.")
      println("(2) Run Query 2... All instances a district has by popular vote switched party.")
      println("(3) Run Query 3... Election over election change in overall voter participation.")
      println("(4) Run Query 4... PLACEHOLDER.") // TODO fill in other queries here
      println("(0) Logout")

      val choice = scala.io.StdIn.readLine("[INPUT] Enter menu choice: ").trim().toLowerCase()
      val query_one = "^(r|run)? ?(q|query)? ?(1|one)$".r
      val query_two = "^(r|run)? ?(q|query)? ?(2|two)$".r
      val query_three = "^(r|run)? ?(q|query)? ?(3|three)$".r
      val query_four = "^(r|run)? ?(q|query)? ?(4|four)$".r
      choice match {
        case query_one(_*) => Trend.run_top_two_nominees_by_year()
        case query_two(_*) => Trend.run_district_conversions()
        case query_three(_*) => Trend.run_district_eoe_participation()
        case query_four(_*) => println("[INFO] PLACEHOLDER") // TODO fill in other queries here
        case "0" | "e" | "ex" | "exit" | "q" | "quit" | "l" | "lo" | "log" | "out" | "logout" =>
          exit = true
        case "i" | "info" | "information" | "w" | "welcome" | "h" | "help" =>
          Menu.display_welcome_message()
        case _ =>
          println("[ERROR] Unrecognized option. Please enter a valid option from the menu.")
      }
    } while (!exit)
  }

  def display_admin_login_prompt(): Unit = {
    println("[INFO] Admin Login...")
    val username = scala.io.StdIn.readLine("[INPUT] Enter your username: ").trim()
    val password = scala.io.StdIn.readLine("[INPUT] Enter your password: ").trim()
    if (Connection.is_username_available(username)) {
      val account_creation = scala.io.StdIn.readLine("[INPUT] Would you like to create an admin account with the information you provided? (yes/no): ").trim().toLowerCase()
      account_creation match {
        case "y" | "yes" =>
          val name = scala.io.StdIn.readLine("[INPUT] Enter your name: ").trim()
          Connection.create_user(username, password, name, true)
          val login_now = scala.io.StdIn.readLine("[INPUT] Would you like to login now? (yes/no): ").trim().toLowerCase()
          login_now match {
            case "y" | "yes" =>
              if (!Connection.verify_login(username, password,true)) {
                println("[ERROR] Could not log you in.")
              } else {
                Menu.display_admin_menu()
              }
            case _ =>
              println("[INFO] Returning you to the main menu.")
          }
        case _ =>
          println("[INFO] Returning you to the main menu.")
      }
    } else {
      if (!Connection.verify_login(username, password,true)) {
        println("[ERROR] Could not log you in.")
      } else {
        Menu.display_admin_menu()
      }
    }
  }

  def display_user_login_prompt(): Unit = {
    println("[INFO] User Login...")
    val username = scala.io.StdIn.readLine("[INPUT] Enter your username: ").trim()
    val password = scala.io.StdIn.readLine("[INPUT] Enter your password: ").trim()
    if (Connection.is_username_available(username)) {
      val account_creation = scala.io.StdIn.readLine("[INPUT] Would you like to create an user account with the information you provided? (yes/no): ").trim().toLowerCase()
      account_creation match {
        case "y" | "yes" =>
          val name = scala.io.StdIn.readLine("[INPUT] Enter your name: ").trim()
          Connection.create_user(username, password, name, false)
          val login_now = scala.io.StdIn.readLine("[INPUT] Would you like to login now? (yes/no): ").trim().toLowerCase()
          login_now match {
            case "y" | "yes" =>
              if (!Connection.verify_login(username, password,false)) {
                println("[ERROR] Could not log you in.")
              } else {
                Menu.display_user_menu()
              }
            case _ =>
              println("[INFO] Returning you to the main menu.")
          }
        case _ =>
          println("[INFO] Returning you to the main menu.")
      }
    } else {
      if (!Connection.verify_login(username, password, false)) {
        println("[ERROR] Could not log you in.")
      } else {
        Menu.display_user_menu()
      }
    }
  }
}