# jdbc-tester
Simple command line tool to test a jdbc connection

Usage: java -jar jdbc-tester-0.0.1.jar -url=jdbcurl [-usr=jdbcusr] [-pwd=jdbcpwd] [-drv=driverclass] ...

Example:
> java -jar jdbc-tester-0.0.1.jar -url='jdbc:mysql://jdbcuser:jdbcpasswd@atgramysql04.runningball.local:3306/rball2'
  Loading driver: com.mysql.cj.jdbc.Driver
  Connecting to: jdbc:mysql://jdbcuser:jdbcpasswd@atgramysql04.runningball.local:3306/rball2
  Successfully connected and queried timestamp: 2020-02-27 08:14:12.0
