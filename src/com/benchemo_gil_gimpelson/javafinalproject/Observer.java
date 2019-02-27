package com.benchemo_gil_gimpelson.javafinalproject;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Observer extends Thread{
    private GUI screen;
    private Thread watchdog;
    private InputStream is = null;
    private HttpURLConnection con = null;
    private NodeList nList = null, lastUpdate = null;

    public Observer(GUI screen){
        this.screen=screen;
        watchdog = new Thread(this);
        watchdog.start();
    }
    public void run(){
            while(true) {
                Date dNow = new Date( );
                SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
                GUI.log_msg("- Current Date: " + ft.format(dNow));
                GUI.log_msg("- Prepare to check if there is a new XML file ");
                try {
                    // Reading XML file from boi
                    URL url = new URL("https://www.boi.org.il/currency.xml");
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.connect();
                    GUI.log_msg("- Connected to Bank of israel");
                    is = con.getInputStream();
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document docWeb = builder.parse(is);

                    lastUpdate = docWeb.getElementsByTagName("LAST_UPDATE");
                    GUI.log_msg("- Date found: " + lastUpdate.item(0).getTextContent());
                    //Reading XML from local file
                    try {
                        File fXmlFile = new File("currency.xml");
                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document docLocal = dBuilder.parse(fXmlFile);
                        nList = docLocal.getElementsByTagName("LAST_UPDATE");
                        GUI.log_msg("- File Date: " + lastUpdate.item(0).getTextContent());
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Comparing the dates - if different we save in local
                    if (!lastUpdate.item(0).getTextContent().equals(nList.item(0).getTextContent())) {
                        // XML it outdated and need to re-retrieve
                        GUI.log_msg("- XML is outdated");
                        GUI.log_msg("- Update the local file");
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
                        } finally {
                            if (in != null) {
                                in.close();
                            }
                            if (fout != null) {
                                fout.close();
                            }
                        }
                        screen.getBtnRefresh().doClick();
                        GUI.log_msg("- Local file is now updated");
                    }
                    else {
                        GUI.log_msg("- Local file is updated");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (con != null) {
                        con.disconnect();
                    }
                }
                try {
                    screen.setStatus("Last check: " + ft.format(dNow));
                    Thread.sleep(9000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
    }
}
