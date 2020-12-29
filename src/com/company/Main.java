package com.company;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File f = new File("Carsf");
        Gui obj = new Gui(f);
        obj.setVisible(true);
        obj.setLocationRelativeTo(null);
        obj.setResizable(false);


    }
}
