#!/bin/bash
MYSQL_PASSWORD=zzz
mkdir -p D:/logs/
mysql -uroot -p$MYSQL_PASSWORD -e "CREATE USER 'youssef'@'localhost' IDENTIFIED BY 'password';GRANT ALL PRIVILEGES ON *.* TO 'youssef'@'localhost' WITH GRANT OPTION;"
mysql -uroot -p$MYSQL_PASSWORD -e "CREATE USER 'youssef'@'%' IDENTIFIED BY 'password';GRANT ALL PRIVILEGES ON *.* TO 'youssef'@'%' WITH GRANT OPTION;"
java -Dlog4j.configuration=file:"./application.properties" -jar Timesheet-Jsleaders.war