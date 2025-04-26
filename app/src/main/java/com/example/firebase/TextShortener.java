package com.example.firebase;

public class TextShortener {
    public String doShortening( int c, String text){
        String[] words = text.split("(?<= )|(?= )|(?<=\\p{Punct})|(?=\\p{Punct})"); // Разделение с сохранением пунктуации
        String result = " ";
        for (String word : words) {
            if (word.trim().isEmpty() || !Character.isLetter(word.charAt(0))) {
                result+=word; // Пробелы и знаки препинания добавляем как есть
                continue;
            }
            if (c == 0){
                return text;
            }
            else if (c == 1){
                if (word.length() >= 6) {
                    String base = word.substring(0, 2);
                    String ending = word.substring(2);

                    // Убираем лишние буквы в конце (оставляем только последнюю или часть слова)
                    if (ending.length() > 1 && !ending.matches(".*\\p{Punct}")) {
                        ending = ending.substring(ending.length() - 1);
                    }

                    result = result + base + "-" + ending;
                } else {
                    result+=word; // Слова < 3 символов не сокращаем
                }
            }
            else if (c ==2 ){
                if (word.length() > 2) {
                    String base = word.substring(0, 1) ;
                    //   String ending = word.substring(2);
                    String ending = " ";
                    // Убираем лишние буквы в конце (оставляем только последнюю или часть слова)
                    if (word.length() > 1 && !word.matches(".*\\p{Punct}")) {
                        ending = word.substring(word.length() - 1);
                    }
                    result = result + base + "-" + ending;
                } else {
                    result += word; // Слова < 3 символов не сокращаем
                }
            }
            else if (c==3){
                if (word.length() > 2) {
                    result += word.substring(0, 1)+".";
                } else {
                    result += word; // Слова < 3 символов не сокращаем
                }
            }
            else{
                return "";
            }
        }

        return result;
    }

}
