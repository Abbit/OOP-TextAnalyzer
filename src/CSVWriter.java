import java.io.BufferedWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class CSVWriter {
    private final BufferedWriter writer;
    private final DecimalFormat decimalFormater = new DecimalFormat("#.####");

    CSVWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public void writeMapToCSV(WordsFreqMap wordsFreqMap) throws IOException {
        List<Map.Entry<String, Integer>> toSort = new ArrayList<>(wordsFreqMap.entrySet());
        toSort.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        writeHeading();

        var wordsCount = wordsFreqMap.getWordsCount();
        for (var el : toSort) {
            writeLine(buildRow(el.getKey(), el.getValue(), (double) el.getValue() / wordsCount * 100));
        }
    }

    private void writeLine(String row) throws IOException {
        writer.write(row);
        writer.newLine();
    }

    private void writeHeading() throws IOException {
        writeLine("Word,Freq,Freq(%)");
    }

    private String buildRow(String word, Integer count, Double freq) {
        var row = new StringJoiner(",");
        row.add(word);
        row.add(count.toString());
        row.add(decimalFormater.format(freq));

        return row.toString();
    }
}
