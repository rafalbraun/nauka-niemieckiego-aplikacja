package com.company.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Data {

    String filename;
    //Collection<Lesson> lessons = new LinkedList<>();
    Map<String, Lesson> lessons = new HashMap<>();
    Lesson chosenLesson;

    public Data() {
        readData();
    }

    public Map<String, Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Map<String, Lesson> lessons) {
        this.lessons = lessons;
    }

    public Lesson createLesson(String lessonName) {
        Lesson lesson = new Lesson();
        lessons.put(lessonName, lesson);
        return lesson;
    }

    public Lesson getChosenLesson() { return chosenLesson; }

    public void setChosenLesson(Lesson lesson) { this.chosenLesson = lesson; }

    public void saveData(Lesson lesson) {
        String filename = "resources/lessons/"+lesson+".txt";
        StringBuilder buffer = new StringBuilder();
        String[][] words = lesson.getWords();
        for (int i=0; i<words.length; i++) {
            buffer.append(words[i][0]+";"+words[i][1]+"\n");
        }
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(String.valueOf(buffer));
            writer.close();
            System.out.println("Success");
        } catch (IOException ex) {
            System.out.println("ERROR");
            ex.printStackTrace();
        }
    }

    public void readData() {

        Map<String, Lesson> lessons = new HashMap<>();

        File folder = new File("resources/lessons");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null) return;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                //System.out.println(file.getName());
                Lesson lesson = new Lesson();
                try {
                    Scanner reader = new Scanner(file);
                    while (reader.hasNextLine()) {
                        String data = reader.nextLine();
                        //System.out.println(data);
                        String[] line = data.split(";");
                        lesson.addWord(line[0], line[1]);
                    }
                    reader.close();
                } catch (FileNotFoundException ex) {
                    System.out.println("ERROR");
                    ex.printStackTrace();
                }
                lessons.put(file.getName(), lesson);
            }
        }

        setLessons(lessons);
        setChosenLesson(getFirstLesson());

/*
        try {
            File file = new File(filename);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                System.out.println(data);
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR");
            ex.printStackTrace();
        }*/
    }


    private Lesson getFirstLesson() {
        Map<String, Lesson> map = lessons; // Your map here
        if (!map.isEmpty()) {
            Map.Entry<String, Lesson> entry = map.entrySet().iterator().next();
            return entry.getValue();
        }
        return null;
    }

/*
    public void saveData() {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write("...");
            writer.close();
            System.out.println("Success");
        } catch (IOException ex) {
            System.out.println("ERROR");
            ex.printStackTrace();
        }
    }

    public void readData() {
        try {
            File file = new File(filename);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                System.out.println(data);
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR");
            ex.printStackTrace();
        }
    }
*/


}
