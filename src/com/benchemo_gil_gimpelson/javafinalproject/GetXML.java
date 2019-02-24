package com.benchemo_gil_gimpelson.javafinalproject;

import java.io.IOException;
import java.net.URL;
import java.io.InputStream;
import java.net.HttpURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.*;


public class GetXML
{
    public static void main(String args[])
    {
        InputStream is = null;
        HttpURLConnection con = null;
        NodeList nList = null, lastUpdate = null;
        try
        {
            // Reading XML file from boi
            URL url = new URL("https://www.boi.org.il/currency.xml");
            con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            is = con.getInputStream();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document docWeb = builder.parse(is);

            lastUpdate = docWeb.getElementsByTagName("LAST_UPDATE");
            System.out.println(lastUpdate.item(0).getTextContent());

            //Reading XML from local file
            try {

                File fXmlFile = new File("currency.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document docLocal = dBuilder.parse(fXmlFile);
                nList = docLocal.getElementsByTagName("LAST_UPDATE");
                System.out.println(nList.item(0).getTextContent());

            }
            catch (Exception e) {
                e.printStackTrace();
            }


            //Comparing the dates - if different we save in local

            if (!lastUpdate.item(0).getTextContent().equals(nList.item(0).getTextContent())){
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

            }
            else System.out.println("same date");
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