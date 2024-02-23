package com.company.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

public class Data {

    String filename;
    Collection<Lesson> lessons = new LinkedList<>();

    public Data(String filename) {
        this.filename = filename;
    }

    public Collection<Lesson> getLessons() {
        return lessons;
    }

    public void createLesson(String lessonName) {
        lessons.add(new Lesson(lessonName));
    }

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

}
