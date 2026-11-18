import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PurchaseScreen extends JFrame implements ActionListener {


    JLabel name;
    JLabel price;
    JLabel pic;
    JButton addToCart = new JButton("افزودن به سبد خرید");
    Product data;

    public PurchaseScreen(Product product){
        data = product;
        name = new JLabel(product.name);
        name.setPreferredSize(new Dimension(400, 100));
        name.setBounds(450, 50, 400, 100);
        name.setFont(new Font(Font.SERIF, Font.PLAIN, 50));

        price = new JLabel(product.price + " " + "تومان");
        price.setPreferredSize(new Dimension(400, 100));
        price.setBounds(450, 150, 400, 200);
        price.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        
        ImageIcon imageIcon = new ImageIcon(product.address);
        ImageIcon resizedImageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
        pic = new JLabel(resizedImageIcon);
        pic.setPreferredSize(new Dimension(400, 400));
        pic.setBounds(0,0,400, 400);

        addToCart.setPreferredSize(new Dimension(380, 90));
        addToCart.setBounds(410, 300, 380,50);
        addToCart.setBackground(Color.GREEN);
        addToCart.setFont(new Font(Font.SERIF, Font.PLAIN, 50));
        addToCart.addActionListener(this);
        
        this.setSize(800, 400);
        this.setLayout(null);
        this.add(pic);
        this.add(name);
        this.add(price);
        this.add(addToCart);
        this.setVisible(true);
       
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addToCart){
            try {
                String host="jdbc:derby://localhost:1527/sabad";
                String username="sabad", password="sabad";
                Connection con = DriverManager.getConnection( host, username, password );
                Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                String SQL="select * from sabad";
                ResultSet rs=stmt.executeQuery(SQL);
                rs.moveToInsertRow();
                rs.updateString("productname", data.name);
                rs.updateString("price", data.price);
                rs.updateString("address", data.address);
                rs.insertRow();
                rs.close();
                rs=stmt.executeQuery(SQL);             
            }
            catch (SQLException ex) {
                Logger.getLogger(PurchaseScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            this.dispose();
        }
    }
    
}
