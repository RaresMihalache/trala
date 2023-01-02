package com.example.producerdevice2;

import com.example.producerdevice2.model.Measurement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class ProducerDevice2Application {


    public static void main(String[] args) throws Exception {
        Connection connection = createRabbitConnection();
        Channel channel = createRabbitChannel(connection);
        Scanner sc = new Scanner(new File("src/main/resources/sensor_data2.csv"));
        readFromCSV(sc, channel);

    }

    public static void readFromCSV(Scanner scanner, Channel channel) throws Exception {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Measurement measurement = new Measurement(LocalDateTime.now().toLocalTime().toString(), "24", line);
            Thread.sleep(500);
            sendDataToQueue("", "sensor", measurement, channel);
        }
    }

    public static String convertToJSONString(Measurement measurement) throws JsonProcessingException {
        // Creating the ObjectMapper object
        ObjectMapper mapper = new ObjectMapper();

        // Converting the Object to JSONString
        String jsonString = mapper.writeValueAsString(measurement);
//        System.out.println(jsonString);
        return jsonString;
    }

    public static Connection createRabbitConnection() throws Exception {
        String uri = System.getenv("CLOUDAMQP_URL");
        if(uri == null) uri = "amqps://jjxruqxp:OFxA_s388IUxC8KvGymve4GmFxvlwlvP@sparrow.rmq.cloudamqp.com/jjxruqxp";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);

        // Recommended settings
        factory.setConnectionTimeout(30000);

        Connection connection = factory.newConnection();

        return connection;
    }

    public static Channel createRabbitChannel(Connection connection) throws Exception {
        Channel channel = connection.createChannel();

        String queue = "sensor";
        boolean durable = false;
        boolean exclusive = false;
        boolean autoDelete = false;

        channel.queueDeclare(queue, durable, exclusive, autoDelete, null);

        return channel;
    }

    public static void sendDataToQueue(String exchangeName, String routingKey, Measurement measurement, Channel channel) throws Exception {
        String message = convertToJSONString(measurement);

        channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
    }

}
