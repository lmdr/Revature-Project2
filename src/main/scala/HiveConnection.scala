import org.apache.spark.sql.SparkSession

// HiveConnection object represents SparkSession connection to Hive through Apache Spark
object HiveConnection {
  private var _spark: org.apache.spark.sql.SparkSession = _

  def connect() : Unit = {
    System.setProperty("hadoop.home.dir", "C:\\hadoop")
    _spark = org.apache.spark.sql.SparkSession
      .builder()
      .appName("Project 1")
      .config("spark.master", "local")
      .enableHiveSupport()
      .getOrCreate()
    _spark.sparkContext.setLogLevel("ERROR")
    HiveConnection.create_database()
    HiveConnection.create_data_table()
  }

  def disconnect(): Unit = {
    _spark.stop()
  }

  private def create_database(): Unit = {
    _spark.sql("SHOW DATABASES").show()
    _spark.sql("CREATE DATABASE IF NOT EXISTS project1")
    _spark.sql("USE project1")
    _spark.sql("SHOW TABLES").show()
  }

  private def create_data_table(): Unit = {
    _spark.sql("DROP TABLE IF EXISTS data")
    _spark.sql("CREATE TABLE IF NOT EXISTS data(year INT, state VARCHAR(255), state_po VARCHAR(2), state_fips INT, state_cen INT, state_ic INT, office VARCHAR(255), candidate VARCHAR(255), party_detailed VARCHAR(255), writein BOOLEAN, candidate_votes INT, total_votes INT, version INT, notes VARCHAR(255), party_simplified VARCHAR(255)) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS TEXTFILE")
    _spark.sql("LOAD DATA LOCAL INPATH 'Project1Data_SampleData.csv' INTO TABLE data")
    _spark.sql("SELECT * FROM data").show()
  }

  def run_data_queries(): Unit = {

  }
}