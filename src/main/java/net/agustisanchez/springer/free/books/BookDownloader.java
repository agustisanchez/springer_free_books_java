package net.agustisanchez.springer.free.books;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class BookDownloader {

    private static String baseUrl = "https://link.springer.com/";

    private static String prefix = "http://doi.org/";

    private Logger logger = LoggerFactory.getLogger(getClass());

    private File outputDir;

    private List<Format> formats = Collections.singletonList(Format.PDF);

    public BookDownloader(File outputDir) {
        this.outputDir = outputDir;
    }

    BookDownloader formats(List<Format> formats) {
        this.formats = formats;
        return this;
    }

    void download(Book book) {
        formats.forEach(f -> {
            String url = new StringBuilder()
                    .append(baseUrl)
                    .append(f.getPath())
                    .append("/")
                    .append(book.getUrl().replace(prefix, "").replace("/", "%2F"))
                    .append(f.getExt())
                    .toString();
            try {
                URL connUrl = new URL(url);
                HttpsURLConnection conn = (HttpsURLConnection) connUrl.openConnection();
                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    String filename = conn.getHeaderField("content-disposition");
                    if (filename != null && filename.contains("filename=")) {
                        filename = filename.substring("filename=".length());
                    }
                    if (filename == null || filename.isEmpty()) {
                        filename = book.getTitle().replace('/', '_').replace('\\', '_') + f.getExt();
                    }
                    logger.info("Writing \"{}\".", filename);
                    IOUtils.copy(conn.getInputStream(), new FileOutputStream(new File(outputDir, filename)));
                } else if (responseCode == 404) {
                    logger.info("WARN: Book \"{}\" ({}) was not found.", book.getTitle(), f);
                } else {
                    throw new Exception("Response code not OK: " + responseCode);
                }
            } catch (Exception e) {
                logger.error("ERROR {}.", "Could not download: " + url, e);
            }
        });
    }

}
