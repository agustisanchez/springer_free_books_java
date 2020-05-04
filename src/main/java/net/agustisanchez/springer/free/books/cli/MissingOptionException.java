package net.agustisanchez.springer.free.books.cli;

public class MissingOptionException extends RuntimeException {

    private Option option;

    public MissingOptionException(Option option) {
        this.option = option;
    }

    public Option getOption() {
        return option;
    }
}
