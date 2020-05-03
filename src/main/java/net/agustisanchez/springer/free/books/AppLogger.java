//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.agustisanchez.springer.free.books;

import org.slf4j.helpers.MessageFormatter;

import java.util.Arrays;

public class AppLogger {

    static void log(String message, Object... params) {
        System.out.println(MessageFormatter.format(message, params).getMessage());
    }

    static void error(String message, Object... params) {
        if (params.length > 0 && params[params.length - 1] instanceof Throwable) {
            Throwable t = (Throwable) params[params.length - 1];
            Object[] messageParams = Arrays.copyOf(params, params.length - 1);
            System.err.println(MessageFormatter.format(message, messageParams).getMessage());
            t.printStackTrace(System.err);
        } else {
            System.err.println(MessageFormatter.format(message, params).getMessage());
        }
    }
}
