package com.dataedge

import org.apache.spark.{SparkConf, SparkContext}

object RDDQueries {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("createRDD").setMaster("local")
    val sc = new SparkContext(conf)

    val rdd2 = sc.textFile("/Users/arjunpanyam/Downloads/PROJECT1_PANYAM/src/main/scala/com/dataedge/Project2Data_USPresidents.csv")
    rdd2.foreach(println)

    println("*********TEST********************")

    val states = rdd2.map(_.split(",")(2))

    states.take(5) foreach(println)

    val votes = rdd2.map(_.split(",")(11))

    votes.take(5) foreach(println)

    val stateVotes = rdd2.map{x => x.split(',')}.map{x => (x(0), x(2), x(11))}

    val stateVotes2 = stateVotes.distinct()


    val rdd4 = stateVotes2.filter(x => (x._1 contains "2020"))
    rdd4.foreach(println)


    println("*********FILTER********************")

    // RDD Transformation - Filter

    val filterRDD_GOP = rdd2.filter(x => !(x.contains("REPUBLICAN")))
    val filterRDD_DemsAndGOP = filterRDD_GOP.filter(x => !(x.contains("DEMOCRAT")))


    println("filter test")

    filterRDD_DemsAndGOP.foreach(println)


    // RDD Transformation - Union
    println("******UNION!!!*********")
    val filterUnion = filterRDD_GOP.union(filterRDD_DemsAndGOP)




    //filterUnion.foreach(println)

    /*
    // RDD Transformation - Map
    // Percentage of total votes by state (example)
    val numbersRDD = sc.parallelize(List(1,2,3,4))
    val squareNumbersRDD = numbersRDD.map(x => x*x).collect()
    println("RDD Transformation - Map")
    squareNumbersRDD.foreach(println)

     */

    // RDD Transformation - Map
    // Percentage of total votes by state (example)
    //val rdd4 = stateVotes2.filter(x => (x._1 contains "2020"))

    val votesRDD = stateVotes2.map(x => (x._3))
    println("RDD Transformation - Map (total votes)")

    votesRDD.foreach(println)



    // RDD Action - Collect
    filterRDD_DemsAndGOP.collect()
    println("Collect action:")
    filterRDD_DemsAndGOP.foreach(println)

    // RDD Action - Count
    println("Count action:")
    println(filterRDD_DemsAndGOP.count())

  }

}
