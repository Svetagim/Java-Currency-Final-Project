package com.benchemo_gil_gimpelson.javafinalproject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;

public class GUI {

    public static void main(String args[]) {
        try {
            File fXmlFile = new File("currency.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document docLocal = dBuilder.parse(fXmlFile);
            NodeList nList = docLocal.getElementsByTagName("CURRENCY");
            String data[][] = new String[nList.getLength()][6];
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    data[i][0] = eElement.getElementsByTagName("NAME").item(0).getTextContent();
                    data[i][1] = eElement.getElementsByTagName("UNIT").item(0).getTextContent();
                    data[i][2] = eElement.getElementsByTagName("CURRENCYCODE").item(0).getTextContent();
                    data[i][3] = eElement.getElementsByTagName("COUNTRY").item(0).getTextContent();
                    data[i][4] = eElement.getElementsByTagName("RATE").item(0).getTextContent();
                    data[i][5] = eElement.getElementsByTagName("CHANGE").item(0).getTextContent();
                }
            }
            String column[] = {"NAME", "UNIT", "CURRENCYCODE", "COUNTRY", "RATE", "CHANGE"};

            //Rate exchange

            String from = "LBP", to = "ILS";
            double ammount = 1;
            double fromRate = 1.0, fromUnit = 1.0, toRate = 1.0, toUnit = 1.0;
            double exchangeRate;
            for (int i = 0; i < nList.getLength(); i++){
                if (data[i][2].equals(from)) {
                    fromRate = Double.parseDouble(data[i][4]);
                    fromUnit = Double.parseDouble(data[i][1]);
                }
                else if (data[i][2].equals(to)) {
                    toRate = Double.parseDouble(data[i][4]);
                    toUnit = Double.parseDouble(data[i][1]);
                }
            }

            exchangeRate = (fromRate/toRate)*(toUnit/fromUnit)*ammount;
            System.out.println(exchangeRate);




//            GUI

//            Table tb = new Table();
            JFrame f = new JFrame("Java Currency App");

            f.setSize(1200, 800);
            f.setLayout(new BorderLayout());
            f.setVisible(true);
            f.setBackground(Color.gray);

            JPanel bottomPanel, topPanel;
            topPanel = new JPanel();
            topPanel.setVisible(true);
            topPanel.setLayout(new BorderLayout());
            topPanel.setBackground(Color.WHITE);
            topPanel.setSize(1200,400);

            bottomPanel = new JPanel();
            bottomPanel.setVisible(true);
            bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            bottomPanel.setBackground(Color.white);
            bottomPanel.setSize(1200,400);

            JTable jt = new JTable(data, column);
            jt.setSize(950, 400);
            JScrollPane sp = new JScrollPane(jt);

            JLabel lblAmount, lblFrom, lblTo, lblresult;
            lblAmount = new JLabel("Result: ");
            lblAmount.setBounds(50, 50, 100, 30);
            lblFrom = new JLabel("From: ");
            lblFrom.setBounds(50, 80, 100, 30);
            lblTo = new JLabel("To: ");
            lblTo.setBounds(50, 110, 100, 30);
            lblresult = new JLabel("Amount: ");
            lblresult.setBounds(50, 140, 100, 30) ;

            JTextField txtAmount, txtresult;
            txtAmount = new JTextField("");
            txtAmount.setBounds(150, 50, 200, 30);
            txtresult = new JTextField("");
            txtresult.setBounds(150, 140, 200, 30);


//            new version:
//            JComboBox<String> tocomboBox;
//            tocomboBox = new JComboBox<>(tb.currCode);
//            JComboBox<String> fromcomboBox;
//            fromcomboBox = new JComboBox<>(tb.currCode);

//            old version:
//            txtFrom = new JTextField("");
//            txtFrom.setBounds(150, 80, 200, 30);
//            txtTo = new JTextField("");
//            txtTo.setBounds(150, 110, 200, 30);

            JButton btnGo;
            btnGo = new JButton("Find: ");
            btnGo.setBounds(50, 170, 80, 30);


//            new version:
            f.add(topPanel, BorderLayout.NORTH);
            f.add(bottomPanel, BorderLayout.SOUTH);
            topPanel.add(jt, BorderLayout.NORTH);
            topPanel.add(sp, BorderLayout.WEST);
            bottomPanel.add(lblTo);
            bottomPanel.add(lblFrom);
            bottomPanel.add(lblAmount);
            bottomPanel.add(txtAmount);
            bottomPanel.add(lblresult);
            bottomPanel.add(txtresult);
//            bottomPanel.add(tocomboBox);
//            bottomPanel.add(fromcomboBox);
            bottomPanel.add(btnGo);


                    }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}






















