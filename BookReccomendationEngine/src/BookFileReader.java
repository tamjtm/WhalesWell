import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import static java.lang.Integer.parseInt;

/**
 * BookFileReader
 * 
 *   Class to read information about book detail from a file
 *      and create book.
 *
 *   Each line of the file has the following structure
 *
 *      SLENDER MAN,ANONYMOUS,HORROR,336,SLENDER MAN;HORROR;ANONYMOUS;FICTION & LITERATURE;SLENDER;MAN,450
 *
 *   First field is book's title
 *   Second field is book's author
 *   Third field is book's category
 *   Fourth field is number of pages
 *   Fifth field is book's keyword
 *   Sixth field is book's price
 * 
 *   Each field is splitted by comma.
 *
 *   Created by Chanisa Phengphon (Chertam)  ID 59070501088
 *     10 May 2019
 *
 */
public class BookFileReader extends TextFileReader
{
    /**
     *      use to split pack of keywords into ArrayList of String
     * 
     * @param field pack of keywords from book
     * @return keywords list of each book
     */
    private ArrayList<String> splitKeyword(String field)
    {
        ArrayList<String> keyword = new ArrayList<String>();
        String keywords[] = field.split(";");               // split keyword by semi-colon

        for (int i = 0; i < keywords.length; i++)
        {
            keyword.add(keywords[i]);                       // add keyword to ArrayList
        }

        return keyword;
    }

    /**
     *      initialize the book from fields that read from each line of file
     * 
     * @return completed book 
     */
    public Book loadBook()
    {
        Book newBook = null;
        String line;
        do
        {
            line = getNextLine();
            if(line != null)
            {
                String fields[] = line.split(",");      // split fields by comma
                newBook = new Book(fields[0], fields[1], fields[2], parseInt(fields[3]), splitKeyword(fields[4]), parseInt(fields[5]));
            }
        }
        while ((newBook == null) && (line != null));    // if there is line or book left 

        return newBook;
    }
    
}
