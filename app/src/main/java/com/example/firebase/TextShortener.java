package com.example.firebase;

public class TextShortener {
    public String doShortening( int c, String text){
        String[] words = text.split("(?<= )|(?= )|(?<=\\p{Punct})|(?=\\p{Punct})");
        StringBuilder result = new StringBuilder(" ");
        String vowels = "аоиеёэыуюя";
        for (String word : words) {
            if (word.trim().isEmpty() || !Character.isLetter(word.charAt(0))) {
                result.append(word);
                continue;
            }
            if (c == 0){
                return text;
            }
            else if(c==1){
                if (word.length() >= 6) {
                    String base = word.substring(0, 3);
                    String ending = word.substring(1);
                    if (!ending.matches(".*\\p{Punct}")) {
                        ending = ending.substring(ending.length() - 1);
                    }
                    result.append(base).append("-").append(ending);
                } else {
                    result.append(word);
                }
            }
            else if (c == 2){
                if (word.length() >= 6) {
                    String base = word.substring(0, 2);
                    String ending = word.substring(2);
                    if (!ending.matches(".*\\p{Punct}")) {
                        ending = ending.substring(ending.length() - 1);
                    }

                    result.append(base).append("-").append(ending);
                } else {
                    result.append(word);
                }
            }
            else if (c==4){
                if (word.length() > 2) {
                    String base = word.substring(0, 1) ;
                    String ending = " ";
                      if (!word.matches(".*\\p{Punct}")) {
                        ending = word.substring(word.length() - 1);
                    }
                    result.append(base).append("-").append(ending);
                } else {
                    result.append(word);
                }
            }
            else if (c==5){
                if (word.length() > 2) {
                    result.append(word.charAt(0)).append(".");
                } else {
                    result.append(word);
                }
            }
            else if (c==3){
                if (word.length() >= 3) {
                    for (int i = 0; i < vowels.length(); i++) {
                        if (word.contains(String.valueOf(vowels.charAt(i)))) {
                            int index = word.indexOf(vowels.charAt(i));
                            String base = word.substring(0, index); 
                            result.append(base).append(".");
                            break;
                        }
                    }
                }
                else {
                    result.append(word);
                }
            }
        }
        return result.toString();
    }
}
