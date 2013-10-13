package com.unit7.study.translationmethods.labs.lab1;

import java.util.Arrays;

import javax.swing.JOptionPane;

import com.unit7.study.translationmethods.labs.exceptions.InformationMessageException;

public class RightDataAnalizer implements Analizer {
    @Override
    public boolean analize(Object param) throws InformationMessageException {
        if (!(param instanceof String[])) {
            throw new IllegalArgumentException("Param must be string array");
        }

        String[] array = (String[]) param;
        if (array.length != 4) {
            throw new IllegalArgumentException("Param.length must be equal 4");
        }

        String terminalsRaw = array[0].trim().replaceAll(" +", " ");
        String notTerminalsRaw = array[1].trim().replaceAll(" +", " ");
        String targetSymbolRaw = array[2].trim().replaceAll(" +", " ");
        String lenRaw = array[3].trim().replaceAll(" +", " ");

        if (isCleaned(targetSymbolRaw) || isCleaned(lenRaw)
                || isCleaned(notTerminalsRaw) || isCleaned(terminalsRaw)) {
            throw new InformationMessageException(
                    "Все поля обязательны для заполнения!");
        }

        Parser parser = new InputDataParser();
        String[] terminals = parser.parse(terminalsRaw).split("");
        for (String terminal : terminals) {
            if (terminal.isEmpty())
                continue;
            
            if (terminal.length() != 1) {
                throw new InformationMessageException("Терминал должен быть единичной длины");
            }
            if (GrammarRules.TERMINALS.indexOf(terminal) == -1) {
                throw new InformationMessageException(
                        "Задан неверный терминал");
            }
        }

        // each char must be unique
        Arrays.sort(terminals);
        if (!checkUnique(terminals)) {
            throw new InformationMessageException(
                    "Заданы одинаковые терминалы");
        }

        String[] notTerminals = parser.parse(notTerminalsRaw).split("");
        for (String terminal : notTerminals) {
            if (terminal.isEmpty())
                continue;
            
            if (terminal.length() != 1) {
                throw new InformationMessageException("Нетерминал должен быть единичной длины");
            }
            if (GrammarRules.NOT_TERMINALS.indexOf(terminal) == -1) {
                throw new InformationMessageException(
                        "Задан неверный нетерминал");
            }
        }

        Arrays.sort(notTerminals);
        if (!checkUnique(notTerminals)) {
            throw new InformationMessageException(
                    "Заданы одинаковые нетерминалы");
        }

        if (targetSymbolRaw.length() != 1) {
            throw new InformationMessageException("Целевой символ должен быть единичной длины");
        }
        
        if (notTerminalsRaw.indexOf(targetSymbolRaw) == -1) {
            throw new InformationMessageException(
                    "Задан неверный целевой символ");
        }

        int len = -1;
        try {
            len = Integer.parseInt(lenRaw);
            if (len <= 0)
                throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            throw new InformationMessageException(
                    "Поле длины цепочки задано неверно");
        }

        return true;
    }

    private boolean isCleaned(String arg) {
        return arg == null || arg.length() == 0;
    }

    private boolean checkUnique(String[] str) {
        for (int i = 0; i < str.length - 1; ++i) {
            if (str[i].equals(str[i + 1]))
                return false;
        }

        return true;
    }
}
