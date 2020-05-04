package net.agustisanchez.springer.free.books.cli;

import java.util.ArrayList;
import java.util.List;

public class Options {

    private List<Option> options = new ArrayList<>();

    public Option addOption(Option option) {
        options.add(option);
        return option;
    }

    public List<Option> list() {
        return options;
    }
}
