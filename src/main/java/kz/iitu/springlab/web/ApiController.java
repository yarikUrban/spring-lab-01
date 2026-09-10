package kz.iitu.springlab.web;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ApiController {

   
    @GetMapping("/sum")
    public SumResult sum(@RequestParam int a, @RequestParam int b) {
        return new SumResult(a + b, a - b, a * b);
    }

    public record SumResult(int sum, int difference, int product) { }

  
    @GetMapping("/reverse")
    public ReverseResult reverse(@RequestParam String text) {
        String reversed = new StringBuilder(text).reverse().toString();
        return new ReverseResult(reversed, reversed.length());
    }

    public record ReverseResult(String reversed, int length) { }

    
    @GetMapping("/palindrome")
    public PalindromeResult palindrome(@RequestParam String text) {
        String cleaned = text.toLowerCase().replaceAll("\\s+", "");
        String reversed = new StringBuilder(cleaned).reverse().toString();
        return new PalindromeResult(cleaned.equals(reversed));
    }

    public record PalindromeResult(boolean palindrome) { }

  
    @GetMapping("/factorial")
    public FactorialResult factorial(@RequestParam int n) {
        if (n < 0 || n > 20) {
            throw new IllegalArgumentException("n must be between 0 and 20");
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return new FactorialResult(n, result);
    }

    public record FactorialResult(int n, long factorial) { }


    @GetMapping("/temperature")
    public TemperatureResult temperature(@RequestParam double f) {
        double celsius = (f - 32) * 5 / 9;
        double kelvin = celsius + 273.15;
        return new TemperatureResult(f, celsius, kelvin);
    }

    public record TemperatureResult(double fahrenheit, double celsius, double kelvin) { }

    @GetMapping("/stats")
    public StatsResult stats(@RequestParam String numbers) {
        int[] nums = Arrays.stream(numbers.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt)
                .toArray();

        if (nums.length == 0) {
            throw new IllegalArgumentException("numbers must not be empty");
        }

        int min = Arrays.stream(nums).min().orElseThrow();
        int max = Arrays.stream(nums).max().orElseThrow();
        double avg = Arrays.stream(nums).average().orElseThrow();

        return new StatsResult(min, max, avg);
    }

    public record StatsResult(int min, int max, double average) { }

    @GetMapping("/wordcount")
    public WordCountResult wordCount(@RequestParam String text) {
        String trimmed = text.trim();
        String[] words = trimmed.isEmpty() ? new String[0] : trimmed.split("\\s+");

        int wordCount = words.length;
        int charCount = text.length();
        String longest = "";
        for (String w : words) {
            if (w.length() > longest.length()) {
                longest = w;
            }
        }

        return new WordCountResult(wordCount, charCount, longest);
    }

    public record WordCountResult(int words, int characters, String longestWord) { }

    @GetMapping("/case")
    public CaseResult caseConvert(@RequestParam String text) {
        String upper = text.toUpperCase();
        String lower = text.toLowerCase();
        String title = toTitleCase(text);
        return new CaseResult(upper, lower, title);
    }

    private String toTitleCase(String text) {
        StringBuilder sb = new StringBuilder();
        for (String word : text.split("\\s+")) {
            if (word.isEmpty()) continue;
            if (sb.length() > 0) sb.append(" ");
            sb.append(Character.toUpperCase(word.charAt(0)))
              .append(word.substring(1).toLowerCase());
        }
        return sb.toString();
    }

    public record CaseResult(String upper, String lower, String title) { }

    @GetMapping("/fibonacci")
    public FibonacciResult fibonacci(@RequestParam int n) {
        if (n < 1 || n > 50) {
            throw new IllegalArgumentException("n must be between 1 and 50");
        }
        List<Long> fib = new ArrayList<>(n);
        long a = 0, b = 1;
        for (int i = 0; i < n; i++) {
            fib.add(a);
            long next = a + b;
            a = b;
            b = next;
        }
        return new FibonacciResult(n, fib);
    }

    public record FibonacciResult(int n, List<Long> numbers) { }
}