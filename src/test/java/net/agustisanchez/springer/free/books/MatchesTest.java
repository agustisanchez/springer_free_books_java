package net.agustisanchez.springer.free.books;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
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

    @Test
    public void testInterpolation(){
        logger.info("aaa {} bbb{}cc{}", LocalDate.now(), 2, "ww");
        logger.error("aaa {} bbb{}cc{}", LocalDate.now(), 2, "ww", new Exception());
        String test = "aaa {} bbb{}cc{}";
        String[] strings = test.split("\\{\\}");
        System.out.println(Arrays.toString(strings));
        StringBuilder builder = new StringBuilder();

    }
}
