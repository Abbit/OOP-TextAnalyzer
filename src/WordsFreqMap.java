import java.util.HashMap;
import java.util.List;

class WordsFreqMap extends HashMap<String, Integer> {
    public int getWordsCount() {
        return values().stream().mapToInt(Integer::intValue).sum();
    }

    public void appendWord(String word) {
        merge(word, 1, (prev, _new) -> prev + 1);
    }

    public void appendWords(List<String> words) {
        for (var word : words) {
            appendWord(word);
        }
    }
}