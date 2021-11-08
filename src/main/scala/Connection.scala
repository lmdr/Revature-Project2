import org.apache.spark.sql.SparkSession

// Connection object represents SparkSession to Hive through Apache Spark
object Connection {
  private var _spark: org.apache.spark.sql.SparkSession = _

  def connect() : Unit = {
    println("connect()")
    System.setProperty("hadoop.home.dir", "C:\\hadoop")
    _spark = SparkSession
      .builder()
      .appName("Project 1")
      .config("spark.master", "local")
      .enableHiveSupport()
      .getOrCreate()
    //_spark.sparkContext.setLogLevel("ERROR")
    Connection.create_database()
    Connection.create_user_table()
  }

  def disconnect(): Unit = {
    println("disconnect()")
    _spark.stop()
  }

  def get_connection(): org.apache.spark.sql.SparkSession = {
    println("get_connection()")
    _spark
  }

  def create_database(): Unit = {
    _spark.sql("SHOW DATABASES").show()
    _spark.sql("CREATE DATABASE IF NOT EXISTS project1")
    _spark.sql("USE project1")
    _spark.sql("SHOW TABLES").show()
  }

  def create_user_table(): Unit = {
    _spark.sql("CREATE TABLE IF NOT EXISTS users(username VARCHAR(255), password VARCHAR(255), name VARCHAR(255), state VARCHAR(255), year INT, admin BOOLEAN) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS TEXTFILE")
    _spark.sql("SELECT * FROM users").show()
  }
}