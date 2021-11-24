import java.io.File
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{SQLContext, SparkSession}

// HiveConnection object represents SparkSession connection to Hive through Apache Spark
object HiveConnection {
  private var _spark: org.apache.spark.sql.SparkSession = _

  def connect() : Unit = {
    // Set logger level to off
    org.apache.log4j.Logger.getLogger("akka").setLevel(org.apache.log4j.Level.OFF)
    org.apache.log4j.Logger.getLogger("hive").setLevel(org.apache.log4j.Level.OFF)
    org.apache.log4j.Logger.getLogger("org").setLevel(org.apache.log4j.Level.OFF)

    // Configure Apache Spark
    System.setProperty("hadoop.home.dir", "C:\\hadoop")
    _spark = org.apache.spark.sql.SparkSession
      .builder()
      .appName("Project 2")
      .config("spark.master", "local")
      .config("spark.sql.warehouse.dir", new java.io.File("spark-warehouse").getAbsolutePath)
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .enableHiveSupport()
      .getOrCreate()

    // Set up Hive datastore for queries
    HiveConnection.create_database()
    HiveConnection.create_presidents_table()
    HiveConnection.create_representatives_table()
    HiveConnection.create_senators_table()
  }

  def disconnect(): Unit = {
    _spark.stop()
  }

  /* BEGIN DATA DEFINITION QUERIES */
  private def create_database(): Unit = {
    println("[INFO] Creating Hive data store.")
    _spark.sql("CREATE DATABASE IF NOT EXISTS project2")
    _spark.sql("USE project2")
    println("[INFO] Created Hive data store.")
  }

  private def create_presidents_table(): Unit = {
    println("[INFO] Creating presidents table.")
    _spark.sql("CREATE TABLE IF NOT EXISTS presidents_staging " +
      "(year INT, state VARCHAR(255), state_po VARCHAR(2), state_fips INT, state_cen INT, state_ic INT, " +
      "office VARCHAR(255), candidate VARCHAR(255), party_detailed VARCHAR(255), writein BOOLEAN, " +
      "candidate_votes INT, total_votes INT, version INT, notes VARCHAR(255), party_simplified VARCHAR(255)) " +
      "ROW FORMAT DELIMITED FIELDS TERMINATED BY ','")
    _spark.sql("LOAD DATA LOCAL INPATH 'input/Project2Data_USPresidents.csv' INTO TABLE presidents_staging")
    _spark.sql("DROP TABLE IF EXISTS presidents")
    _spark.sql("CREATE TABLE IF NOT EXISTS presidents " +
      "(state VARCHAR(255) COMMENT 'State name.', " +
      "state_po VARCHAR(2) COMMENT 'U.S. postal code state abbreviation.', " +
      "state_fips INT COMMENT 'State FIPS code.', " +
      "state_cen INT COMMENT 'U.S. Census state code.', " +
      "state_ic INT COMMENT 'ICPSR state code.', " +
      "office VARCHAR(255) COMMENT 'U.S. PRESIDENT.', " +
      "candidate VARCHAR(255) COMMENT 'Name of the candidate as it appears in the House Clerk report.', " +
      "party_detailed VARCHAR(255) COMMENT 'Party of the candidate as it appears in the House Clerk report.', " +
      "writein BOOLEAN COMMENT 'Whether votes are associated with a write-in candidate.', " +
      "candidate_votes INT COMMENT 'Votes received by this candidate for this particular party.', " +
      "total_votes INT COMMENT 'Total number of votes cast for this election.', " +
      "version INT COMMENT 'Date on which dataset as finalized.', " +
      "notes VARCHAR(255) COMMENT 'Additional notes.', " +
      "party_simplified VARCHAR(255) COMMENT 'The entries will be one of: DEMOCRAT, REPUBLICAN, LIBERTARIAN, OTHER.') " +
      "COMMENT 'The data file `1976-2016-president` contains constituency (state-level) returns for elections " +
      "to the U.S. presidency from 1976 to 2016. The data source is the document \"[Statistics of the Congressional " +
      "Election](http://history.house.gov/Institution/Election-Statistics/Election-Statistics/),\" published " +
      "biennially by the Clerk of the U.S. House of Representatives.' " +
      "PARTITIONED BY (year INT COMMENT 'Year in which election was held.') " +
      "ROW FORMAT DELIMITED FIELDS TERMINATED BY ','")
    _spark.sql("INSERT OVERWRITE TABLE presidents PARTITION (year) " +
      "SELECT state, state_po, state_fips, state_cen, state_ic, office, candidate, party_detailed, writein, " +
      "candidate_votes, total_votes, version, notes, party_simplified, year FROM presidents_staging")
    _spark.sql("DROP TABLE IF EXISTS presidents_staging")
    _spark.sql("SELECT year, state, state_po, state_fips, state_cen, state_ic, office, candidate, " +
      "party_detailed, writein, candidate_votes, total_votes, version, notes, party_simplified FROM presidents").cache()
    println("[INFO] Created presidents table.")
  }

  private def create_representatives_table(): Unit = {
    println("[INFO] Creating representatives table.")
    _spark.sql("CREATE TABLE IF NOT EXISTS representatives_staging " +
      "(year INT, state VARCHAR(255), state_po VARCHAR(2), state_fips INT, state_cen INT, state_ic INT, " +
      "office VARCHAR(255), district INT, stage VARCHAR(255), runoff BOOLEAN, special BOOLEAN, " +
      "candidate VARCHAR(255), party_detailed VARCHAR(255), writein BOOLEAN, mode VARCHAR(255), " +
      "candidate_votes INT, total_votes INT, unofficial BOOLEAN, version INT, fusion_ticket BOOLEAN) " +
      "ROW FORMAT DELIMITED FIELDS TERMINATED BY ','")
    _spark.sql("LOAD DATA LOCAL INPATH 'input/Project2Data_USRepresentatives.csv' " +
      "INTO TABLE representatives_staging")
    _spark.sql("DROP TABLE IF EXISTS representatives")
    _spark.sql("CREATE TABLE IF NOT EXISTS representatives " +
      "(state VARCHAR(255) COMMENT 'State name.', " +
      "state_po VARCHAR(2) COMMENT 'U.S. postal code state abbreviation.', " +
      "state_fips INT COMMENT 'State FIPS code.', " +
      "state_cen INT COMMENT 'U.S. Census state code.', " +
      "state_ic INT COMMENT 'ICPSR state code.', " +
      "office VARCHAR(255) COMMENT 'U.S. House.', " +
      "district INT COMMENT 'District number (at-large districts are coded as 0).', " +
      "stage VARCHAR(255) COMMENT 'The entries will be one of: gen = general election, pri = primary election.', " +
      "runoff BOOLEAN COMMENT 'Whether the election was a runoff.', " +
      "special BOOLEAN COMMENT 'Whether election was a special election.', " +
      "candidate VARCHAR(255) COMMENT 'Name of the candidate as it appears in the House Clerk report.', " +
      "party_detailed VARCHAR(255) COMMENT 'Party of the candidate as it appears in the House Clerk report.', " +
      "writein BOOLEAN COMMENT 'Whether votes are associated with a write-in candidate.', " +
      "mode VARCHAR(255) COMMENT 'Mode of voting.', " +
      "candidate_votes INT COMMENT 'Votes received by this candidate for this particular party.', " +
      "total_votes INT COMMENT 'Total number of votes cast for this election.', " +
      "unofficial BOOLEAN COMMENT 'Whether reported results are unofficial.', " +
      "version INT COMMENT 'Date on which dataset as finalized.', " +
      "fusion_ticket BOOLEAN COMMENT 'Whether the given candidate is running on a fusion party ticket, which " +
      "will in turn mean that a candidate will appear multiple times, but by different parties.') " +
      "COMMENT 'The data file `1976-2020-house` contains constituency (district) returns for elections to the " +
      "U.S. House of Representatives from 1976 to 2020. The data source is the document \"[Statistics of the " +
      "Congressional Election](https://history.house.gov/Institution/Election-Statistics/),\" published " +
      "biennially by the Clerk of the U.S. House of Representatives.'" +
      "PARTITIONED BY (year INT COMMENT 'Year in which election was held.') " +
      "ROW FORMAT DELIMITED FIELDS TERMINATED BY ','")
    _spark.sql("INSERT OVERWRITE TABLE representatives PARTITION (year) " +
      "SELECT state, state_po, state_fips, state_cen, state_ic, office, district, stage, runoff, special, candidate, " +
      "party_detailed, writein, mode, candidate_votes, total_votes, unofficial, version, fusion_ticket, year " +
      "FROM representatives_staging")
    _spark.sql("DROP TABLE IF EXISTS representatives_staging")
    _spark.sql("SELECT year, state, state_po, state_fips, state_ic, office, district, stage, runoff, special, " +
      "candidate, party_detailed, writein, mode, candidate_votes, total_votes, unofficial, version, fusion_ticket " +
      "FROM representatives").cache()
    println("[INFO] Created representatives table.")
  }

  private def create_senators_table(): Unit = {
    println("[INFO] Creating senators table.")
    _spark.sql("CREATE TABLE IF NOT EXISTS senators_staging " +
      "(year INT, state VARCHAR(255), state_po VARCHAR(2), state_fips INT, state_cen INT, state_ic INT, " +
      "office VARCHAR(255), district VARCHAR(255), stage VARCHAR(255), special BOOLEAN, candidate VARCHAR(255), " +
      "party_detailed VARCHAR(255), writein BOOLEAN, mode BOOLEAN, candidate_votes INT, total_votes INT, " +
      "unofficial BOOLEAN, version INT, party_simplified VARCHAR(255)) " +
      "ROW FORMAT DELIMITED FIELDS TERMINATED BY ','")
    _spark.sql("LOAD DATA LOCAL INPATH 'input/Project2Data_USSenators.csv' INTO TABLE senators_staging")
    _spark.sql("DROP TABLE IF EXISTS senators")
    _spark.sql("CREATE TABLE IF NOT EXISTS senators " +
      "(state VARCHAR(255) COMMENT 'State name.', " +
      "state_po VARCHAR(2) COMMENT 'U.S. postal code state abbreviation.', " +
      "state_fips INT COMMENT 'State FIPS code.', " +
      "state_cen INT COMMENT 'U.S. Census state code.', " +
      "state_ic INT COMMENT 'ICPSR state code.', " +
      "office VARCHAR(255) COMMENT 'U.S. SENATE.', " +
      "district VARCHAR(255) COMMENT 'Statewide.', " +
      "stage VARCHAR(255) COMMENT 'The entries will be one of: " +
      "gen = general election, runoff = runoff election, pri = primary election.', " +
      "special BOOLEAN COMMENT 'Whether election was a special election.', " +
      "candidate VARCHAR(255) COMMENT 'Name of the candidate as it appears in the House Clerk report.', " +
      "party_detailed VARCHAR(255) COMMENT 'Party of the candidate as it appears in the House Clerk report.', " +
      "writein BOOLEAN COMMENT 'Whether votes are associated with a write-in candidate.', " +
      "mode VARCHAR(255) COMMENT 'Mode of voting.', " +
      "candidate_votes INT COMMENT 'Votes received by this candidate for this particular party.', " +
      "total_votes INT COMMENT 'Total number of votes cast for this election.', " +
      "unofficial BOOLEAN COMMENT 'Whether reported results are unofficial.', " +
      "version INT COMMENT 'Date on which dataset as finalized.', " +
      "party_simplified VARCHAR(255) COMMENT 'The entries will be one of: DEMOCRAT, REPUBLICAN, LIBERTARIAN, OTHER') " +
      "COMMENT 'The data file `1976-2018-senate` contains constituency (state-level) returns for elections " +
      "to the U.S. Senate from 1976 to 2018. The data source is the document \"[Statistics of the Congressional " +
      "Election](http://history.house.gov/Institution/Election-Statistics/Election-Statistics/),\" published " +
      "biennially by the Clerk of the U.S. House of Representatives.' " +
      "PARTITIONED BY (year INT COMMENT 'Year in which election was held') " +
      "ROW FORMAT DELIMITED FIELDS TERMINATED BY ','")
    _spark.sql("INSERT OVERWRITE TABLE senators PARTITION (year) " +
      "SELECT state, state_po, state_fips, state_cen, state_ic, office, district, stage, special, candidate, " +
      "party_detailed, writein, mode, candidate_votes, total_votes, unofficial, version, party_simplified, year " +
      "FROM senators_staging")
    _spark.sql("DROP TABLE IF EXISTS senators_staging")
    _spark.sql("SELECT year, state, state_po, state_fips, state_cen, state_ic, office, " +
      "district, stage, special, candidate, party_detailed, writein, mode, candidate_votes, total_votes, " +
      "unofficial, version, party_simplified FROM senators").cache()
    println("[INFO] Created senators table.")
  }
  /* END DATA DEFINITION QUERIES */

  /* BEGIN DF FACTORIES */
  def make_presidents_dataframe(): org.apache.spark.sql.DataFrame = {
    _spark.sql("SELECT year, state, state_po, state_fips, state_cen, state_ic, office, candidate, " +
      "party_detailed, writein, candidate_votes, total_votes, version, notes, party_simplified FROM presidents")
  }

  def make_representatives_dataframe(): org.apache.spark.sql.DataFrame = {
    _spark.sql("SELECT year, state, state_po, state_fips, state_ic, office, district, stage, runoff, special, " +
      "candidate, party_detailed, writein, mode, candidate_votes, total_votes, unofficial, version, fusion_ticket " +
      "FROM representatives")
  }

  def make_senators_dataframe(): org.apache.spark.sql.DataFrame = {
    _spark.sql("SELECT year, state, state_po, state_fips, state_cen, state_ic, office, " +
      "district, stage, special, candidate, party_detailed, writein, mode, candidate_votes, total_votes, " +
      "unofficial, version, party_simplified FROM senators")
  }
  /* END DF FACTORIES */

  /* BEGIN PROJECT ONE QUERIES */
  def run_data_query_one(): Unit = {
    _spark.sql("WITH " +
      "year_sums AS " +
      "(SELECT year, candidate, party_simplified, SUM(candidate_votes) AS candidate_votes, SUM(total_votes) AS total_votes " +
      "FROM presidents " +
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
      "FROM presidents " +
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
      "FROM presidents " +
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
      "FROM presidents " +
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
      "SELECT year AS Year, ROUND((year_total - lag_year) / lag_year * 100, 3) AS EoE_Delta " +
      "FROM year_lags " +
      "WHERE lag_year IS NOT NULL").show()
  }

  def run_data_query_five(): Unit = {
    _spark.sql("WITH " +
      "state_totals AS " +
      "(SELECT year, state, candidate, party_simplified, candidate_votes, total_votes, ROW_NUMBER() OVER (PARTITION BY year, state ORDER BY candidate_votes DESC) AS row_number " +
      "FROM presidents " +
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
    // Could have used window frames to simplify moving average calculation for 2024, 2028, 2032 elections
    _spark.sql("WITH " +
      "year_names AS " +
      "(SELECT year, party_simplified, SUM(candidate_votes) AS candidate_votes, SUM(total_votes) AS total_votes " +
      "FROM presidents " +
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
      "(SELECT year, (percent - lag) / lag * 100 AS eoe_d_d " +
      "FROM year_lag " +
      "WHERE party_simplified = 'DEMOCRAT' AND party_simplified IS NOT NULL), " +
      "republican_yoy AS " +
      "(SELECT year, (percent - lag) / lag * 100 AS eoe_d_r " +
      "FROM year_lag " +
      "WHERE party_simplified = 'REPUBLICAN' AND party_simplified IS NOT NULL), " +
      "eoe AS " +
      "(SELECT democrat_yoy.year, democrat_yoy.eoe_d_d, republican_yoy.eoe_d_r " +
      "FROM democrat_yoy " +
      "INNER JOIN republican_yoy ON democrat_yoy.year = republican_yoy.year " +
      "WHERE eoe_d_d IS NOT NULL), " +
      "select_1 AS " +
      "(SELECT * " +
      "FROM eoe " +
      "ORDER BY year DESC " +
      "LIMIT 3), " +
      "eoe_1 AS " +
      "(SELECT 2024 AS year, AVG(eoe_d_d) AS eoe_d_d, AVG(eoe_d_r) AS eoe_d_r " +
      "FROM select_1), " +
      "union_1 AS " +
      "(SELECT * " +
      "FROM eoe " +
      "UNION " +
      "SELECT * " +
      "FROM eoe_1), " +
      "select_2 AS " +
      "(SELECT * " +
      "FROM union_1 " +
      "ORDER BY year DESC " +
      "LIMIT 3), " +
      "eoe_2 AS " +
      "(SELECT 2028 AS year, AVG(eoe_d_d) AS eoe_d_d, AVG(eoe_d_r) AS eoe_d_r " +
      "FROM select_2), " +
      "union_2 AS " +
      "(SELECT * " +
      "FROM union_1 " +
      "UNION " +
      "SELECT * " +
      "FROM eoe_2), " +
      "select_3 AS " +
      "(SELECT * " +
      "FROM union_2 " +
      "ORDER BY year DESC " +
      "LIMIT 3), " +
      "eoe_3 AS " +
      "(SELECT 2032 AS year, AVG(eoe_d_d) AS eoe_d_d, AVG(eoe_d_r) AS eoe_d_r " +
      "FROM select_3), " +
      "union_3 AS " +
      "(SELECT * " +
      "FROM union_2 " +
      "UNION " +
      "SELECT * " +
      "FROM eoe_3) " +
      "SELECT year AS Year, ROUND(eoe_d_d, 3) AS EoE_Delta_Democrat, ROUND(eoe_d_r, 3) AS EoE_Delta_Republican " +
      "FROM union_3 " +
      "ORDER BY year").show()
  }
  /* END PROJECT ONE QUERIES */

  /*  BEGIN PROJECT TWO QUERIES */
  // Utility function
  def is_valid_state(state: String): Boolean = {
    !HiveConnection.make_representatives_dataframe().where(s"state = '$state'").limit(1).isEmpty
  }

  // RDD
  def run_2020_presidential_vote_state_breakdown(): Unit = {
    val rdd2 = _spark.sparkContext.textFile("input/Project2Data_USPresidents.csv")
    val stateVotes = rdd2.map{x => x.split(',')}.map{x => (x(0), x(2), x(11))}
    val stateVotes2 = stateVotes.distinct()
    val rdd4 = stateVotes2.filter(x => (x._1 contains "2020"))
    val rdd5 = rdd4.sortBy(_._2)
    rdd5.foreach(println)
    println("Breakdown of the vote count, state by state, during the 2020 Presidential Election" + '\n' )
  }

  // RDD
  def run_alternative_presidential_nominees(): Unit = {
    var rdd = _spark.sparkContext.textFile("input/Project2Data_USPresidents.csv")
    val header = rdd.first()
    rdd = rdd.filter(row => row != header)
    val filterRDD_GOP = rdd.filter(x => !(x.contains("REPUBLICAN")))
    val filterRDD_DemsAndGOP = filterRDD_GOP.filter(x => !(x.contains("DEMOCRAT")))
    val parties = filterRDD_DemsAndGOP.map{x => x.split(',')}.map{x => ( x(8), x(7), x(0))}
    val partiesDistinct = parties.distinct()
    val partiesSorted = partiesDistinct.sortBy(r => (r._1, r._2, r._3))
    partiesSorted.foreach(println)
    println('\n' + "A list of all non-Democratic and non-Republican presidential candidates from 1976 to 2020 ")
    println("In total, " +filterRDD_DemsAndGOP.count() + " presidential candidates have ran that neither identifies as a Democrat or a Republican" + '\n' )
  }

  // SQL and DF Query
  def run_top_two_nominees_by_year(state: String): Unit = {
    // Create windows
    val ranking_window = org.apache.spark.sql.expressions.Window
      .partitionBy("year")
      .orderBy(org.apache.spark.sql.functions.col("candidate_votes").desc)
    val ranking_window_representatives = org.apache.spark.sql.expressions.Window
      .partitionBy("year", "district")
      .orderBy(org.apache.spark.sql.functions.col("candidate_votes").desc)

    // Create queries and display results
    HiveConnection.make_presidents_dataframe()
      .where(s"state = '$state'")
      .withColumn("ranking", org.apache.spark.sql.functions.row_number().over(ranking_window))
      .where("ranking <= 2")
      .withColumn("percent", org.apache.spark.sql.functions.col("candidate_votes") / org.apache.spark.sql.functions.col("total_votes") * 100)
      .orderBy("year", "party_detailed")
      .select("year", "office", "candidate", "party_detailed", "percent")
      .withColumnRenamed("year", "Year")
      .withColumnRenamed("office", "Office")
      .withColumnRenamed("candidate", "Nominee")
      .withColumnRenamed("party_detailed", "Party")
      .withColumnRenamed("percent", "Percent")
      .show(50)
    HiveConnection.make_representatives_dataframe()
      .where(s"state = '$state'")
      .withColumn("ranking", org.apache.spark.sql.functions.row_number().over(ranking_window_representatives))
      .where("ranking <= 2")
      .withColumn("percent", org.apache.spark.sql.functions.col("candidate_votes") / org.apache.spark.sql.functions.col("total_votes") * 100)
      .orderBy("year", "district", "party_detailed")
      .select("year", "office", "district", "candidate", "party_detailed", "percent")
      .withColumnRenamed("year", "Year")
      .withColumnRenamed("office", "Office")
      .withColumnRenamed("district", "District")
      .withColumnRenamed("candidate", "Nominee")
      .withColumnRenamed("party_detailed", "Party")
      .withColumnRenamed("percent", "Percent")
      .show(2544)
    HiveConnection.make_senators_dataframe()
      .where(s"state = '$state'")
      .withColumn("ranking", org.apache.spark.sql.functions.row_number().over(ranking_window))
      .where("ranking <= 2")
      .withColumn("percent", org.apache.spark.sql.functions.col("candidate_votes") / org.apache.spark.sql.functions.col("total_votes") * 100)
      .orderBy("year", "party_detailed")
      .select("year", "office", "candidate", "party_detailed", "percent")
      .withColumnRenamed("year", "Year")
      .withColumnRenamed("office", "Office")
      .withColumnRenamed("candidate", "Nominee")
      .withColumnRenamed("party_detailed", "Party")
      .withColumnRenamed("percent", "Percent")
      .show(50)
  }

  // SQL and DF Query
  def run_district_conversions(state: String): Unit = {
    // Create windows
    val ranking_window = org.apache.spark.sql.expressions.Window
      .partitionBy("year", "district")
      .orderBy(org.apache.spark.sql.functions.col("candidate_votes").desc)
    val lag_window = org.apache.spark.sql.expressions.Window
      .partitionBy("district")
      .orderBy("year")

    // Create queries
    val district_conversion = HiveConnection.make_representatives_dataframe()
      .where(s"state = '$state'")
      .withColumn("ranking", org.apache.spark.sql.functions.row_number().over(ranking_window))
      .where("ranking = 1")
      .withColumn("party_lag", org.apache.spark.sql.functions.lag("party_detailed", 1).over(lag_window))
      .select("year", "district", "party_detailed", "party_lag")
      .where("NOT party_detailed = party_lag")
      .orderBy("year", "district")
      .withColumnRenamed("year", "Year")
      .withColumnRenamed("district", "District")
      .withColumnRenamed("party_detailed", "Party")
      .withColumnRenamed("party_lag", "Previous_Party")
    val count_conversion = district_conversion
      .groupBy("District")
      .agg(org.apache.spark.sql.functions.count("District"))
      .orderBy(org.apache.spark.sql.functions.col("count(District)").desc, org.apache.spark.sql.functions.col("District"))
      .withColumnRenamed("count(District)", "Count_Conversions")

    // Display results
    district_conversion.show(100)
    count_conversion.show(50)
  }

  // SQL and DF Query
  def run_district_eoe_participation(state: String): Unit = {
    // Create window
    val lag_window = org.apache.spark.sql.expressions.Window
      .partitionBy("district")
      .orderBy("year")

    // Create query
    val participation = HiveConnection.make_representatives_dataframe()
      .where(s"state = '$state'")
      .groupBy("year", "district")
      .agg(org.apache.spark.sql.functions.max("total_votes"))
      .withColumn("lag_votes", org.apache.spark.sql.functions.lag("max(total_votes)", 1).over(lag_window))
      .withColumn("eoe",
        (org.apache.spark.sql.functions.col("max(total_votes)") -
          org.apache.spark.sql.functions.col("lag_votes")) /
          org.apache.spark.sql.functions.col("lag_votes") * 100)
      .select("year", "district", "eoe")
      .withColumnRenamed("year", "Year")
      .withColumnRenamed("district", "District")
      .withColumnRenamed("eoe", "EoE_Delta")

    // Display results
    participation.show(400)
  }

  // SQL, RDD, and DF Query
  def run_new_york_senators_party_sums(): Unit = {
    val US_Senators_DF = _spark.read.format("csv").option("header", true).load("input/Project2Data_USSenators.csv")
    val NY_Senator_DF = US_Senators_DF.filter(US_Senators_DF("state") === "NEW YORK")

    val party = _spark.sparkContext.parallelize(Seq("REPUBLICAN", "SOCIALIST WORKERS", "LIBERAL", "CONSERVATIVE", "LABOR" ,"" +
      "COMMUNIST", "LIBERTARIAN", "DEMOCRAT", "FREE LIBERTARIAN", "RIGHT TO LIFE", "NEW ALLIANCE", "WORKERS WORLD", "INDEPENDENT PROGRESSIVE LINE", "" +
      "NATURAL LAW", "INDEPENDENCE FUSION", "GREEN", "MARIJUANA REFORM", "CONSTITUTION", "INDEPENDENCE", "BUILDERS", "WORKING FAMILIES", "" +
      "SOCIALIST EQUALITY", "RENT IS 2 DAMN HIGH", "TAX REVOLT", "ANTI-PROHIBITION", "COMMON SENSE", "REFORM", "WOMEN'S EQUALITY"))

    // So we can use toDF()
    val sqlContext = new org.apache.spark.sql.SQLContext(_spark.sparkContext)
    import sqlContext.implicits._

    val party_DF = party.toDF("Party")
    party_DF.cache()
    val records = party_DF.join(NY_Senator_DF, org.apache.spark.sql.functions.col("party_detailed") === org.apache.spark.sql.functions.col("Party"))
    records.createOrReplaceTempView("records")
    val final_DF = _spark.sql("SELECT SUM(candidatevotes), Party FROM records GROUP BY Party ORDER BY SUM(candidatevotes) DESC")
    final_DF.show(40)
  }
  /* END PROJECT TWO QUERIES */
}