object Main {
  def main(args: Array[String]): Unit = {
    Connection.connect()
    Menu.display_welcome_message()
    Menu.display_main_menu()
    Connection.disconnect()
  }
}