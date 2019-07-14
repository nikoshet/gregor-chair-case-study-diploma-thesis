package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Achilleas Triantafyllou
 */


@SpringBootApplication
@EnableDiscoveryClient
public class ConsulServer {

    public static void main(String[] args) {
        SpringApplication.run(ConsulServer.class, args);
    }

}