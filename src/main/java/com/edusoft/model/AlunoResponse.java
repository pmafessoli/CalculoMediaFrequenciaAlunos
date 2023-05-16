package com.edusoft.model;

import java.util.List;

public class AlunoResponse {
	private String resultado;
	private List<AlunoModel> alunos;

	public AlunoResponse(String resultado, List<AlunoModel> alunos) {
		super();
		this.resultado = resultado;
		this.alunos = alunos;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public List<AlunoModel> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<AlunoModel> alunos) {
		this.alunos = alunos;
	}

}
