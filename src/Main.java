import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Main extends JFrame{
    private JTextArea  m1, m2, m31, m32, m33 ;
    private JLabel m, p31, p32;
    private JButton b1, b2;
public Main(){
    setTitle("Welcome");
    setSize(getMaximumSize());
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JLabel w1 = new JLabel("Public Key Authenticated Encryption with keyword search supporting Constant Trapdoor Generation");
    w1.setFont(new Font("Times New Roman",Font.BOLD,26));
    w1.setPreferredSize(new Dimension(1200,100));

    JLabel m=new JLabel("Message ");
    m.setPreferredSize(new Dimension(100, 50));
    m.setFont(new Font("Times New Roman",Font.BOLD,18));

    JTextArea m1=new JTextArea();
    m1.setPreferredSize(new Dimension(550, 100));
    m1.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

    JTextArea m2 = new JTextArea();
    m2.setPreferredSize(new Dimension(550, 100));
    m2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

    JLabel k1=new JLabel("Public Key ");
    k1.setPreferredSize(new Dimension(100, 50));
    k1.setFont(new Font("Times New Roman",Font.BOLD,18));


    JTextArea m31 = new JTextArea();
    m31.setPreferredSize(new Dimension(550, 100));

    JTextArea m32 = new JTextArea();
    m32.setPreferredSize(new Dimension(550, 100));

    JLabel k2=new JLabel("     ");
    k2.setPreferredSize(new Dimension(350, 50));

    JLabel k3=new JLabel("     ");
    k3.setPreferredSize(new Dimension(450, 50));

    JTextArea m33 = new JTextArea();
    m33.setPreferredSize(new Dimension(550, 100));

    JTextArea m34 = new JTextArea();
    m34.setPreferredSize(new Dimension(550,100));

    JLabel p31 = new JLabel();
    p31.setPreferredSize(new Dimension(300,100));

    JLabel p32 = new JLabel("Sender Public key ");
    p32.setPreferredSize(new Dimension(200, 100));
    p32.setFont(new Font("Times New Roman",Font.BOLD,18));

    JButton b1 = new JButton("Message ");
    b1.setPreferredSize(new Dimension(150,40));

    JButton b2 = new JButton("Key Generation ");
    b2.setPreferredSize(new Dimension(150,40));

    JButton b3 = new JButton("Encryption");
    b3.setPreferredSize(new Dimension(150,40));

    JPanel panel=new JPanel();
    panel.setBackground(new Color(173, 216, 230));

    panel.add(w1);
    panel.add(m);
    panel.add(m1);
    panel.add(m2);
    panel.add(k1);
    panel.add(m31);
    panel.add(m32);
    panel.add(k2);
    panel.add(m33);
    panel.add(p31);
    panel.add(p32);
    panel.add(m34);
    panel.add(k3);
    panel.add(b1);
    panel.add(b2);
    panel.add(b3);

    Pairing pairing = PairingFactory.getPairing("params.properties");
    PairingFactory.getInstance().setUsePBCWhenPossible(true);

    // initiate p
    Element P = pairing.getG1().newRandomElement().getImmutable();
    System.out.println("P is :" + P);

    // initiate g
    Element g = pairing.getG1().newRandomElement().getImmutable();
    System.out.println("g is :"+g);

    Element x1 = pairing.getZr().newRandomElement().getImmutable();
    Element x2 = pairing.getZr().newRandomElement().getImmutable();
    Element x3 = pairing.getZr().newRandomElement().getImmutable();
    Element x4 = pairing.getZr().newRandomElement().getImmutable();
            /*
            // public key generation
               Element pr- public key of Receiver and ps- public key of sender */

    Element pr1=g.powZn(x1);
    System.out.println("public key1: "+pr1);
    Element pr2=g.powZn(x2);
    System.out.println("public key2: "+pr2);
    Element pr3=g.powZn(x3);
    System.out.println("public key3: "+pr3);

    // Skr1= x1,skr2=x2,skr3=x3 and skr4=x4 are the serect keys
    Element skr1=x1;
    Element skr2=x2;
    Element skr3=x3;
    Element skr4=x4;

    // Key Generation for receiver  - ends here.

    // Key Generation for Sender  - Starts here.
    // y is the Secret key of sender.
    Element y = pairing.getZr().newRandomElement().getImmutable();

    // public key of Sender is pks
    Element pks=g.powZn(y);
    System.out.println("public key Sender: "+pks);

    // Key Generation for Sender  - Ends here.

    // Keyword Encryption Starts here
    /* Random number for powers */
    Element r1 = pairing.getZr().newRandomElement().getImmutable();
    Element r2 = pairing.getZr().newRandomElement().getImmutable();

    /* Encryption*/
    Element h1_hash_int = pairing.getZr().newRandomElement().getImmutable();
    Element c1 = (pr2.powZn(h1_hash_int).add(pr3)).powZn(r1);

    Element c2 = g.powZn(r1);

    Element h2_hash_int = pairing.getZr().newRandomElement().getImmutable();
    Element h3_hash_int = pairing.getZr().newRandomElement().getImmutable();
    Element c3 = ((pr2.powZn(h2_hash_int).add(pr3)).powZn(r2)).add(g.powZn(h3_hash_int.mul(r1)));

    Element c4_hash_point = pairing.getG1().newRandomElement().getImmutable();
    Element c4 = c4_hash_point.powZn(r2);

    Element c5_hash_point = pairing.getG1().newRandomElement().getImmutable();
    Element c5 = c5_hash_point.powZn(r1);

    System.out.println("c1 is : " + c1);
    System.out.println("c2 is : " + c2);
    System.out.println("c3 is : " + c3);
    System.out.println("c4 is : " + c4);
    System.out.println("c5 is : " + c5);

    //action
    b1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            m1.setText("P (x,y) is :"+P.toString());
            m1.setLineWrap(true);

            // initiate g
            Element g = pairing.getG1().newRandomElement().getImmutable();
            System.out.println("g is :"+g);
            m2.setText("g is :"+g.toString());
            m2.setLineWrap(true);
    }
    });

    b2.addActionListener(new ActionListener() {
                             @Override
                             public void actionPerformed(ActionEvent e) {
                                 // Key Generation for receiver  - Starts here
                                 m31.setText("Public key1 is :"+pr1.toString());
                                 m31.setLineWrap(true);
                                 m32.setText("Public key2 is :"+pr2.toString());
                                 m32.setLineWrap(true);
                                 m33.setText("Public key3 is :"+pr3.toString());
                                 m33.setLineWrap(true);
                                 m34.setText("sender Public key is :"+pks.toString());
                                 m34.setLineWrap(true);
                                 // Key Generation for Sender  - Ends here.
                             }
                         });

    b3.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        message msg = new message();
        msg.setVisible(true);
        setVisible(false);
        }
    });

    add(panel);
    setVisible(true);
        //updated test ends here
    }
    public static void main(String[] args) {
        new Main();
}
}
