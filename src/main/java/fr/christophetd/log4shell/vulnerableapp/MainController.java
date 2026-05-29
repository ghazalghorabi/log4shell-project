package fr.christophetd.log4shell.vulnerableapp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class MainController {

    private static final Logger logger = LogManager.getLogger("HelloWorld");

    @GetMapping("/")
    public String index(@RequestHeader("X-Api-Version") String apiVersion) {
        if (isSuspicious(apiVersion)) {
            logger.warn("Blocked suspicious API version header");
            return "Blocked suspicious input";
        }
        logger.info("Received a request for API version " + apiVersion);
        return "Hello, world!";
    }

    private boolean isSuspicious(String input) {
        if (input == null) {
            return false;
        }
        return input.contains("${") 
        || input.contains("${jndi:") 
        || input.contains("${ldap:") 
        || input.contains("${rmi:")
        || input.contains("${dns:");
    }

}