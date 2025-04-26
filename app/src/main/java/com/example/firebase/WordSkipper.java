package com.example.firebase;

import java.util.Random;

public class WordSkipper {
    public String[] doSkip(String text){
        String[] words = text.split("(?<= )|(?= )|(?<=\\p{Punct})|(?=\\p{Punct})"); // Разделение с сохранением пунктуации
        String result = " ";
        Random rand = new Random();

        int randomNum = rand.nextInt(words.length );
        while (words[randomNum].trim().isEmpty() || !Character.isLetter(words[randomNum].charAt(0))){
            randomNum = rand.nextInt(words.length );
        }

        System.out.println(words[randomNum]);
        String ans = words[randomNum];
        words[randomNum] = "_".repeat(words[randomNum].length());

        for (String word : words) {
            result += word; // Слова < 3 символов не сокращаем
        }

        String[] r = {result, ans};
        return r;
    }
}
