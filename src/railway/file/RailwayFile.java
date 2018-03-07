package railway.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import railway.network.Network;
import railway.validation.NetValidation;
import railway.validation.ValidationException;
import railway.validation.ValidationInfo;

import java.io.*;

/**
 * This class handles all file operations for the JSON representing the railway.
 * Uses Jackson JARs in lib for parsing.
 *
 * @author Josh Corne
 */

public class RailwayFile extends File
{
    private ObjectMapper mapper;

    public RailwayFile(String s)
    {
        super(s);
        mapper = new ObjectMapper();
    }

    /**
     * Reads the set file and returns the network object parsed from JSON
     *
     * @return Network the network object
     */
    public Network read() throws IOException
    {
        return mapper.readValue(getAbsoluteFile(), Network.class);
    }

    /**
     * Writes the network object out to the set file
     *
     * @param network the network object
     */
    public void write(Network network) throws IOException
    {
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writer().writeValue(getAbsoluteFile(), network);
    }

    public String readJson() throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(getAbsoluteFile()));

        String line;
        StringBuilder sb = new StringBuilder("");

        while((line = bufferedReader.readLine()) != null) {
            sb.append(line).append("\n");
        }

        bufferedReader.close();

        return sb.toString();
    }
}
