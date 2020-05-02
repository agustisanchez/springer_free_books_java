package net.agustisanchez.springer.free.books;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AppCommandLine {

    private CommandLine cmd;

    public AppCommandLine(CommandLine cmd) {
        this.cmd = cmd;
    }

    List<String> optionValues(String opt)  {
        return cmd.hasOption(opt) ? Arrays.asList(cmd.getOptionValues(opt)) : Collections.emptyList();
    }

    List<String> optionValues(Option opt)  {
        return optionValues(opt.getOpt());
    }


}
