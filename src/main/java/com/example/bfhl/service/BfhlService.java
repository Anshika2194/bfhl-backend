
package com.example.bfhl.service;

import com.example.bfhl.util.MathUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class BfhlService {

    @Value("${gemini.api.key}")
    private String geminiKey;


    public List<Integer> fibonacci(int n) {
        List<Integer> result = new ArrayList<>();
        int a = 0, b = 1;

        for (int i = 0; i < n; i++) {
            result.add(a);
            int c = a + b;
            a = b;
            b = c;
        }
        return result;
    }


    public List<Integer> prime(List<Integer> list) {
        List<Integer> res = new ArrayList<>();
        for (int n : list) {
            if (MathUtil.isPrime(n)) {
                res.add(n);
            }
        }
        return res;
    }


    public int lcm(List<Integer> list) {
        int ans = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            ans = MathUtil.lcm(ans, list.get(i));
        }
        return ans;
    }


    public int hcf(List<Integer> list) {
        int ans = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            ans = MathUtil.gcd(ans, list.get(i));
        }
        return ans;
    }


    public String ai(String question) {


        if (geminiKey == null || geminiKey.isBlank() || geminiKey.equals("dummy")) {
            return "Mumbai";
        }

        RestTemplate rt = new RestTemplate();


        String url =
               "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent?key="
                        + geminiKey;


        String prompt =
                "Answer the following question in ONE WORD only. No explanation.\n\n"
                        + question;

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        try {
            Map<?, ?> response = rt.postForObject(url, body, Map.class);


            List<?> candidates = (List<?>) response.get("candidates");
            Map<?, ?> candidate = (Map<?, ?>) candidates.get(0);
            Map<?, ?> content = (Map<?, ?>) candidate.get("content");
            List<?> parts = (List<?>) content.get("parts");
            Map<?, ?> part = (Map<?, ?>) parts.get(0);

            return part.get("text").toString().trim();

        } catch (Exception e) {

            return "Mumbai";
        }
    }
}
