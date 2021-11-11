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
      println("(0) Exit\n")

      val choice = scala.io.StdIn.readLine("[INPUT] Enter menu choice: ").trim().toLowerCase()
      choice match {
        case "1" | "a" | "ad" | "admin" => Menu.display_admin_login_prompt()
        case "2" | "u" | "user" => Menu.display_user_login_prompt()
        case "0" | "e" | "ex" | "exit" | "q" | "quit" => exit = true
        case "i" | "info" | "information" | "w" | "welcome" | "h" | "help" => Menu.display_welcome_message()
        case _ =>
          println("[ERROR] Unrecognized option. Please enter a valid option from the menu.")
        }
      println("")
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
      println("(0) Logout\n")

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
      println("")
    } while (!exit)
  }

  def display_user_menu(): Unit = {
    var exit = false
    do {
      println("[MENU] User Menu")
      println("================================================================================")
      println("(1) Run Query 1...")
      println("(2) Run Query 2...")
      println("(3) Run Query 3...")
      println("(4) Run Query 4...")
      println("(5) Run Query 5...")
      println("(6) Run Query 6...")
      println("(0) Logout\n")

      val choice = scala.io.StdIn.readLine("[INPUT] Enter menu choice: ").trim().toLowerCase()
      choice match {
        case "1" | "r1" | "q1" | "r 1" | "q 1" | "rq1" | "rq 1" | "r q1" | "r q 1" |
             "run1" | "run 1" | "run q1" | "run q 1" |
             "r query1" | "r query 1" | "run query 1" =>
          Trend.run_query(1)
        case "2" | "r2" | "q2" | "r 2" | "q 2" | "rq2" | "rq 2" | "r q2" | "r q 2" |
             "run2" | "run 2" | "run q2" | "run q 2" |
             "r query2" | "r query 2" | "run query 2" =>
          Trend.run_query(2)
        case "3" | "r3" | "q3" | "r 3" | "q 3" | "rq3" | "rq 3" | "r q3" | "r q 3" |
             "run3" | "run 3" | "run q3" | "run q 3" |
             "r query3" | "r query 3" | "run query 3" =>
          Trend.run_query(3)
        case "4" | "r4" | "q4" | "r 4" | "q 4" | "rq4" | "rq 4" | "r q4" | "r q 4" |
             "run4" | "run 4" | "run q4" | "run q 4" |
             "r query4" | "r query 4" | "run query 4" =>
          Trend.run_query(4)
        case "5" | "r5" | "q5" | "r 5" | "q 5" | "rq5" | "rq 5" | "r q5" | "r q 5" |
             "run5" | "run 5" | "run q5" | "run q 5" |
             "r query5" | "r query 5" | "run query 5" =>
          Trend.run_query(5)
        case "6" | "r6" | "q6" | "r 6" | "q 6" | "rq6" | "rq 6" | "r q6" | "r q 6" |
             "run6" | "run 6" | "run q6" | "run q 6" |
             "r query6" | "r query 6" | "run query 6" =>
          Trend.run_query(6)
        case "0" | "e" | "ex" | "exit" | "q" | "quit" | "l" | "lo" | "log" | "out" | "logout" =>
          exit = true
        case "i" | "info" | "information" | "w" | "welcome" | "h" | "help" =>
          Menu.display_welcome_message()
        case _ =>
          println("[ERROR] Unrecognized option. Please enter a valid option from the menu.")
      }
      println("")
    } while (!exit)
  }

  def display_admin_login_prompt(): Unit = {
    println("[INFO] Admin Login...")
    val username = scala.io.StdIn.readLine("[INPUT] Enter your username: ").trim()
    val password = scala.io.StdIn.readLine("[INPUT] Enter your password: ").trim()
    if (!Connection.verify_login(username,password,true)) {
      println("[ERROR] Could not log you in.")
      val account_creation = scala.io.StdIn.readLine("[INPUT] Would you like to create an admin account with the information you provided? (yes/no): ").trim().toLowerCase()
      account_creation match {
        case "y" | "yes" =>
          val name = scala.io.StdIn.readLine("[INPUT] Enter your name: ").trim()
          Connection.create_user(username, password, name, true)
          val login_now = scala.io.StdIn.readLine("[INPUT] Would you like to login now? (yes/no): ").trim().toLowerCase()
          login_now match {
            case "y" | "yes" =>
              if (!Connection.verify_login(username,password,true)) {
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
      Menu.display_admin_menu()
    }
  }

  def display_user_login_prompt(): Unit = {
    println("[INFO] User Login...")
    val username = scala.io.StdIn.readLine("[INPUT] Enter your username: ").trim()
    val password = scala.io.StdIn.readLine("[INPUT] Enter your password: ").trim()
    if (!Connection.verify_login(username, password, false)) {
      println("[ERROR] Could not log you in.")
      val account_creation = scala.io.StdIn.readLine("[INPUT] Would you like to create an user account with the information you provided? (yes/no): ").trim().toLowerCase()
      account_creation match {
        case "y" | "yes" =>
          val name = scala.io.StdIn.readLine("[INPUT] Enter your name: ").trim()
          Connection.create_user(username, password, name, false)
          val login_now = scala.io.StdIn.readLine("[INPUT] Would you like to login now? (yes/no): ").trim().toLowerCase()
          login_now match {
            case "y" | "yes" =>
              if (!Connection.verify_login(username,password,false)) {
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
      Menu.display_user_menu()
    }
  }
}