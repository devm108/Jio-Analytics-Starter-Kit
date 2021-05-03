import org.apache.spark
import org.apache.spark.sql._

object BikeData extends App{

  val Spark = SparkSession.builder()
    .master(master = "local[*]")
    .appName(name = "Bike Data")
    .getOrCreate()

  import org.apache.log4j.{Level, Logger}
  Logger.getLogger(name = "org").setLevel(Level.OFF)

  val df_cust = spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .csv(path= "data/customer.csv")
  df_cust.printSchema()
  df_cust.show(truncate= false)

  val df_transaction = spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .csv(path= "data/transactions.csv")
  df_transaction.printSchema()
  df_transaction.show(truncate= false)

  val df_item = spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .csv(path= "data/item.csv")
  df_transaction.printSchema()
  df_transaction.show(truncate= false)

  val df_payment = spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .csv(path= "data/payment.csv")
  df_transaction.printSchema()
  df_transaction.show(truncate= false)
  
  val df_combined = df_transaction
    .join(df_cust, usingColumn= "cust_id")
    .join(df_item, usingColumn= "item_id")
    .join(df_paymenet, usingColumn= "payment_type")
  df_combined.show(truncate= false)
  
  val df_distinct_cust_item = df_combined.dropDuplicates(col1="cust_id", cols="item_id")
  df_distinct_cust_item.show(truncate= false)

  val df_distinct_cust_payment = df_combined.dropDuplicates(col1="cust_id", cols="payment_type")
  df_distinct_cust_payment.show(truncate= false)
  
  val gds_payment_age = df_distinct_cust_payment
    .groupBy(col1="payment_type")
    .mean(colNames="age")
    .join(df_payment, usingColumn= "payment_type")
    .show(truncate= false)
  
  val gds_item_age = df_distinct_cust_item
    .groupBy(col1="item_id")
    .mean(colNames="age")
    .join(df_item, usingColumn= "item_id")
    .show(truncate= false)

}
