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

    public static void main(String[] args) {
        GUI gui = new GUI();
    }

    private String column[] = {"NAME", "UNIT", "CURRENCYCODE", "COUNTRY", "RATE", "CHANGE"};
    private String data[][];

    private GUI()
    {
        parseXMLfile();
        BuildGUI();
    }
    private void parseXMLfile()
    {
        try{
            File fXmlFile = new File("currency.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document docLocal = dBuilder.parse(fXmlFile);
            NodeList nList = docLocal.getElementsByTagName("CURRENCY");
            this.data = new String[nList.getLength()][6];
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void BuildGUI()
    {
        //            GUI
//            DECLARING ALL COMPONENTS

        String currCodeStatic[] = {"USD", "GBP", "JPY", "EUR", "AUD", "CAD", "DKK", "NOK", "ZAR", "SEK", "CHF", "JOD", "LBP", "EGP"};
        JFrame f;
        JPanel topPanel, bottomPanel;
        JTable jt;
        JScrollPane sp;
        GroupLayout layout;
        JLabel lblAmount, lblFrom, lblTo, lblresult;
        JComboBox<String> tocomboBox;
        JComboBox<String> fromcomboBox;
        JTextField txtAmount, txtresult;
        JButton btnGo;

//            CREATING ALL COMPONENTS

        f = new JFrame("Java Currency App");
        bottomPanel = new JPanel();
        topPanel = new JPanel();
        jt = new JTable(data, column);
        sp = new JScrollPane(jt);
        layout= new GroupLayout(topPanel);
        lblAmount = new JLabel("Amount: ");
        lblFrom = new JLabel("From: ");
        lblTo = new JLabel("To: ");
        lblresult = new JLabel("Result: ");
        tocomboBox = new JComboBox<>(currCodeStatic);
        fromcomboBox = new JComboBox<>(currCodeStatic);
        txtAmount = new JTextField("");
        txtresult = new JTextField("");
        btnGo = new JButton("Go ");

//            PROPERTIES OF ALL COMPONENTS

        f.setSize(1200, 380);
        f.setLayout(new BorderLayout());
        f.setVisible(true);
        f.setBackground(Color.GRAY);

        bottomPanel.setSize(1200, 250);
        bottomPanel.setVisible(true);
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        sp.setPreferredSize(new Dimension(1200, 250));

        topPanel.setSize(1200, 400);
        topPanel.setVisible(true);
        topPanel.setLayout(layout);
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        lblTo.setSize(100, 30);
        lblFrom.setSize(100, 30);
        lblAmount.setSize(100, 30);
        lblresult.setSize(100, 30) ;

        txtAmount.setSize(200, 30);
        txtresult.setSize(200, 30);

        fromcomboBox.setSize(200, 30);
        tocomboBox.setSize(200, 30);

        btnGo.setSize(80, 30);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblTo))
                                .addComponent(tocomboBox)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblFrom))
                                .addComponent(fromcomboBox)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblAmount)
                                .addComponent(txtAmount))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblresult)
                                .addComponent(txtresult))
                        .addComponent(btnGo)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblTo)
                                .addComponent(lblFrom)
                                .addComponent(lblAmount)
                                .addComponent(lblresult))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(tocomboBox)
                                .addComponent(fromcomboBox)
                                .addComponent(txtAmount)
                                .addComponent(txtresult)
                                .addComponent(btnGo))
        );


//            ADDING ALL COMPONENTS TO CONTAINERS

        f.add(bottomPanel, BorderLayout.SOUTH);
        f.add(topPanel, BorderLayout.CENTER);
        bottomPanel.add(sp, BorderLayout.CENTER);
        topPanel.add(lblTo);
        topPanel.add(lblFrom);
        topPanel.add(lblAmount);
        topPanel.add(txtAmount);
        topPanel.add(lblresult);
        topPanel.add(txtresult);
        topPanel.add(tocomboBox);
        topPanel.add(fromcomboBox);
        topPanel.add(btnGo);
    }
}


































