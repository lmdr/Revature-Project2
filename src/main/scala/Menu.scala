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
    println("display_main_menu()")
  }

  def display_admin_menu(): Unit = {
    println("display_admin_menu()")
  }

  def display_user_menu(): Unit = {
    println("display_user_menu()")
  }
}
