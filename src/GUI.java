import org.w3c.dom.*;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

class GUI
{
    public static void main(String args[])
    {
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
            String column[] = {"NAME","UNIT","CURRENCYCODE","COUNTRY","RATE","CHANGE"};
            JFrame f = new JFrame("Java Currency");
            JLabel lblAmount, lblFrom, lblTo;
            JTextField txtAmount, txtFrom, txtTo;
            JButton btnGo;

            lblAmount = new JLabel("Amount");
            lblAmount.setBounds(50,50, 100,30);
            txtAmount = new JTextField("");
            txtAmount.setBounds(150,50, 200,30);

            lblFrom = new JLabel("From");
            lblFrom.setBounds(50,80, 100,30);
            txtFrom = new JTextField("");
            txtFrom.setBounds(150,80, 200,30);

            lblTo = new JLabel("To");
            lblTo.setBounds(50,110, 100,30);
            txtTo = new JTextField("");
            txtTo.setBounds(150,110, 200,30);

            btnGo = new JButton("Find");
            btnGo .setBounds(40,140,80,30);
//https://www.javatpoint.com/java-jtable
            JTable jt = new JTable(data,column);
            jt.setBounds(20,200,1000,400);
            //JScrollPane sp = new JScrollPane(jt);

            f.add(lblAmount);
            f.add(txtAmount);
            f.add(lblFrom);
            f.add(txtFrom);
            f.add(lblTo);
            f.add(txtTo);
            f.add(btnGo);
            f.add(jt);

            f.setSize(1200,900);
            f.setLayout(null);
            f.setVisible(true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
    }
}
