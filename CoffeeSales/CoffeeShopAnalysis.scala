import org.apache.spark.sql._
object CoffeeShopAnalysis extends App {
  val Spark = SparkSession.builder().master(master = "local[*]").appName("CoffeeShopAnalysis").getOrCreate()
  import org.apache.log4j.{Level, Logger}
  Logger.getLogger("org").setLevel(Level.OFF)

  val df_salesReciepts = Spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .csv("Data/201904 sales reciepts.csv")
  //df_salesReciepts.printSchema()
  //df_salesReciepts.show(truncate = false)

  val df_cust = Spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .csv("Data/customer.csv")
  //df_cust.printSchema()
  //df_cust.show(false)

  val df_dates = Spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .csv("Data/Dates.csv")
  //df_dates.printSchema()
  //df_dates.show(false)

  val df_generations = Spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .csv("Data/generations.csv")
  //df_generations.printSchema()
  //df_generations.show(false)

  val df_pastryInventory = Spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .csv("Data/pastry inventory.csv")
  //df_pastryInventory.printSchema()
  //df_pastryInventory.show(false)

  val df_product = Spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .csv("Data/product.csv")
  //df_product.printSchema()
  //df_product.show(false)

  val df_salesTarget = Spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .csv("Data/sales targets.csv")
  //df_salesTarget.printSchema()
  //df_salesTarget.show(false)

  val df_salesOutlet = Spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .csv("Data/sales_outlet.csv")
  //df_salesTarget.printSchema()
  //df_salesTarget.show(false)

  //joining customer dataframe with receipts

  val df_customerSales = df_salesReciepts
    .join(df_cust, usingColumn = "customer_id")
  //df_customerSales.printSchema()
  //df_customerSales.show(false)


  //describing the sales in terms of quantities
  //df_customerSales.describe("quantity").show()

  //describing the sales in terms of total dollars billed
  //df_customerSales.describe("line_item_amount").show()

  //sales vs targets of outlets
  val df_salesVtarget1 = df_salesReciepts
    //.groupBy("quantity", "sales_outlet_id")
    .drop("transaction_id", "transaction_date", "transaction_time", "staff_id", "customer_id", "instore_yn", "order", "line_item_id", "product_id", "line_item_amount", "unit_price", "promo_item_yn")
  //df_salesVtarget.printSchema()
    .groupBy("sales_outlet_id").sum().drop("sum(sales_outlet_id")

  val df_salesVtarget2 = df_salesTarget
    .drop("year_month", "beans_goal", "beverage_goal", "food_goal", "merchandise _goal")
    .groupBy("sales_outlet_id").sum().drop("sum(sales_outlet_id)")
  //df_salesVtarget2.show

  val df_combined = df_salesVtarget2
    .join(df_salesVtarget1, "sales_outlet_id").drop("sum(sales_outlet_id)")
    .withColumnRenamed("sales_outlet_id", "Outlet No.")
    .withColumnRenamed("sum(total_goal)", "Goal")
    .withColumnRenamed("sum(quantity", "Sale")

  df_combined.show(false)


}
