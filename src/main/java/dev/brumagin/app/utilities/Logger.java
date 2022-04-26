package dev.brumagin.app.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

/**
 * The Logger Class has helpful functions to log errors and the application state.
 */
public class Logger {

    private Logger(){
    }

    /**
     * Log messages according to the application state
     *
     * @param message the current application state
     * @param level severity level of the log
     */
    public static void log(String message, LogLevel level){

        String logMessage = level.name() +" " +  message + " " + new Date() + "\n";
        String path = System.getProperty("user.dir")+"//expenses.log";
        boolean created;

        try {

            File file = new File(path);

            if(!file.exists()) {
                created = file.createNewFile(); // attempt to create the file if not found
                if(!created)
                    throw new IOException("Could not create file that does not exist: expenses.log\n"+
                    "Please check write access to : user.dir");
            }

            Files.write(Paths.get(path),
                    logMessage.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}