package textprocessors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class SentimentAnalisysTest {
        @Test
        public void testProcess() {
            SentimentAnalisys sentimentAnalisys = new SentimentAnalisys();
            HashMap<String, String[]> answersWithTextsSamples = new HashMap<>();
            answersWithTextsSamples.put(SentimentAnalisys.ANSWER_TO_NEGATIVE, new String[]{
                    "почему я говорю с идиотом", "почему ты такой тупой", "я тебя ненавижу"
            });
            answersWithTextsSamples.put(SentimentAnalisys.ANSWER_TO_SPEECH, new String[] {
                    "привет, как дела", "спасибо огромное", "поздравляем победителей"
            });
            answersWithTextsSamples.put(SentimentAnalisys.ANSWER_TO_NEUTRAL, new String[] {
                    "я ем гречу с курой", "пандемия скоро закончится", "но кажется мы не выйдем из дома"
            });
            answersWithTextsSamples.put(SentimentAnalisys.ANSWER_TO_POSITIVE, new String[] {
                    "я тебя люблю", "сегодня прекрасный день", "благородный Мохо - красивая птичка"
            });
            answersWithTextsSamples.put(SentimentAnalisys.ANSWER_TO_SKIP, new String[] {
                    "Я — Озимандия, я — мощный царь царей!", "Ровно тридцать лет и три года"
            });
            for(Map.Entry<String, String[]> e: answersWithTextsSamples.entrySet()) {
                for(String t: e.getValue()) {
                    assertEquals(e.getKey(), sentimentAnalisys.Process(t), t);
                }
            }
        }
}
