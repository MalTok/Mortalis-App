package pl.mt.mortalis.validation;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

@Service
public class BadWordsFileReader {
    private static final String PL_BAD_WORDS_FILE_PATH = "static/files/PL_bad_words.txt";

    public Set<String> readFile() {
        Set<String> badWords = new TreeSet<>();
        try (
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PL_BAD_WORDS_FILE_PATH);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                badWords.add(nextLine);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File could not be found");
        } catch (IOException e) {
            throw new RuntimeException("Error while file reading or writing");
        }
        return badWords;
    }
}
