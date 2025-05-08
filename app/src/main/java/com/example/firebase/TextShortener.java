package com.example.firebase;

public class TextShortener {
    public String doShortening( int c, String text){
        String[] words = text.split("(?<= )|(?= )|(?<=\\p{Punct})|(?=\\p{Punct})"); // Разделение с сохранением пунктуации
        String result = " ";
        String vowels = "аоиеёэыуюя";
        for (String word : words) {
            if (word.trim().isEmpty() || !Character.isLetter(word.charAt(0))) {
                result+=word;
                continue;
            }
            if (c == 0){
                return text;
            }
            else if(c==1){
                if (word.length() >= 6) {
                    String base = word.substring(0, 3); //fiojvjsdf
                    String ending = word.substring(1);
                    if (!ending.matches(".*\\p{Punct}")) {
                        ending = ending.substring(ending.length() - 1);
                    }
                    result = result + base + "-" + ending;
                } else {
                    result+=word;
                }
            }
            else if (c == 2){
                if (word.length() >= 6) {
                    String base = word.substring(0, 2);
                    String ending = word.substring(2);
                    if (!ending.matches(".*\\p{Punct}")) {
                        ending = ending.substring(ending.length() - 1);
                    }

                    result = result + base + "-" + ending;
                } else {
                    result+=word;
                }
            }
            else if (c==4){
                if (word.length() > 2) {
                    String base = word.substring(0, 1) ;
                    String ending = " ";
                      if (!word.matches(".*\\p{Punct}")) {
                        ending = word.substring(word.length() - 1);
                    }
                    result = result + base + "-" + ending;
                } else {
                    result += word;
                }
            }
            else if (c==5){
                if (word.length() > 2) {
                    result += word.substring(0, 1)+".";
                } else {
                    result += word;
                }
            }
            else if (c==3){
                if (word.length() >= 3) {
                    for (int i = 0; i < vowels.length(); i++) {
                        if (word.contains(String.valueOf(vowels.charAt(i)))) {
                            int index = word.indexOf(vowels.charAt(i));
                            String base = word.substring(0, index); //fiojvjsdf
                            result = result + base + ".";
                            break;
                        }
                    }
                }
                else {
                    result+=word;
                }
            }
        }
        return result;
    }
}
