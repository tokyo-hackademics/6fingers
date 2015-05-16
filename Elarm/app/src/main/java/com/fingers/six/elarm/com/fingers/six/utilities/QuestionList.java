package com.fingers.six.elarm.com.fingers.six.utilities;

import java.util.TreeSet;

/**
 * Created by Nghia on 5/16/2015.
 */
public class QuestionList {
    private int _id;
    private int _lastId;
    private String _name;
    private TreeSet<Word> _wordList;
    private int[] _status;

    public QuestionList(int id, String name) {
        set_id(id);
        set_name(name);
        set_wordList(new TreeSet<Word>());
        set_status(new int[3]);
        set_lastId(0);
    }

    public boolean addNewWord(String jap, String eng, int score) {
        try {
            Word w = new Word(get_lastId() + 1, jap, eng, score);
            _wordList.add(w);
            Utilities.appendToFile(_name + ".edb", w.toString());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean removeWord(int id) {
        try {
            for (Word w : _wordList) {
                if (w.get_id() == id)
                {
                    _wordList.remove(w);
                    Utilities.writeAllToFile(_name + ".edb", this.toString());
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean updateWord(int id, String jap, String eng) {
        removeWord(id);
        return addNewWord(jap, eng, 0);
    }

    public Word search(String query) {
        for (Word w : _wordList) {
            if (w.get_eng().equals(query) || w.get_jap().equals(query))
            {
                return w;
            }
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Word w : _wordList) {
            sb.append(w.toString() + System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    public TreeSet<Word> get_wordList() {
        return _wordList;
    }

    public void set_wordList(TreeSet<Word> _wordList) {
        this._wordList = _wordList;
    }

    public int[] get_status() {
        return _status;
    }

    public void set_status(int[] _status) {
        this._status = _status;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_lastId() {
        return _lastId;
    }

    public void set_lastId(int _lastId) {
        this._lastId = _lastId;
    }
}
