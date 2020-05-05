package net.agustisanchez.springer.free.books;

import net.agustisanchez.springer.free.books.cli.CommandLine;
import net.agustisanchez.springer.free.books.cli.MissingOptionException;
import net.agustisanchez.springer.free.books.cli.Option;
import net.agustisanchez.springer.free.books.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    private static Logger logger = LoggerFactory.getLogger(App.class);

    private static String TABLE_URL = "https://resource-cms.springernature.com/springer-cms/rest/v1/content/17858272/data/v4";

    private static String BASE_URL = "https://link.springer.com/";

    private static String DOI_PREFIX = "http://doi.org/";

    public App() {
    }

    public static void main(String[] args) {

        Options options = new Options();
        Option catOpt = options.addOption(new Option("c", "Category", null, true));
        Option langOpt = options.addOption(new Option("l", "en", "Languages (2-letter ISO code; default \"en\")."));
        Option formatOpt = options.addOption(new Option("f", "pdf", "Document format (Values: pdf, epub. Defaults to \"pdf\")."));
        Option outputOpt = options.addOption(new Option("o", ".", "Output directory (current directory by default)."));

        try {
            CommandLine cmd = CommandLine.parse(options.list(), args);
            cmd.validate();
            new App().run(cmd.getValuesForOption(catOpt),
                    cmd.getValuesForOption(langOpt),
                    cmd.getValuesForOption(formatOpt),
                    cmd.getValueForOption(outputOpt));
        } catch (MissingOptionException e) {
            logger.error("ERROR You need to specify at least one category.");
            System.exit(1);
        } catch (AppException e) {
            logger.error("ERROR {}", e.getMessage());
            System.exit(2);
        } catch (Exception e) {
            logger.error("ERROR {}", e.getClass().getName() + (e.getMessage() != null ? ": " + e.getMessage() : ""), e);
            System.exit(3);
        }

    }

    private void run(List<String> categories, List<String> languages, List<String> formats, String output) throws Exception {

        logger.info("Categories: \"{}\".", categories.stream().collect(Collectors.joining(",")));
        logger.info("Languages: \"{}\".", languages.stream().collect(Collectors.joining(",")));
        logger.info("Formats: \"{}\".", formats.stream().collect(Collectors.joining(",")));
        logger.info("Output dir: \"{}\".", output);

        File outputDir = new File(output);

        if (!outputDir.exists()) {
            logger.info("Creating output directory \"{}\".", outputDir.getAbsolutePath());
            outputDir.mkdirs();
        }

        List<Format> typedFormats = formats.stream().map(f -> Format.valueOf(f.toUpperCase())).collect(Collectors.toList());
        List<Book> books = readTable(categories, languages);
        logger.info("Found {} books.", books.size());
        if (books.isEmpty()) {
            logger.info("No books to download.", books.size());
        } else {
            logger.info("Downloading to  \"{}\".", outputDir.getAbsolutePath());
            BookDownloader downloader = new BookDownloader(BASE_URL, DOI_PREFIX, typedFormats, outputDir);
            books.forEach(book -> {
                logger.info("Downloading book \"{}\".", book.getTitle());
                downloader.download(book);
            });
        }

    }

    private List<Book> readTable(List<String> categories, List<String> languages) throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("textbooks-v5.xlsx");
        return new ExcelReader(inputStream).langs(languages).categories(categories).read();

    }

    @Deprecated
    private List<Book> readTable0(List<String> categories, List<String> languages) throws Exception {
        URL url = new URL(TABLE_URL);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            return new ExcelReader(conn.getInputStream()).langs(languages).categories(categories).read();
        } else {
            throw new AppException("Could not read remote data. HTTP status code was " + responseCode + ".");
        }

    }
}
