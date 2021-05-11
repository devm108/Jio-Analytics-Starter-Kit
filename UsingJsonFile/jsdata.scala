import org.apache.spark.sql.SparkSession

object jsData extends App{
  val spark = SparkSession.builder().master(master = "local[*]").appName(name = "jsData").getOrCreate()

  import org.apache.log4j.{Level, Logger}
  Logger.getLogger("org").setLevel(Level.OFF)

  val df_json_grades = spark.read
    //.option("header", "true")
    //.option("inferSchema", "true")
    .option("multiline", "true")
    .json("Data/winemag-data-130k-v2.json")
  
  df_json_grades.drop("description").printSchema()
  df_json_grades.show(false)


  

}
