package com.benchemo_gil_gimpelson.javafinalproject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.*;
import java.util.Observer;
import java.util.Observable;

public class GUI  {

    private String column[] = {"NAME", "UNIT", "CURRENCYCODE", "COUNTRY", "RATE", "CHANGE"};
    private String data[][];
    private String code[];

    public void BuildGUI() {
        //            GUI
//            DECLARING ALL COMPONENTS

        JFrame f;
        JPanel bottomPanel, topPanel;
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
        topPanel = new JPanel();
        bottomPanel = new JPanel();
        jt = new JTable(data, column);
        sp = new JScrollPane(jt);
        layout= new GroupLayout(bottomPanel);
        lblAmount = new JLabel("Amount: ");
        lblFrom = new JLabel("From: ");
        lblTo = new JLabel("To: ");
        lblresult = new JLabel("Result: ");
        tocomboBox = new JComboBox<>(code);
        fromcomboBox = new JComboBox<>(code);
        txtAmount = new JTextField("");
        txtresult = new JTextField("");
        btnGo = new JButton("Go ");

//            PROPERTIES OF ALL COMPONENTS

        f.setSize(1200, 600);
        f.setLayout(new BorderLayout());
        f.setVisible(true);
        f.setBackground(Color.gray);

        topPanel.setSize(1200, 250);
        topPanel.setVisible(true);
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        sp.setPreferredSize(new Dimension(1200, 250));

        bottomPanel.setSize(1200, 400);
        bottomPanel.setVisible(true);
        bottomPanel.setLayout(layout);
        bottomPanel.setBackground(Color.WHITE);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        lblTo.setBounds(50, 110, 100, 30);
        lblFrom.setBounds(50, 80, 100, 30);
        lblAmount.setBounds(50, 50, 100, 30);
        lblresult.setBounds(50, 140, 100, 30) ;

        txtAmount.setBounds(150, 50, 200, 30);
        txtresult.setBounds(150, 140, 200, 30);

        btnGo.setBounds(50, 170, 80, 30);

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

        f.add(topPanel, BorderLayout.NORTH);
        f.add(bottomPanel, BorderLayout.SOUTH);
        topPanel.add(sp, BorderLayout.CENTER);
        bottomPanel.add(lblTo);
        bottomPanel.add(lblFrom);
        bottomPanel.add(lblAmount);
        bottomPanel.add(txtAmount);
        bottomPanel.add(lblresult);
        bottomPanel.add(txtresult);
        bottomPanel.add(tocomboBox);
        bottomPanel.add(fromcomboBox);
        bottomPanel.add(btnGo);
    }
    public void parseXMLfile() {
        try{
            File fXmlFile = new File("currency.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document docLocal = dBuilder.parse(fXmlFile);
            NodeList nList = docLocal.getElementsByTagName("CURRENCY");
            this.data = new String[nList.getLength()][6];
            this.code = new String[nList.getLength()];
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
                    code[i] = eElement.getElementsByTagName("CURRENCYCODE").item(0).getTextContent();
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
    public static void log_msg(String msg) {
        String fileName = "log.txt";
        try {
            // Open given file in append mode.
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));
            out.write(msg+"\n");
            out.close();
        }
        catch (IOException e) {
            System.out.println("exception occoured" + e);
        }
    }

    public static void main(String args[]) {
        GUI screen = new GUI();
        screen.parseXMLfile();
        screen.BuildGUI();
    }
}


































