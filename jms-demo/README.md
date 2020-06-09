# Overview
This folder contains an SpringBoot project with a JMS example.   
Uses an ActiveMQ message service to move the messages between    
the client (Client1) and the producer (Client2).   

## Prerrequistes
- ActiveMQ 5.8.0 already running (see https://activemq.apache.org/getting-started)
  - http://127.0.0.1:8161/admin/queues.jsp 
  - user: admin
  - pass: admin
  - queue port: 61616
- Java 8

## Utilities
~~~cmd
  netstat -an|find "61616"
~~~