package me.zhengjie;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EladminSystemApplicationTests {

    @Test
    public void contextLoads() {
    }

    public static void main(String[] args) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("a", "1");
        map.put("a", "2");
        map.put("b", "3");
        map.put("c", "3");
        map.put("d", null);
        map.put(null, "4");
        System.out.println("HashMap " + map.size());
    }
}

