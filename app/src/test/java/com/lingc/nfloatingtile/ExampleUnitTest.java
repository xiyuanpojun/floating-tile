package com.lingc.nfloatingtile;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExampleUnitTest {

    private List<String> getNumber(String title) {
        List<String> list = new ArrayList<>();
        String reg = "\\D+(\\d+)\\D+";
        Pattern p2 = Pattern.compile(reg);
        Matcher m2 = p2.matcher(title);
        while (m2.find()) {
            list.add(m2.group(1));
        }
        return list;
    }

    @Test
    public void addition_isCorrect() {
        System.out.println(Arrays.toString(getNumber("hi123who456abc789").toArray()));
    }
}