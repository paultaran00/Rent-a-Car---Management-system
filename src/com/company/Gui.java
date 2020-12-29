package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Gui extends JFrame {
    private static final long serialVersionUID = 1L;


    BTree bt;
    List<Car> arr;
    int pozitie;


    public Gui(File f) throws IOException, ClassNotFoundException {
        super("Rent a Car");

        bt = new BTree(3);
        arr = new ArrayList<>();
        pozitie = 0;

        if(!f.exists())
            f.createNewFile();

        boolean empty = f.exists() && f.length() == 0;
        if(empty == false)
            deserializare(f);

        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setSize(700,550);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel poz = new JPanel(new FlowLayout());
            poz.setPreferredSize(new Dimension(700,250));
            BufferedImage myPicture = ImageIO.read(new File("poz.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            poz.add(picLabel);
        this.add(poz);


        JPanel left = new JPanel(new FlowLayout());
            left.setPreferredSize(new Dimension(400,300));
            JPanel top1 = new JPanel(new FlowLayout());
                top1.setPreferredSize(new Dimension(400, 300));
                JPanel vin = new JPanel(new FlowLayout());
                    JLabel l_vin = new JLabel("VIN: ");
                    JTextField t_vin = new JTextField(5);
                    vin.add(l_vin);
                    vin.add(t_vin);
                top1.add(vin, BorderLayout.WEST);
                JPanel brand = new JPanel(new FlowLayout());
                    JLabel l_brand = new JLabel("Brand: ");
                    JTextField t_brand = new JTextField(7);
                    brand.add(l_brand);
                    brand.add(t_brand);
                top1.add(brand, BorderLayout.CENTER);
                JPanel model = new JPanel(new FlowLayout());
                    JLabel l_model = new JLabel("Model: ");
                    JTextField t_model = new JTextField(7);
                    model.add(l_model);
                    model.add(t_model);
                top1.add(model, BorderLayout.EAST);
            left.add(top1);

                JLabel spatiu = new JLabel("                                                                                                 ");
                top1.add(spatiu);


                JPanel motor = new JPanel(new FlowLayout());
                    JLabel l_motor = new JLabel("Motor: ");
                    JSpinner s_motor = new JSpinner(new SpinnerNumberModel(1,0.1,20,0.1));
                    motor.add(l_motor);
                    motor.add(s_motor);
                top1.add(motor, BorderLayout.WEST);

                JPanel horse = new JPanel(new FlowLayout());
                    JLabel l_horsePower = new JLabel("Horse Power: ");
                    JSpinner t_horsePower = new JSpinner(new SpinnerNumberModel(50,5,700,5));
                    horse.add(l_horsePower);
                    horse.add(t_horsePower);
                top1.add(horse, BorderLayout.CENTER);

                JPanel pret = new JPanel(new FlowLayout());
                    JLabel l_nou = new JLabel("Price/Day: ");
                    JTextField t_pret = new JTextField(3);
                    JLabel dol = new JLabel("$");
                    pret.add(l_nou);
                    pret.add(t_pret);
                    pret.add(dol);
                top1.add(pret);

                JLabel spatiu2 = new JLabel("                                                                                                               ");
                top1.add(spatiu2);

                JLabel l_descriere = new JLabel("Car description: ");
                JTextArea ta_descriere = new JTextArea(5,35);
                top1.add(l_descriere);
                top1.add(ta_descriere);

        this.add(left);


        JPanel right = new JPanel(new FlowLayout());
            right.setPreferredSize(new Dimension(250,300));
            JPanel row1 = new JPanel(new BorderLayout());
                row1.setPreferredSize(new Dimension(200,30));
                JButton b_next = new JButton("    Next    ");
                JButton b_prev = new JButton("Previous");
                row1.add(b_next,BorderLayout.WEST);
                row1.add(b_prev,BorderLayout.EAST);
            right.add(row1);

            JLabel rightspace = new JLabel("                                 ");
            right.add(rightspace);

            JPanel row2 = new JPanel(new BorderLayout());
                row2.setPreferredSize(new Dimension(200,30));
                JButton b_first = new JButton("    First    ");
                JButton b_last = new JButton("    Last    ");
                row2.add(b_first, BorderLayout.WEST);
                row2.add(b_last, BorderLayout.EAST);
            right.add(row2);

            JLabel rightspace2 = new JLabel("                                 ");
            right.add(rightspace2);

            JPanel row3 = new JPanel(new BorderLayout());
                row3.setPreferredSize(new Dimension(200,30));
                JButton b_add = new JButton("    Add     ");
                JButton b_delete = new JButton("    Rent    ");
                row3.add(b_add, BorderLayout.WEST);
                row3.add(b_delete, BorderLayout.EAST);
            right.add(row3);

            JLabel rightspace3 = new JLabel("                                 ");
            right.add(rightspace3);

            JPanel row4 = new JPanel(new BorderLayout());
                row4.setPreferredSize(new Dimension(200,40));
                JButton b_viewList = new JButton("View list of cars");
                row4.add(b_viewList);
            right.add(row4);

        this.add(right);



        b_next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arr.isEmpty()){
                    t_vin.setText("Empty!");
                    t_brand.setText("Empty!");
                    t_model.setText("Empty!");
                    t_pret.setText("Empty!");
                    ta_descriere.setText("Empty!");

                }
                else{
                    if(pozitie == arr.size() - 1)
                        pozitie = -1;

                    pozitie += 1;

                    t_vin.setText(String.valueOf(arr.get(pozitie).getVehicleIdentificationNumber()));
                    t_brand.setText(arr.get(pozitie).getBrend());
                    t_model.setText(arr.get(pozitie).getModel());
                    s_motor.setValue(arr.get(pozitie).getMotor());
                    t_horsePower.setValue(arr.get(pozitie).getHorsePower());

                    t_pret.setText(String.valueOf(0.1*arr.get(pozitie).getHorsePower()));
                    ta_descriere.setText(arr.get(pozitie).getDescription());



                }
            }
        });

        b_prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arr.isEmpty()){
                    t_vin.setText("Empty!");
                    t_brand.setText("Empty!");
                    t_model.setText("Empty!");
                    t_pret.setText("Empty!");
                    ta_descriere.setText("Empty!");

                }
                else{
                    if(pozitie == 0)
                        pozitie = arr.size();

                    pozitie -= 1;

                    t_vin.setText(String.valueOf(arr.get(pozitie).getVehicleIdentificationNumber()));
                    t_brand.setText(arr.get(pozitie).getBrend());
                    t_model.setText(arr.get(pozitie).getModel());
                    s_motor.setValue(arr.get(pozitie).getMotor());
                    t_horsePower.setValue(arr.get(pozitie).getHorsePower());

                    t_pret.setText(String.valueOf(0.1*arr.get(pozitie).getHorsePower()));
                    ta_descriere.setText(arr.get(pozitie).getDescription());



                }
            }
        });


        b_first.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arr.isEmpty()){
                    t_vin.setText("Empty!");
                    t_brand.setText("Empty!");
                    t_model.setText("Empty!");
                    t_pret.setText("Empty!");
                    ta_descriere.setText("Empty!");

                }
                else{

                    t_vin.setText(String.valueOf(arr.get(0).getVehicleIdentificationNumber()));
                    t_brand.setText(arr.get(0).getBrend());
                    t_model.setText(arr.get(0).getModel());
                    s_motor.setValue(arr.get(0).getMotor());
                    t_horsePower.setValue(arr.get(0).getHorsePower());

                    t_pret.setText(String.valueOf(0.1*arr.get(0).getHorsePower()));
                    ta_descriere.setText(arr.get(0).getDescription());

                    pozitie = 0;

                }
            }
        });



        b_last.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arr.isEmpty()){
                    t_vin.setText("Empty!");
                    t_brand.setText("Empty!");
                    t_model.setText("Empty!");
                    t_pret.setText("Empty!");
                    ta_descriere.setText("Empty!");

                }
                else{

                    pozitie = arr.size() - 1;

                    t_vin.setText(String.valueOf(arr.get(pozitie).getVehicleIdentificationNumber()));
                    t_brand.setText(arr.get(pozitie).getBrend());
                    t_model.setText(arr.get(pozitie).getModel());
                    s_motor.setValue(arr.get(pozitie).getMotor());
                    t_horsePower.setValue(arr.get(pozitie).getHorsePower());

                    t_pret.setText(String.valueOf(0.1*arr.get(pozitie).getHorsePower()));
                    ta_descriere.setText(arr.get(pozitie).getDescription());


                }
            }
        });


        b_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(arr.isEmpty()) {
                    t_vin.setText("Empty!");
                    t_brand.setText("Empty!");
                    t_model.setText("Empty!");
                    t_pret.setText("Empty!");
                    ta_descriere.setText("Empty!");
                }
                else {
                    arr.remove(pozitie);
                    pozitie--;
                    if (pozitie>=0) {
                        t_vin.setText(String.valueOf(arr.get(pozitie).getVehicleIdentificationNumber()));
                        t_brand.setText(arr.get(pozitie).getBrend());
                        t_model.setText(arr.get(pozitie).getModel());
                        s_motor.setValue(arr.get(pozitie).getMotor());
                        t_horsePower.setValue(arr.get(pozitie).getHorsePower());

                        t_pret.setText(String.valueOf(0.1 * arr.get(pozitie).getHorsePower()));
                        ta_descriere.setText(arr.get(pozitie).getDescription());
                    } else {
                        t_vin.setText(null);
                        t_brand.setText(null);
                        t_model.setText(null);
                        s_motor.setValue(1);
                        t_horsePower.setValue(50);

                        t_pret.setText(null);
                        ta_descriere.setText(null);
                    }
                }
            }
        });


        b_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arr.add(new Car(Integer.parseInt(t_vin.getText()), t_brand.getText(),t_model.getText(),
                        (double)s_motor.getValue(), (Integer) t_horsePower.getValue(), ta_descriere.getText()));
                pozitie = arr.size() - 1;

                t_vin.setText(null);
                t_brand.setText(null);
                t_model.setText(null);
                s_motor.setValue(1);
                t_horsePower.setValue(50);

                t_pret.setText(null);
                ta_descriere.setText(null);

            }
        });


        b_viewList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame(arr);
            }
        });


        t_horsePower.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                t_pret.setText(String.valueOf(0.1 * (Integer) t_horsePower.getValue()));
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                if (!arr.isEmpty()){
                    pozitie = 0;
                    t_vin.setText(String.valueOf(arr.get(pozitie).getVehicleIdentificationNumber()));
                    t_brand.setText(arr.get(pozitie).getBrend());
                    t_model.setText(arr.get(pozitie).getModel());
                    s_motor.setValue(arr.get(pozitie).getMotor());
                    t_horsePower.setValue(arr.get(pozitie).getHorsePower());

                    t_pret.setText(String.valueOf(0.1*arr.get(pozitie).getHorsePower()));
                    ta_descriere.setText(arr.get(pozitie).getDescription());


                }
            }
        });


        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                try {
                    serializare(f);
                } catch (IOException e1) {

                    e1.printStackTrace();
                }
            }
        });


    }


    public static void createFrame(List<Car> ee)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JFrame frame = new JFrame("List of cars");
                frame.setPreferredSize(new Dimension(500,300));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                Car[] e = new Car[ee.size()];
                for (int i = 0; i < ee.size(); i++){
                    e[i] = ee.get(i);
                }
                JList lista = new JList(e);
                JScrollPane scroller = new JScrollPane(lista);
                scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                panel.add(scroller);
                frame.getContentPane().add(BorderLayout.CENTER, panel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setResizable(false);

            }
        });
    }


    void serializare(File f) throws IOException {
        FileOutputStream fos = new FileOutputStream(f);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        bt = new BTree(3);
        for(Car c: arr){
            bt.insert(c);
        }
        oos.writeObject(bt);
        oos.close();
        fos.close();
    }


    void deserializare(File f) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(f);
        ObjectInputStream ois = new ObjectInputStream(fis);

        BTree<?> l = (BTree<?>) ois.readObject();


        l.iterator();
        for (Iterator it = l.iterator(); it.hasNext(); ) {

            Car j = (Car) it.next();
            arr.add((Car) j);

        }

        ois.close();
        fis.close();
    }

}
