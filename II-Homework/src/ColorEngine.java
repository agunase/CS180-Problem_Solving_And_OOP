import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The {@code ColorEngine} class is what defines the Color fixer algorithm. It will read the broken file in,
 * collapse the array twice, via the {@code collapseArrayList} method, and the {@code analyzeColors} method.
 *
 * @author Abhishek Gunasekar agunase
 * @version 10-12-2019 1.0
 */
public class ColorEngine {

    @SuppressWarnings("FieldCanBeLocal")
    private static ArrayList<String> contentsOfFile;
    @SuppressWarnings("FieldCanBeLocal")
    private static ArrayList<String> collapsedContents;
    private static ArrayList<String> toWrite;

    public static void main(String[] args) throws UnsupportedColorSpectrumException, FileNotFoundException {
        Scanner userInput = new Scanner(System.in);
        System.out.println("What is the name of the color file you would like to fix?");
        String inputFile = userInput.nextLine();
        System.out.println("What is the name of the file you would like to dump the updated contents to?");
        String outputFile = userInput.nextLine();
        collapsedContents = new ArrayList<String>();
        contentsOfFile = new ArrayList<String>();
        toWrite = new ArrayList<String>();
        analyzeColors(collapseArrayList(readFile(inputFile)));
        writeFile(toWrite, outputFile);

    }

    /**
     * This method should check whether the passed file name exists, and if it does, then it should read the contents
     * of the file into an {@code ArrayList} of {@code String}s. If the file doesn't exist, the {@code
     * FileNotFoundException} should be thrown.
     *
     * @param fileName The file name to read from
     * @return The {@code ArrayList} to read the file contents into
     * @throws FileNotFoundException thrown if the file could not be found
     */
    //Ask Professor Bergstrom about FileNotFoundException
    private static ArrayList<String> readFile(String fileName) throws java.io.FileNotFoundException {
        String line = null;
        File file = new File(fileName);
        BufferedReader bfr = new BufferedReader(new FileReader(file));
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        try {
            while (true) {
                line = bfr.readLine();
                if (line == null) {
                    break;
                }
                contentsOfFile.add(line);
            }

            bfr.close();
        } catch (IOException e) {
            e.getMessage();
        }
        return contentsOfFile;
    }

    /**
     * Collapses an {@code ArrayList} into a third of its size. Should collapse the {@code ArrayList} from
     * having an integer on each line to each line containing three integers.
     *
     * @param toCollapse The pre-collapsed {@code ArrayList}
     * @return the newly collapsed {@code ArrayList}
     */
    private static ArrayList<String> collapseArrayList(ArrayList<String> toCollapse) {

        for (int i = 0; i < toCollapse.size(); i = i + 3) {
            collapsedContents.add(toCollapse.get(i) + toCollapse.get(i + 1) + toCollapse.get(i + 2));
        }
        return collapsedContents;
    }

    /**
     * This method should instantiate a new {@code ColorApproximator} object, and create a new {@code Color} object
     * using the data from the {@code ArrayList} parameter's data. It should then add a string of this template to
     * the {@code ArrayList} that holds the data that will be written to the file.
     * <p>
     * 0,0,0 is approximately the color BLACK
     *
     * @param toAnalyze The 'brokencolors.txt' file, that has been 'collapsed' once, into three digits per line.
     * @throws UnsupportedColorSpectrumException thrown if the {@code Color} constructor throws an exception.
     */
    private static void analyzeColors(ArrayList<String> toAnalyze) throws UnsupportedColorSpectrumException {
        for (int i = 0; i < toAnalyze.size(); i = i + 3) {

            Color color = new Color(null, Integer.parseInt(toAnalyze.get(i)),
                    Integer.parseInt(toAnalyze.get(i + 1)),
                    Integer.parseInt(toAnalyze.get(i + 2)));
            ColorApproximation col = new ColorApproximation(color);
            String output = Integer.parseInt(toAnalyze.get(i)) + ", " + Integer.parseInt(toAnalyze.get(i + 1)) + ", "
                    + Integer.parseInt(toAnalyze.get(i + 2)) +
                    " is approximately the color " + col.approximateColor();
            toWrite.add(output);
        }


    }

    /**
     * This method should take in a parameter of an ArrayList, containing the fixed numbers and their color
     * approximations. It should print the contents of the ArrayList to a file, with a new line
     *
     * @param dataToWrite The {@code ArrayList} that contains the data to write to the text file
     * @param filename    The filename of the file you want to write data to
     */


    private static void writeFile(ArrayList<String> dataToWrite, String filename) {
        try {
            File f = new File(filename);
            FileOutputStream fos = new FileOutputStream(f, true);
            PrintWriter pw = new PrintWriter(fos);
            if (!f.exists()) {
                throw new FileNotFoundException();
            } else {
                for (int i = 0; i < dataToWrite.size(); i++) {
                    pw.println(dataToWrite.get(i));
                }
            }
            pw.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }


}
