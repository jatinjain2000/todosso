package com.todo.entities;

public class Captcha {
        private String characters = "acndjsa12344";

    public Captcha(String characters) {
        this.characters = characters;
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }
}
