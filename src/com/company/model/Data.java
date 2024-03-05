package com.company.model;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;

public class Data {

    private static final String myDocuments = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
    private static final File propsFile = Paths.get(myDocuments, "app.conf").toFile();
    private static final String DEFAULT_DIR = "default.dir";

    Map<String, Lesson> lessons = new HashMap<>();
    Lesson chosenLesson;
    Properties props;
    boolean isChanged;      // hold information if the user made changes to ask if he wants to save on exit

    public Data() {

        this.props = loadProperties();

        if (props.getProperty(DEFAULT_DIR) == null) {

            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
            chooser.setDialogTitle("Wybierz Folder");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            int choice = chooser.showOpenDialog(null);
            if (choice == JFileChooser.APPROVE_OPTION) {
                String selectedPath = chooser.getSelectedFile().getAbsolutePath();

                try (OutputStream output = new FileOutputStream(propsFile)) {

                    props.setProperty(DEFAULT_DIR, selectedPath);
                    props.store(output, "User properties");

                } catch (Exception e ) {
                    e.printStackTrace();
                }

            } else {
                System.exit(1);
            }
        }

        try {
            readData();
        } catch (FileNotFoundException | ParseException ex) {
            JOptionPane.showConfirmDialog(null,
                    "Error:" + ex.getMessage(), "Błąd", JOptionPane.DEFAULT_OPTION);
        }
    }

    public File getDefaultDir() {
        return new File(props.getProperty("default.dir"));
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

    public void removeLesson(String lessonName) {
        System.out.println(lessonName);
        lessons.remove(lessonName);
    }

    public Lesson getChosenLesson() { return chosenLesson; }

    public void setChosenLesson(Lesson lesson) { this.chosenLesson = lesson; }

    public void saveData() {
        for (Map.Entry<String, Lesson> entry : lessons.entrySet()) {
            String lessonName = entry.getKey();
            Lesson lesson = entry.getValue();

            File file = Paths.get(getDefaultDir().getAbsolutePath(), lessonName).toFile();
            saveWordListToFile(lesson.getWordList(), file);
        }
    }

    public static void saveWordListToFile(List<Pair<String, String>> wordList, File file) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Pair<String, String> pair : wordList) {
                String first = pair.getFirst();
                String second = pair.getSecond();
                writer.println(first + "\t" + second);
            }
        } catch (IOException ex) {
            JOptionPane.showConfirmDialog(null,
                    "Błąd zapisu:" + ex.getMessage(), "Błąd", JOptionPane.DEFAULT_OPTION);
        }
    }

    public void readData() throws FileNotFoundException, ParseException {
        Map<String, Lesson> lessons = new HashMap<>();
        File[] listOfFiles = getDefaultDir().listFiles();

        if (listOfFiles == null) return;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                Lesson lesson = readLesson(file);
                lessons.put(file.getName(), lesson);
            }
        }

        setLessons(lessons);
        setChosenLesson(getFirstLesson());
    }

    private Lesson readLesson(File file) throws FileNotFoundException, ParseException {
        Lesson lesson = new Lesson();
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            String[] line = data.split("\\t");
            if (line.length != 2) {
                throw new ParseException("Plik nieprawidłowy: odczytano więcej niż dwie wartości w linii.", -1);
            }
            lesson.addWord(line[0], line[1]);
        }
        reader.close();
        return lesson;
    }

    private Lesson getFirstLesson() {
        Map<String, Lesson> map = lessons;
        if (!map.isEmpty()) {
            Map.Entry<String, Lesson> entry = map.entrySet().iterator().next();
            return entry.getValue();
        }
        return null;
    }

    private static Properties loadProperties() {
        Properties props = new Properties();

        try (InputStream input = new FileInputStream(propsFile)) {
            props.load(input);
        } catch (Exception e ) {
            e.printStackTrace();
        }

        return props;
    }

    public void importFile(String path) throws FileNotFoundException, ParseException {
        File file = new File(path);
        Lesson lesson = readLesson(file);
        lessons.put(file.getName(), lesson);
        setLessons(lessons);
        setChosenLesson(getFirstLesson());
    }

}
