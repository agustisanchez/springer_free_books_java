package net.agustisanchez.springer.free.books;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class AppCommandLine {

    private CommandLine cmd;

    private Options options;

    private HelpFormatter formatter = new HelpFormatter();

    private AppCommandLine(CommandLine cmd, Options options) {
        this.cmd = cmd;
        this.options = options;
    }

    static AppCommandLine parse(Options options, String[] args) throws ParseException {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);
        return new AppCommandLine(cmd, options);
    }

    List<String> optionValues(String opt)  {
        return cmd.hasOption(opt) ? Arrays.asList(cmd.getOptionValues(opt)) : Collections.emptyList();
    }

    List<String> optionValues(Option opt)  {
        return optionValues(opt.getOpt());
    }

    boolean noOptionsGiven(){
        return cmd.getOptions().length == 0;
    }

    void printHelp() {
        StringWriter sw = new StringWriter();
        formatter.printHelp(new PrintWriter(sw),80,  "java -jar springer.free.books-VERSION.jar [options]", "", options, 5, 2, "");
        sw.append("Example:\n");
        if (System.getProperty("os.name").startsWith("Windows")) {
            sw.append("java -jar springer.free.books-1.0-SNAPSHOT.jar -c chemistry -c \"computer science\" -f pdf -l en -o %HOME%\\Downloads\\springer-books");
        }  else {
            sw.append("java -jar springer.free.books-1.0-SNAPSHOT.jar -c chemistry -c \"computer science\" -f pdf -l en -o ~/Downloads/springer-books");
        }
        AppLogger.log(sw.toString());

    }

}
