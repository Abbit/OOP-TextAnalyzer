import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileParser {
    // Regex is alternative to \W+ for all unicode chars
    private static final Pattern splitPattern = Pattern.compile("[^\\p{L}\\p{Nd}]+");

    public static WordsFreqMap getWordsFreqMap(BufferedReader reader) throws IOException {
        var wordsFreqMap = new WordsFreqMap();
        String line = null;

        while ((line = reader.readLine()) != null) {
            var words = getWordsFromLine(line);

            wordsFreqMap.appendWords(words);
        }

        return wordsFreqMap;
    }

    private static List<String> getWordsFromLine(String line) {
        return Arrays.stream(splitPattern.split(line))
                .filter(str -> !str.equals(""))
                .collect(Collectors.toList());
    }
}
