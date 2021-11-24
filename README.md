# Revature Project 2 : US Election Analysis: 1976 - 2020

A command line interface application that interprets and displays statistics about the US Presidential, Representative, and Senatorial election data available from 1976 to 2020. The application was developed in Scala and utilizes the Spark API to interact with Apache Hive.

### Collaborators

This project was the result of a collaboration between Arjun Panyam, Louis Duller, Marx Bacungan, and Saulo Castillo.

### Data Source

The source for the election data used in this project comes from the MIT Election Lab.
The website can be found here: https://electionlab.mit.edu/data.

### Technology Stack

This application makes use of the following technologies:

Technology | Version
---------- | -------
Hadoop | 3.2
Hive | 3.1.2
Intellij IDEA (Community Edition) | 2021.2.3
MySQL | 8.0.26
SBT | 1.5.5
Scala | 2.12.15
Spark | 3.2.0
Zeppelin | 0.8.0-SNAPSHOT

### Run

Install the above technologies and clone the repository.

Ensure that the following environmental variables for the MySQL user account are set:

```
MYSQL_USER
MYSQL_PASSWORD
```

In the project directory run the following:

```
sbt run
```