package railway.file;

//import com.fasterxml.jackson.databind.ObjectMapper;
import railway.network.Network;

import java.io.*;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * This class handles all file operations for the JSON representing the railway.
 * Uses Jackson JARs in lib for parsing.
 *
 * @author Josh Corne
 */

public class RailwayFile extends File
{
    public RailwayFile(String s)
    {
        super(s);
    }

    /**
     * Reads the set file and returns the network object parsed from JSON
     *
     * @return Network the network object
     */
    public Network read() throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(this.getAbsoluteFile(), Network.class);
    }

    /**
     * Writes the network object out to the set file
     *
     * @param network the network object
     */
    public void write(Network network) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(this.getAbsoluteFile(), network);
    }
}
