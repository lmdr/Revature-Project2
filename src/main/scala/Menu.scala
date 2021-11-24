// Menu represents user menus
object Menu {
  def display_welcome_message(): Unit = {
    println("[INFO] US Election Analysis Tool\n")
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
    println("[INFO] This tool loads election data from 1976 to 2020 and finds trends in the data.\n")
    println("[INFO] Welcome! To use this application, please make a select from the menu below.")
  }

  def display_main_menu(): Unit = {
    var exit = false
    do {
      println("====================================================================================================")
      println("[MENU] Main Menu")
      println("====================================================================================================")
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
      println("====================================================================================================")
      println("[MENU] Admin Menu")
      println("====================================================================================================")
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
      println("====================================================================================================")
      println("[MENU] User Menu")
      println("====================================================================================================")
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
      println("====================================================================================================")
      println("[MENU] Project One User Menu")
      println("====================================================================================================")
      val query_one = Utility.create_user_menu_option(1, "one", "All winners grouped by party.")
      val query_two = Utility.create_user_menu_option(2, "two", "All instances where a nominee has won twice.")
      val query_three = Utility.create_user_menu_option(3, "three", "All instances where the winner was from the same party as the previous winner.")
      val query_four = Utility.create_user_menu_option(4, "four", "Election over election change in overall voter participation.")
      val query_five = Utility.create_user_menu_option(5, "five", "All instances where a state has by popular vote switched party.")
      val query_six = Utility.create_user_menu_option(6, "six", "Election over election change in voter participation by party.")
      println("(0) Return to User Menu")

      val choice = scala.io.StdIn.readLine("[INPUT] Enter menu choice: ").trim().toLowerCase()
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
      println("====================================================================================================")
      println("[MENU] Project Two User Menu")
      println("====================================================================================================")
      val query_one = Utility.create_user_menu_option(1, "one", "Top two nominees (presidents, representatives, senators) by year.")
      val query_two = Utility.create_user_menu_option(2, "two", "All instances a district has by popular vote switched party.")
      val query_three = Utility.create_user_menu_option(3, "three", "Election over election change in district voter participation.")
      val query_four = Utility.create_user_menu_option(4, "four", "Breakdown of the vote count, state by state, during the 2020 Presidential Election.")
      val query_five = Utility.create_user_menu_option(5, "five", "All presidential nominees not of Democrat or Republican parties.")
      val query_six = Utility.create_user_menu_option(6, "six", "Sum of votes received by all parties in New York.")
      println("(0) Return to User Menu")

      val choice = scala.io.StdIn.readLine("[INPUT] Enter menu choice: ").trim().toLowerCase()
      choice match {
        case query_one(_*) => Trend.run_top_two_nominees_by_year()
        case query_two(_*) => Trend.run_district_conversions()
        case query_three(_*) => Trend.run_district_eoe_participation()
        case query_four(_*) => Trend.run_2020_presidential_vote_state_breakdown()
        case query_five(_*) => Trend.run_alternative_presidential_nominees()
        case query_six(_*) => Trend.run_new_york_senators_party_sums()
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