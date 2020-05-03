package net.agustisanchez.springer.free.books;

import org.apache.commons.cli.Options;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class App {

    String tableUrl = "https://resource-cms.springernature.com/springer-cms/rest/v1/content/17858272/data/v4";

    public App() {
    }

    public static void main(String[] args) {

        Options options = new Options();
        options.addOption("c", "category", true, "Category");
        options.addOption("l", "language", true, "Languages (2-letter ISO code).");
        options.addOption("f", "format", true, "Document format (Values: pdf, epub. Defaults to pdf).");
        options.addOption("o", "output", true, "Output directory (current directory by default)");

        try {
            AppCommandLine cmd = AppCommandLine.parse(options, args);
            if (cmd.noOptionsGiven()) {
                cmd.printHelp();
            } else {
                new App().run(cmd.optionValues("c"),
                        cmd.optionValues("l"),
                        cmd.optionValues("f"),
                        cmd.optionValues("o").stream().findFirst());
            }
        } catch (AppException e) {
            AppLogger.error(e.getMessage());
            System.exit(-1);
        } catch (Exception e) {
            AppLogger.error(e.getClass().getName() + (e.getMessage() != null ? ": " + e.getMessage() : ""));
            System.exit(-2);
        }

    }

    private void run(List<String> categories, List<String> languages, List<String> formats, Optional<String> outputOpt) throws Exception {
        File output = new File(outputOpt.orElse("."));
        // AppLogger.log("Categories " + categories + " languages " + languages + " formats " + formats + " output " + output.getAbsolutePath());
        if (!output.exists()) {
            AppLogger.log("Creating output directory \"{}\".", output.getAbsolutePath());
            output.mkdirs();
        }
        List<Format> typedFormats = formats.stream().map(f -> Format.valueOf(f.toUpperCase())).collect(Collectors.toList());
        List<Book> books = readTable(categories, languages);
        AppLogger.log("Found {} books.", books.size());
        AppLogger.log("Downloading to  \"{}\".", output.getAbsolutePath());
        BookDownloader downloader = new BookDownloader(output);
        downloader.formats(typedFormats);
        books.forEach(book -> {
            AppLogger.log("Downloading book \"{}\".", book.getTitle());
            downloader.download(book);
        });

    }

    private List<Book> readTable(List<String> categories, List<String> languages) throws Exception {
        URL url = new URL(tableUrl);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            return new ExcelReader(conn.getInputStream()).langs(languages).categories(categories).read();
        } else {
            throw new AppException("Could not read remote data. HTTP status code was " + responseCode + ".");
        }

    }
}
