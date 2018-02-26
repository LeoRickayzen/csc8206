package railway.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import railway.network.Network;

import java.io.*;

/**
 * @author Josh Corne
 */

public class NetworkFile extends File
{
    public NetworkFile(String s)
    {
        super(s);
    }

    public Network read() throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(this.getAbsoluteFile(), Network.class);
    }

    public void write(String fileName, Network network) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(fileName), network);
    }
}
