package textprocessors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SentimentAnalisys implements TextProcessor {
    static final String ANSWER_TO_SPEECH = "Люблю когда люди говорят";
    static final String ANSWER_TO_NEGATIVE = "Не будь таким негативным";
    static final String ANSWER_TO_SKIP = "Не знаю что и сказать...";
    static final String ANSWER_TO_NEUTRAL = "Интересно было услышать";
    static final String ANSWER_TO_POSITIVE = "Звучит позитивно!";

    enum Sentiment {
        SPEECH("speech"),
        NEGATIVE("negative"),
        SKIP("skip"),
        NEUTRAL("neutral"),
        POSITIVE("positive");

        final private String text;

        Sentiment(String text) {
            this.text = text;
        }

        public static Sentiment fromString(String text) {
            for (Sentiment b : Sentiment.values()) {
                if (b.text.equalsIgnoreCase(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    final private ObjectMapper mapper;
    final private MapType mapType;
    final private File sentimentScript;

    public SentimentAnalisys() {
        mapper = new ObjectMapper();
        TypeFactory typeFactory = mapper.getTypeFactory();
        mapType = typeFactory.constructMapType(HashMap.class, String.class, Double.class);
        sentimentScript = new File("src/main/resources/sentiment.py");
    }

    public String Process(String input) {
        if(input == null || input.length() == 0) {
            return "Input cannot be empty";
        }
        try {
            Process p = Runtime.getRuntime().exec("python " + sentimentScript.getAbsolutePath() + " \""+input+"\"");
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s = stdInput.readLine();
            if (s != null) {
                try {
                    Map<String, Double> weights = mapper.readValue(s, mapType);
                    return formAnswerBySentimentsWeight(weights);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e) {
            System.out.println("failed to process text:");
            e.printStackTrace();
        }
        return "Looks like something went wrong";
    }

    private String formAnswerBySentimentsWeight(Map<String, Double> weights) {
        Sentiment maxSentiment = Sentiment.fromString(
                Collections.max(weights.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey()
        );
        if (maxSentiment == null) {
            maxSentiment = Sentiment.SKIP;
        }
        return switch (maxSentiment) {
            case SKIP -> ANSWER_TO_SKIP;
            case NEUTRAL -> ANSWER_TO_NEUTRAL;
            case SPEECH -> ANSWER_TO_SPEECH;
            case POSITIVE -> ANSWER_TO_POSITIVE;
            case NEGATIVE -> ANSWER_TO_NEGATIVE;
        };
    }
}
