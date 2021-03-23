package textprocessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class QuestionRandomWord implements TextProcessor {
    final private Random random;
    public QuestionRandomWord(long seed) {
        random = new Random(seed);
    }
    public String Process(String input) {
        if(input == null || input.length() == 0) {
            return "Input cannot be empty";
        }
        List<String> tokens = Arrays.asList(input.split("\\s+|\\?"));
        List<String> words = tokens.stream().filter(
                w -> w.matches("^[A-zЁёА-я]+$")
        ).collect(Collectors.toList());
        if(words.size() > 0) {
            String chosenWord = words.get(random.nextInt(words.size()));
            return chosenWord.substring(0, 1).toUpperCase() + (chosenWord.length() > 1 ? chosenWord.substring(1) : "") + "?";
        } else {
            return "No words has been found";
        }
    }
}
