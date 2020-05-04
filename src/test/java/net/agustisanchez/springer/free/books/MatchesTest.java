package net.agustisanchez.springer.free.books;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@Ignore
@RunWith(JUnit4.class)
public class MatchesTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void test() {
        List<String> list = Arrays.asList(new String[]{"chemistry".toUpperCase(), "Computer".toUpperCase()});
        System.out.println(list.stream().anyMatch(i -> "chemistry".toUpperCase().contains(i)));
        System.out.println(list.stream().anyMatch(i -> "computer".toUpperCase().contains(i)));
        System.out.println(list.stream().anyMatch(i -> "physics".contains(i)));
    }

}
