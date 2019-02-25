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
import java.util.*;
import java.text.*;

class Observer extends Thread{

    //Tread veriables
    private Thread watchdog;

    //XML pulling veriables
    private InputStream is = null;
    private HttpURLConnection con = null;
    private NodeList nList = null, lastUpdate = null;

    Observer( String name) {
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        GUI.log_msg("- Current Date: " + ft.format(dNow));
        GUI.log_msg("- Prepare to check if there is a new XML file ");
        if (watchdog == null) {
            watchdog = new Thread (this);
            watchdog.start ();
        }
    }

    public void run() {
//        System.out.println("Running " +  threadName );
//        try {
//            for(int i = 4; i > 0; i--) {
//                System.out.println("Thread: " + threadName + ", " + i);
//                // Let the thread sleep for a while.
//                if(threadName=="Thread-1")
//                    Thread.sleep(500);
//                else
//                    Thread.sleep(50);
//            }
//        } catch (InterruptedException e) {
//            System.out.println("Thread " +  threadName + " interrupted.");
//        }
//        System.out.println("Thread " +  threadName + " exiting.");
        while (true) {
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
                    System.out.println(nList.item(0).getTextContent());
                    GUI.log_msg("- File Date: " + lastUpdate.item(0).getTextContent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Comparing the dates - if different we save in local

                if (!lastUpdate.item(0).getTextContent().equals(nList.item(0).getTextContent())) {
                    GUI.log_msg("- Update the saved file");
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

                } else {
                    GUI.log_msg("- No Change");
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
                watchdog.sleep(90000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

class TestThread {

    public static void main(String args[]) {

        Observer T1 = new Observer( "Thread-1");
        Observer T2 = new Observer( "Thread-2");
    }
}



