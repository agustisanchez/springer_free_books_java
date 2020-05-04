package net.agustisanchez.springer.free.books;

enum Format {
    PDF("content/pdf", ".pdf"), EPUB("download/epub", ".epub");

    private String path;

    private String ext;

    Format(String path, String ext) {
        this.path = path;
        this.ext = ext;
    }

    String getPath() {
        return path;
    }

    String getExt() {
        return ext;
    }
}
