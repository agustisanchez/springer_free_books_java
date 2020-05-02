package net.agustisanchez.springer.free.books;

class Book {

    private String title;

    private String isbn;

    private String url;

    public Book(String title, String isbn, String url) {
        this.title = title;
        this.isbn = isbn;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
