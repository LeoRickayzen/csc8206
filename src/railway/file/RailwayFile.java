package railway.file;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    public RailwayFile(String s)
    {
        super(s);
    }

    /**
     * Reads the set file and returns the network object parsed from JSON
     *
     * @return Network the network object
     */
    public Network read() throws IOException, ValidationException
    {
        ObjectMapper mapper = new ObjectMapper();
        Network n = mapper.readValue(this.getAbsoluteFile(), Network.class);
        ValidationInfo networkValidation = NetValidation.Validate(n);
        if(networkValidation.isValid())
        {
            return n;
        }
        else
        {
            throw new ValidationException(networkValidation.toString());
        }
    }

    /**
     * Writes the network object out to the set file
     *
     * @param network the network object
     */
    public void write(Network network) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(this.getAbsoluteFile(), network);
    }

    public String readJson() throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(this.getAbsoluteFile()));

        String line;
        StringBuilder sb = new StringBuilder("");

        while((line = bufferedReader.readLine()) != null) {
            sb.append(line).append("\n");
        }

        bufferedReader.close();

        return sb.toString();
    }
}
