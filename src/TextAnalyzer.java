import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;


@Command(name = "analyzetext", mixinStandardHelpOptions = true, version = "analyzetext 0.1")
public class TextAnalyzer implements Runnable {
    @Parameters(paramLabel = "FILE", description = "File with text to analyze")
    private Path inputFile;

    @Option(names = {"-o", "--output"}, description = "Path for output file")
    private Path outputFile = Paths.get("analyzed.csv");

    @Override
    public void run() {
        WordsFreqMap wordsFreqMap = null;

        try (BufferedReader reader = Files.newBufferedReader(inputFile)) {
            wordsFreqMap = FileParser.getWordsFreqMap(reader);
        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
            return;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(
                outputFile, StandardOpenOption.CREATE_NEW, StandardOpenOption.APPEND)) {
            new CSVWriter(writer).writeMapToCSV(wordsFreqMap);
        } catch (FileAlreadyExistsException e) {
            System.err.println("Output file already exists: " + e.getLocalizedMessage());
        } catch (IOException e) {
            System.err.println("Error while writing to file: " + e.getLocalizedMessage());
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new TextAnalyzer()).execute(args);
        System.exit(exitCode);
    }
}
