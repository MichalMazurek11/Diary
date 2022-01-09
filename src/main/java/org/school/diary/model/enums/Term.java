package org.school.diary.model.enums;

import java.util.Arrays;

public enum Term {
    SEMESTR_I("SEMESTR I"),
    SEMESTR_II("SEMESTR II");






    private String text;

    Term(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public Term getByValue(String value){
        return Arrays.stream(Term.values()).filter(enumRole -> enumRole.name().equals(value)).findFirst().orElse(SEMESTR_I);
    }



}
