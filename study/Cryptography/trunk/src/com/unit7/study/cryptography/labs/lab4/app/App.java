package com.unit7.study.cryptography.labs.lab4.app;

import javax.swing.JFrame;

import com.unit7.study.cryptography.labs.lab4.forms.MainForm;

public class App {
    public static void main(String[] args) {
        JFrame app = new MainForm().createGUI();
        app.setVisible(true);
    }
}
