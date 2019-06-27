package com.glodon.gdemo1;

import com.glodon.gdemo1.service.Materials;
import com.glodon.gdemo1.utils.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GDemo1Application implements CommandLineRunner {
    @Autowired
    private Materials materials;

    public static String destUrl;
    public static String sourceUrl;
    public static String targetUrl;

    public static void main(String[] args) throws ParseException {
        CommandLineParser parser = new GnuParser();
        org.apache.commons.cli.CommandLine cmd = parser.parse(CommandLine.OPTIONS, args);

        if (cmd.hasOption("t")){
            targetUrl = cmd.getOptionValue("t");
        }
        if (cmd.hasOption("d")){
            destUrl = cmd.getOptionValue("d");
        }
        if (cmd.hasOption("s")){
            sourceUrl = cmd.getOptionValue("s");
        }

        SpringApplication.run(GDemo1Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        materials.copyAndRename();
    }
}
