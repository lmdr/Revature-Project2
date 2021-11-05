// Connection object represents SparkSession to Hive through Apache Spark
object Connection {
  def connect() : Unit = {
    println("connect()")
  }

  def disconnect(): Unit = {
    println("disconnect()")
  }

  def getConnection(): Unit = {
    println("getConnection()")
  }
}
