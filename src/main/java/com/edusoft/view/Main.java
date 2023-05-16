package com.edusoft.view;

import com.edusoft.controller.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        AlunoController alunoController = new AlunoController();
        alunoController.processarAlunos();
    }
}
