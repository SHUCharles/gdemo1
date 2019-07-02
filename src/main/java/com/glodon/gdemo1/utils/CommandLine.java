package com.glodon.gdemo1.utils;

import org.apache.commons.cli.Options;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

public class CommandLine {
    public final static Options OPTIONS ;
    static {
        OPTIONS = new Options()
                .addOption("d", true, "destDir")
                .addOption("s", true, "sourceDir")
                .addOption("t", true, "target")
                .addOption("j", true, "JsonDir");
    }
}
