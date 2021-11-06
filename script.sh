#!/bin/bash
set -x
MYSQL_PASSWORD=zzz
service mysql start
mkdir /log
sleep 5s 
mysql -uroot -p$MYSQL_PASSWORD -e "CREATE USER 'youssef'@'localhost' IDENTIFIED BY 'password';GRANT ALL PRIVILEGES ON *.* TO 'youssef'@'localhost' WITH GRANT OPTION;"
mysql -uroot -p$MYSQL_PASSWORD -e "CREATE USER 'youssef'@'%' IDENTIFIED BY 'password';GRANT ALL PRIVILEGES ON *.* TO 'youssef'@'%' WITH GRANT OPTION;"
java -Dlog4j.configuration=file:"./application.properties" -jar Timesheet-Jsleaders.war