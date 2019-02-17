import javax.swing.*;

class GUI
{
    public static void main(String args[])
    {
        String data[][] = { {"Dollar","1","USD","USA","3.641","-0.573"},
                {"Pound","1","GBP","Great Britain","4.6608","-0.633"},
                {"Yen","100","JPY","Japan","3.2995","0.045"}};
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
        // JScrollPane sp = new JScrollPane(jt);

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
}
