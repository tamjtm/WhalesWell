import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import static java.lang.Integer.parseInt;

public class BookFileReader extends TextFileReader
{
    private ArrayList<String> splitKeyword(String field)
    {
        ArrayList<String> keyword = new ArrayList<String>();
        String keywords[] = field.split(";");

        for (int i = 0; i < keywords.length; i++)
        {
            keyword.add(keywords[i]);
        }

        return keyword;
    }

    public Book loadBook()
    {
        Book newBook = null;
        String line;
        do
        {
            line = getNextLine();
            if(line != null)
            {
                String fields[] = line.split(",");
                newBook = new Book(fields[0], fields[1], fields[2], parseInt(fields[3]), splitKeyword(fields[4]), parseInt(fields[5]));
            }
        }
        while ((newBook == null) && (line != null));

        return newBook;
    }
    
}
