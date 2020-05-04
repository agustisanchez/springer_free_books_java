package net.agustisanchez.springer.free.books.cli;

import java.util.Objects;

public class Option {

    private String id;

    private String defaultValue;

    private boolean required;

    private String description;

    public Option(String id, String description, String defaultValue, boolean required) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(description);
        this.id = id;
        this.defaultValue = defaultValue;
        this.required = required;
        this.description = description;
    }

    public Option(String id, String defaultValue, String description) {
        this(id, description, defaultValue, false);
    }

    public String getId() {
        return id;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isRequired() {
        return required;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return Objects.equals(id, option.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
