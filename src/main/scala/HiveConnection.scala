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
    _spark.sql("CREATE DATABASE IF NOT EXISTS project1")
    _spark.sql("USE project1")
  }

  private def create_data_table(): Unit = {
    _spark.sql("DROP TABLE IF EXISTS data")
    _spark.sql("CREATE TABLE IF NOT EXISTS data(year INT, state VARCHAR(255), state_po VARCHAR(2), state_fips INT, state_cen INT, state_ic INT, office VARCHAR(255), candidate VARCHAR(255), party_detailed VARCHAR(255), writein BOOLEAN, candidate_votes INT, total_votes INT, version INT, notes VARCHAR(255), party_simplified VARCHAR(255)) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS TEXTFILE")
    _spark.sql("LOAD DATA LOCAL INPATH 'Project1Data_SampleData.csv' INTO TABLE data")
  }

  def run_data_query_one(): Unit = {
    _spark.sql("WITH " +
      "year_sums AS " +
      "(SELECT year, candidate, party_simplified, SUM(candidate_votes) AS candidate_votes, SUM(total_votes) AS total_votes " +
      "FROM data " +
      "WHERE (party_simplified = 'DEMOCRAT' OR party_simplified = 'REPUBLICAN') AND NOT (candidate = 'OTHER' OR candidate = '') " +
      "GROUP BY year, candidate, party_simplified " +
      "ORDER BY year, party_simplified), " +
      "year_votes AS " +
      "(SELECT year, candidate, party_simplified, candidate_votes, total_votes, ROW_NUMBER() OVER (PARTITION BY year ORDER BY candidate_votes DESC) AS row_number " +
      "FROM year_sums) " +
      "SELECT year AS Year, candidate AS Name, party_simplified AS Party, candidate_votes AS Votes, total_votes AS Total, ROUND(candidate_votes / total_votes * 100, 3) AS Percent " +
      "FROM year_votes " +
      "WHERE row_number = 1 " +
      "ORDER BY party_simplified, year").show()
  }

  def run_data_query_two(): Unit = {
    _spark.sql("WITH " +
      "year_sums AS " +
      "(SELECT year, candidate, party_simplified, SUM(candidate_votes) AS candidate_votes, SUM(total_votes) AS total_votes " +
      "FROM data " +
      "WHERE (party_simplified = 'DEMOCRAT' OR party_simplified = 'REPUBLICAN') AND NOT (candidate = 'OTHER' OR candidate = '') " +
      "GROUP BY year, candidate, party_simplified " +
      "ORDER BY year, party_simplified), " +
      "year_votes AS " +
      "(SELECT year, candidate, party_simplified, candidate_votes, total_votes, ROW_NUMBER() OVER (PARTITION BY year ORDER BY candidate_votes DESC) AS row_number " +
      "FROM year_sums), " +
      "year_winners AS (SELECT year, candidate, party_simplified, candidate_votes, total_votes " +
      "FROM year_votes " +
      "WHERE row_number = 1 " +
      "ORDER BY party_simplified, year), " +
      "year_reelects AS " +
      "(SELECT *, ROW_NUMBER() OVER (PARTITION BY candidate ORDER BY year) AS re_elected " +
      "FROM year_winners) " +
      "SELECT candidate AS Name, party_simplified AS Party " +
      "FROM year_reelects " +
      "WHERE re_elected = 2").show()
  }

  def run_data_query_three(): Unit = {
    _spark.sql("WITH " +
      "year_sums AS " +
      "(SELECT year, candidate, party_simplified, SUM(candidate_votes) AS candidate_votes, SUM(total_votes) AS total_votes " +
      "FROM data " +
      "WHERE (party_simplified = 'DEMOCRAT' OR party_simplified = 'REPUBLICAN') AND NOT (candidate = 'OTHER' OR candidate = '') " +
      "GROUP BY year, candidate, party_simplified " +
      "ORDER BY year, party_simplified), " +
      "year_votes AS " +
      "(SELECT year, candidate, party_simplified, candidate_votes, total_votes, ROW_NUMBER() OVER (PARTITION BY year ORDER BY candidate_votes DESC) AS row_number " +
      "FROM year_sums), " +
      "year_lag AS " +
      "(SELECT *, LAG(year) OVER (ORDER BY year) AS lag_year, LAG(candidate) OVER (ORDER BY year) AS lag_name, LAG(party_simplified) OVER (ORDER BY year) AS lag_party " +
      "FROM year_votes " +
      "WHERE row_number = 1) " +
      "SELECT year AS Year, candidate AS Name, party_simplified AS Party, lag_year AS Previous_Year, lag_name AS Previous_Name, lag_party AS Previous_Party " +
      "FROM year_lag " +
      "WHERE party_simplified = lag_party").show()
  }

  def run_data_query_four(): Unit = {
    _spark.sql("WITH " +
      "state_totals AS " +
      "(SELECT year, state, FIRST(total_votes) AS state_total " +
      "FROM data " +
      "GROUP BY year, state)," +
      "year_totals AS " +
      "(SELECT year, SUM(state_total) AS year_total " +
      "FROM state_totals " +
      "WHERE year IS NOT NULL " +
      "GROUP BY year " +
      "ORDER BY year), " +
      "year_lags AS " +
      "(SELECT *, LAG(year_total) OVER (ORDER BY year) AS lag_year " +
      "FROM year_totals) " +
      "SELECT year AS Year, ROUND((year_total - lag_year) / lag_year * 100, 3) AS YoY_Delta " +
      "FROM year_lags " +
      "WHERE lag_year IS NOT NULL").show()
  }

  def run_data_query_five(): Unit = {
    _spark.sql("WITH " +
      "state_totals AS " +
      "(SELECT year, state, candidate, party_simplified, candidate_votes, total_votes, ROW_NUMBER() OVER (PARTITION BY year, state ORDER BY candidate_votes DESC) AS row_number " +
      "FROM data " +
      "WHERE (party_simplified = 'DEMOCRAT' OR party_simplified = 'REPUBLICAN') AND NOT (candidate = 'OTHER' OR candidate = '') " +
      "ORDER BY year, state, party_simplified), " +
      "state_lag AS " +
      "(SELECT year, state, candidate, party_simplified, LAG(party_simplified) OVER (PARTITION BY state ORDER BY year) AS lag_party " +
      "FROM state_totals " +
      "WHERE row_number = 1) " +
      "SELECT year AS Year, candidate AS Name, state AS State, party_simplified AS Party, lag_party AS Previous_Party " +
      "FROM state_lag " +
      "WHERE NOT party_simplified = lag_party AND lag_party IS NOT NULL " +
      "ORDER BY year, state").show(100)
  }

  def run_data_query_six(): Unit = {
    _spark.sql("WITH " +
      "year_names AS " +
      "(SELECT year, party_simplified, SUM(candidate_votes) AS candidate_votes, SUM(total_votes) AS total_votes " +
      "FROM data " +
      "WHERE (party_simplified = 'DEMOCRAT' OR party_simplified = 'REPUBLICAN') AND NOT (candidate = 'OTHER' OR candidate = '') " +
      "GROUP BY year, party_simplified " +
      "ORDER BY year, party_simplified), " +
      "year_percent AS " +
      "(SELECT year, party_simplified, candidate_votes / total_votes AS percent " +
      "FROM year_names), " +
      "year_lag AS " +
      "(SELECT year, party_simplified, percent, LAG(percent) OVER (PARTITION BY party_simplified ORDER BY year) AS lag " +
      "FROM year_percent), " +
      "democrat_yoy AS " +
      "(SELECT year, ROUND((percent - lag) / lag * 100, 3) AS YoY_Delta_Democrat " +
      "FROM year_lag " +
      "WHERE party_simplified = 'DEMOCRAT' AND party_simplified IS NOT NULL), " +
      "republican_yoy AS " +
      "(SELECT year, ROUND((percent - lag) / lag * 100, 3) AS YoY_Delta_Republican " +
      "FROM year_lag " +
      "WHERE party_simplified = 'REPUBLICAN' AND party_simplified IS NOT NULL) " +
      "SELECT democrat_yoy.year AS Year, democrat_yoy.YoY_Delta_Democrat as YoY_Delta_Democrat, republican_yoy.YoY_Delta_Republican as YoY_Delta_Republican " +
      "FROM democrat_yoy " +
      "INNER JOIN republican_yoy ON democrat_yoy.year = republican_yoy.year " +
      "WHERE YoY_Delta_Democrat IS NOT NULL").show()
  }
}