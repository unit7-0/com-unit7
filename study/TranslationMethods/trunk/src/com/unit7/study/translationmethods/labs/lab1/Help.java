package com.unit7.study.translationmethods.labs.lab1;

public class Help {
    public static final String HELP = 
            "<html>" +
            "Для построения цеопчек необходимо ввести список терминалов, <br>" +
    		"нетерминалов, целевой символ и максимальную длину цепочек.<br>" +
    		"Затем нажать кнопку \"Подготовить\". В поле терминалов можно вводить только<br>" +
    		"строчные латинские буквы, а также пара скобок - (), либо все слитно, либо каждая через пробел, либо запятую.<br>" +
    		"В поле нетерминалов можно вводить только прописные латинские буквы с такими же правилами<br>" +
    		"как и для терминалов. В поле целевого символа можно ввести только один символ,<br>" +
    		"который задан в нетерминалах. В поле длины цепочек только цифры в пределах разумного.<br>" +
    		"После нажатия кнопки \"Подготовить\" появятся поля для задания правил каждому нетерминалу.<br>" +
    		"Правила могу состоять из терминалов, нетерминалов, символа разделителя — | и пустого символа — &.<br>" +
    		"После задания правил нужно нажать кнопку \"Построить\", появится окно с построенными цепочками,<br>" +
    		"если таковые возможны. Можно сбросить все правила и вернуться к редактированию начальных<br>" +
    		"условий, нажав кнопку \"Сбросить\". Также имеется возможность очистить все поля нажатием кнопки<br>" +
    		"\"Очистить\". Любой ввод проверяется на корректность и если существует ошибка, о ней будет выведено<br>" +
    		"сообщение.<br>" +
    		"</html>";
}