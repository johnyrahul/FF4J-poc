from pyspark.sql import SparkSession
print("Hello World")
spark = SparkSession.builder \
    .appName("PostgreSQLConnection") \
    .getOrCreate()
jdbc_url = "jdbc:postgresql://mesh-postgres-demo.ce50yhobtvnj.ap-south-1.rds.amazonaws.com:5432/postgres"
connection_properties = {
    "user": "postgres",
    "password": "AjJVTp&wF6a0^3",
    "driver": "org.postgresql.Driver"  # Path to the PostgreSQL JDBC driver JAR
}

table_name = "tracks"

df = spark.read \
    .jdbc(url=jdbc_url, table=table_name, properties=connection_properties)

df.show()

spark.stop()
