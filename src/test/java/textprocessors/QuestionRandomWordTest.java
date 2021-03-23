package textprocessors;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

public class QuestionRandomWordTest {
    @Test
    public void testProcess() {
        QuestionRandomWord qrw = new QuestionRandomWord(0);
        MatcherAssert.assertThat(qrw.Process("привет машина"), anyOf(is("Привет?"), is("Машина?")));
        MatcherAssert.assertThat(qrw.Process("прИвЕт мАшИна"), anyOf(is("ПрИвЕт?"), is("МАшИна?")));
        MatcherAssert.assertThat(qrw.Process("heLlo maChine"), anyOf(is("HeLlo?"), is("MaChine?")));
        assertEquals("Input cannot be empty", qrw.Process(null));
        assertEquals("Input cannot be empty", qrw.Process(""));
        assertEquals("No words has been found", qrw.Process(", , !"));
    }
}
