import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class FileManager extends TextFileReader
{
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

    /*
    public Account loadUserData()
    {
        Account account = null;
        String line;
        do
        {
            line = getNextLine();
            if(line != null)
            {
                String fields[] = line.split(",");
                account = new Account(fields[0], fields[1], fields[2], fields[3]);
            }
        }
        while ((newBook == null) && (line != null));

        return newBook;
    }

    private ArrayList<String> splitHistory(String field)
    {
        String history[] = field.split(";");

        for (int i = 0; i < keywords.length; i++)
        {
            String subKey[] = keywords[i].split("\t");
        }
    }
    */
}
