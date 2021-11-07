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
  }

  def disconnect(): Unit = {
    println("disconnect()")
    _spark.stop()
  }

  def get_connection(): org.apache.spark.sql.SparkSession = {
    println("getConnection()")
    _spark
  }
}