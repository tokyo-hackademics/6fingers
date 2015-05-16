package com.fingers.six.elarm.com.fingers.six.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * Created by Nghia on 5/16/2015.
 */
public class DatabaseManager {
    private TreeSet<QuestionList> _questionDatabase;
    private final String DB_PATH = "db";
    public DatabaseManager() throws Exception {
        loadData();
    }

    private void loadData() throws Exception {
        File folder = new File(DB_PATH);
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory() || !fileEntry.getName().endsWith(".edb")) {
                continue;
            } else {
                loadQuestionListFromFile(fileEntry.getCanonicalFile());
            }
        }
    }

    private void loadQuestionListFromFile(File file) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        StringTokenizer tok;
        QuestionList list = new QuestionList(_questionDatabase.size() + 1, file.getName());
        while (line != null) {
            tok = new StringTokenizer(line, "|");
            list.addNewWord(tok.nextToken(), tok.nextToken(), Integer.parseInt(tok.nextToken()));
        }
        _questionDatabase.add(list);

        br.close();
    }

    public void addNewQuestionList(String name) {
        _questionDatabase.add(new QuestionList(_questionDatabase.size() + 1, name));

    }

    public TreeSet<QuestionList> get_questionDatabase() {
        return _questionDatabase;
    }

    public void set_questionDatabase(TreeSet<QuestionList> _questionDatabase) {
        this._questionDatabase = _questionDatabase;
    }
}
