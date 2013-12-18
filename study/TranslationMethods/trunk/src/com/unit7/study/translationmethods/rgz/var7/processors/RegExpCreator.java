/**
 * Date:	15 дек. 2013 г.
 * File:	RegExpCreator.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.rgz.var7.processors;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.rgz.var5.processors.Creator;
import com.unit7.study.translationmethods.rgz.var5.processors.GrammarCreator;
import com.unit7.study.translationmethods.rgz.var7.RegExp;

/**
 * @author unit7
 * 
 */
public class RegExpCreator implements Creator<RegExp>, ActionListener {
    public RegExpCreator(JTextField alph, JTextField begin, JTextField character, JTextField mulChar, JTextField min, JTextField max) {
        this.alph = alph;
        this.begin = begin;
        this.mulChar = mulChar;
        this.min = min;
        this.max = max;
        this.character = character;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            create();
        } catch (InformationException ex) {
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
            return;
        }

        JFrame frame = new JFrame();

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        JPanel rules = new JPanel();
        rules.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JLabel rulesLabel = new JLabel("Выражение: ");

        JTextArea rulesArea = new JTextArea(10, 40);
        JScrollPane pane = new JScrollPane(rulesArea);

        rules.add(rulesLabel);
        rules.add(pane);

        pane.revalidate();

        rulesArea.append(regExp.getExpr());
        rulesArea.append("\r\n");

        main.add(rules);

        frame.setTitle("Регулярное выражение");
        frame.setContentPane(main);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.unit7.study.translationmethods.rgz.var5.processors.Creator#create()
     */
    @Override
    public RegExp create() throws InformationException {
        String alph = this.alph.getText().trim().replaceAll("\\s", "");
        String beg = this.begin.getText().trim().replaceAll("\\s", "");
        String mulChar = this.mulChar.getText().trim().replaceAll("\\s", "");
        String character = this.character.getText().trim().replaceAll("\\s", "");
        String startLen = this.min.getText().trim();
        String endLen = this.max.getText().trim();

        if (alph.equals("")) {
            throw new InformationException(String.format(GrammarCreator.EMPTY_INPUT_PARAMETER, "Алфавит"));
        }

        if (beg.equals("")) {
            throw new InformationException(String.format(GrammarCreator.EMPTY_INPUT_PARAMETER, "Начальная подцепочка"));
        }

        if (character.equals("")) {
            throw new InformationException(String.format(GrammarCreator.EMPTY_INPUT_PARAMETER, "Символ кратности"));
        }
        
        int multipleChar = 0;
        if (mulChar.equals("")) {
            throw new InformationException(String.format(GrammarCreator.EMPTY_INPUT_PARAMETER, "Кратность символа"));
        } else {
            try {
                multipleChar = Integer.parseInt(mulChar);
            } catch (NumberFormatException ex) {
                throw new InformationException(String.format(GrammarCreator.WRONG_INPUT_PARAMETER, "Кратность символа"));
            }
        }

        int start = 0;
        if (startLen.equals("")) {
            throw new InformationException(String.format(GrammarCreator.EMPTY_INPUT_PARAMETER, "Минимальная длина"));
        } else {
            try {
                start = Integer.parseInt(startLen);
            } catch (NumberFormatException ex) {
                throw new InformationException(String.format(GrammarCreator.WRONG_INPUT_PARAMETER, "Минимальная длина"));
            }
        }

        int fin = 0;
        if (startLen.equals("")) {
            throw new InformationException(String.format(GrammarCreator.EMPTY_INPUT_PARAMETER, "Максимальная длина"));
        } else {
            try {
                fin = Integer.parseInt(endLen);
            } catch (NumberFormatException ex) {
                throw new InformationException(
                        String.format(GrammarCreator.WRONG_INPUT_PARAMETER, "Максимальная длина"));
            }
        }

        if (start > fin)
            throw new InformationException("Минимальная длина больше максимальной");

        String[] alphArr = alph.split("");
        Set<String> terms = new HashSet<String>();
        for (String str : alphArr) {
            if (str.equals(""))
                continue;

            if (str.length() > 1)
                throw new InformationException();

            terms.add(str);
        }

        if (alphArr.length != terms.size() + 1)
            throw new InformationException(GrammarCreator.SAME_TERMINALS);

        // проверка принадлежности кртаного символа алфавиту
        if (!terms.contains(character))
            throw new InformationException(String.format(GrammarCreator.WRONG_INPUT_PARAMETER, "Кратный символ"));
        
        // проверка принаждлежности подцепочек алфавиту
        for (int i = 0; i < beg.length(); ++i) {
            if (!terms.contains(String.valueOf(beg.charAt(i)))) {
                throw new InformationException(String.format(GrammarCreator.WRONG_INPUT_PARAMETER, "начальная подцепочка"));
            }
        }
        
        StringBuilder expr = new StringBuilder().append(beg);
        
        int startCount = 0;
        // количество кратного символа в начальной подцепочке
        for (int i = 0; i < beg.length(); ++i) {
            if (beg.charAt(i) == character.charAt(0))
                startCount += 1;
        }
        
        StringBuilder anyChain = new StringBuilder().append("(");
        for (int i = 0; i < alph.length(); ++i) {
            if (alph.charAt(i) != character.charAt(0))
                anyChain.append(alph.charAt(i)).append("+");
        }
        
        anyChain.delete(anyChain.length() - 1, anyChain.length()).append(")*");
        
        String any = anyChain.toString();
        
        if (alph.length() == 1)
            any = "";
        
        expr.append(any);
        
        // добиваем кратность
        for (int i = 0; i < multipleChar - startCount; ++i) {
            expr.append(character).append(any);
        }
        
        // добиваем остальную подцепочку
        expr.append("(");
        for (int i = 0; i < multipleChar; ++i) {
            expr.append(character).append(any);
        }
        
        expr.append(")*");
        
        regExp = new RegExp(expr.toString(), alph, start, fin);
        
        return regExp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.unit7.study.translationmethods.rgz.var5.processors.Creator#getCreated
     * ()
     */
    @Override
    public RegExp getCreated() throws Exception {
        if (regExp == null)
            throw new Exception(Creator.EMPTY_PARAMETER);
        
        return regExp;
    }

    private JTextField alph;
    private JTextField begin;
    private JTextField mulChar;
    private JTextField min;
    private JTextField max;
    private JTextField character;

    private RegExp regExp;
}
