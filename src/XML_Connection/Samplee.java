package XML_Connection;

import java.io.IOException;
import java.net.URL;
import java.io.InputStream;
import java.net.HttpURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.*;


public class Samplee
{
    public static void main(String args[])
    {
        InputStream is = null;
        HttpURLConnection con = null;
        try
        {
            URL url = new URL("https://www.boi.org.il/currency.xml");
            con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            BufferedInputStream in = null;
            FileOutputStream fout = null;

            try {
                in = new BufferedInputStream((url).openStream());
                fout = new FileOutputStream("currency.xml");

                final byte data[] = new byte[1024];
                int count;
                while ((count = in.read(data, 0, 1024)) != -1) {
                    fout.write(data, 0, count);
                }
            }
            finally {
                if (in != null) {
                    in.close();
                }
                if (fout != null) {
                    fout.close();
                }
            }


            is = con.getInputStream();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);

            NodeList lastUpdate = doc.getElementsByTagName("LAST_UPDATE");
            System.out.println(lastUpdate.item(0).getTextContent());

            NodeList list = doc.getElementsByTagName("CURRENCY");
            int length = list.getLength();
            for(int i=0; i<length; i++)
            {
                System.out.println("\n"+ list.item(i).getTextContent());
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        catch(SAXException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(is!=null)
            {
                try
                {
                    is.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            if(con!=null)
            {
                con.disconnect();
            }
        }
    }
}