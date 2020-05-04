package net.agustisanchez.springer.free.books;

class Book {

    private String title;

    private String url;

    public Book(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
