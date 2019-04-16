OPTS_MEMORY="-server -Xms512m -Xmx512m -Xss512k -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m"
nohup java $OPTS_MEMORY -jar yuyue.jar >>nohup.log 2>&1&
