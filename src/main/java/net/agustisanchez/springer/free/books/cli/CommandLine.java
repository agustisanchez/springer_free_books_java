package net.agustisanchez.springer.free.books.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CommandLine {

    private Map<String, Option> optionsMap;

    private Map<String, List<String>> valuesMap;

    public CommandLine(Map<String, Option> optionsMap, Map<String, List<String>> valuesMap) {
        this.optionsMap = optionsMap;
        this.valuesMap = valuesMap;
    }

    public static CommandLine parse(List<Option> options, String[] args) {
        final Map<String, Option> optionsMap = new HashMap<>(options.size());
        final Map<String, List<String>> valuesMap = new HashMap<>();
        options.forEach(opt -> {
            optionsMap.put(opt.getId(), opt);
            valuesMap.put(opt.getId(), new ArrayList<>());
        });
        if (args.length > 1) {
            List<String> discardList = new ArrayList<>();
            List<String> current = discardList;
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if (arg.startsWith("-")) {
                    String id = arg.substring(1);
                    current = valuesMap.getOrDefault(id, discardList);
                } else {
                    current.add(arg);
                }
            }
        }
        valuesMap.forEach((id, list) -> {
            if (list.isEmpty()) {
                Option opt = optionsMap.get(id);
                if (opt.getDefaultValue() != null && !opt.getDefaultValue().isEmpty()) {
                    list.add(opt.getDefaultValue());
                }
            }
        });
        return new CommandLine(optionsMap, valuesMap);
    }

    public void validate() throws MissingOptionException {
        valuesMap.forEach((id, list) -> {
            if (list.isEmpty()) {
                Option opt = optionsMap.get(id);
                if (opt.isRequired() && list.isEmpty()) {
                    throw new MissingOptionException(opt);
                }
            }
        });
    }

    public List<String> getValuesForOption(Option option) {
        return valuesMap.get(option.getId());
    }

    public String getValueForOption(Option option) {
        List<String> list = getValuesForOption(option);
        return list == null ? null : list.isEmpty() ? null : list.get(0);
    }

    public String helpText() {
        StringBuilder builder = new StringBuilder("Options are:");
        if (valuesMap.isEmpty()) {
            builder.append(" -- No options defined");
        } else {
            Iterator<Option> iterator = optionsMap.values().iterator();
            while (iterator.hasNext()) {
                Option option = iterator.next();
                builder.append("   -");
                builder.append(option.getId());
                builder.append(": ");
                builder.append(option.getDescription());
                builder.append("(required=");
                builder.append(option.isRequired());
                builder.append("; default value \"");
                builder.append(option.getDefaultValue());
                builder.append("\")");
            }

        }
        return builder.toString();

    }
}
