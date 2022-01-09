package org.school.diary.model.enums;

import org.school.diary.model.Mark;

import java.util.Arrays;

public enum TypeMark {

    SPRAWDZIAN("SPRAWDZIAN"),
    KARTKÓWKA("KARTKÓWKA"),
    PRACA_DOMOWA("PRACA DOMOWA"),
    ODPOWIEDŹ("ODPOWIEDŹ"),
    AKTYWNOŚĆ("AKTYWNOŚĆ"),
    INNE("INNE");


    private String text;

    TypeMark(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public TypeMark getByValue(String value){
        return Arrays.stream(TypeMark.values()).filter(enumRole -> enumRole.name().equals(value)).findFirst().orElse(SPRAWDZIAN);
    }





}
