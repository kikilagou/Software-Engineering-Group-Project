package Model;

import java.io.*;

/**
 * Created by asherfischbaum on 18/04/2017.
 */
public class HelpReader {
    private final String fileName;

    public HelpReader() {
        fileName = "help.txt";
    }

    /**
     * reads the help menu file and returns it as a string to be used by the view
     * @return string of the read file
     */
    public String readHelpFile()
    {
        String all = "";

        BufferedReader br = null;

        try {
            FileReader fr = new FileReader(new File(fileName));
            br = new BufferedReader(fr);

            String line = null;
            while((line = br.readLine()) != null)
            {
                all += line+"\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br!=null)
            {
                try {
                    br.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }

        return all;
    }
}
