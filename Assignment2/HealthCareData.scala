import org.apache.spark.sql._
object HealthCareData extends App {
  val spark = SparkSession.builder()
    .master(master = "local[*]")
    .appName("Health Care Data")
    //.enableHiveSupport()
    .getOrCreate()

  import org.apache.log4j.{Level, Logger}
  Logger.getLogger("org").setLevel(Level.OFF)

  val df_ped = spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .csv(path= "data/Department_for_the_Aging__DFTA__Geriatric_Mental_Health_Contracted_Providers.csv")
  df_ped.printSchema()
  df_ped.show(truncate= false)

  println(df_ped.columns.toList)

  //val df_ped2 = df_ped.withColumnRenamed("ProgramName", "Program_Name")
  //df_ped2.show()

  val new_col_names = df_ped.columns.map(c=>c.toLowerCase() + "_new")
  val df_ped3 = df_ped.toDF(new_col_names:_*)
  df_ped3.show()

  df_ped3.write.mode("overwrite").saveAsTable("table1")

  val myQuery = spark.sql("select count(*) from table1")
  println(myQuery)




}
