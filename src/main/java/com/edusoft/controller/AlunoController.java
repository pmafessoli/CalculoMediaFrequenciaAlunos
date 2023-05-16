package com.edusoft.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import com.edusoft.model.AlunoModel;
import com.edusoft.model.AlunoResponse;
import com.edusoft.model.Nota;
import com.edusoft.model.ResultadoAluno;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AlunoController {
	private final String TOKEN_URL = "http://desenvolvimento.edusoft.inf.br/desenvolvimentoMentorWebG5/rest/servicoexterno/token/recuperaAlunos";
	private final String EXECUTE_URL = "http://desenvolvimento.edusoft.inf.br/desenvolvimentoMentorWebG5/rest/servicoexterno/execute/recuperaAlunos";
	private final String RESULTADO_URL = "http://desenvolvimento.edusoft.inf.br/desenvolvimentoMentorWebG5/rest/servicoexterno/token/gravaResultado";
	private final String USERNAME = "mentor";
	private final String PASSWORD = "123456";

	public void processarAlunos() throws InterruptedException {
		try {
			String token = getToken();
			List<AlunoModel> alunos = getAlunos(token);

			calcularMediaFrequencia(alunos);

			enviarResultados(alunos, token);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getToken() throws IOException, InterruptedException {
		String credentials = USERNAME + ":" + PASSWORD;
		String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(TOKEN_URL))
				.header("Authorization", "Basic " + encodedCredentials).build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		return response.body();
	}

	private List<AlunoModel> getAlunos(String token) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(EXECUTE_URL))
				.header("Content-Type", "application/json").header("Authorization", "Bearer " + token)
				.POST(HttpRequest.BodyPublishers.ofString("{}")).build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		ObjectMapper mapper = new ObjectMapper();
		AlunoResponse alunoResponse = mapper.readValue(response.body(), AlunoResponse.class);

		return alunoResponse.getAlunos();
	}

	private void calcularMediaFrequencia(List<AlunoModel> alunos) {
		for (AlunoModel aluno : alunos) {
			List<Nota> notas = aluno.getNotas();

			// Cálculo da média
			double somaNotas = 0;
			for (Nota nota : notas) {
				somaNotas += nota.getNota();
			}
			double media = somaNotas / notas.size();

			// Cálculo da frequência
			int totalFaltas = 0;
			for (Nota nota : notas) {
				totalFaltas += nota.getFaltas();
			}
			double frequencia = 100 - ((100.0 * totalFaltas) / aluno.getTotalAulas());
			// Definir o resultado do aluno
			String resultado;
			if (frequencia < 70) {
				resultado = "RF";
			} else if (media >= 7) {
				resultado = "AP";
			} else {
				resultado = "RM";
			}
			// Atualizar o resultado no objeto Aluno
			aluno.setMedia(media);
			aluno.setFrequencia(frequencia);
			aluno.setResultado(resultado);
		}
	}

	private void enviarResultados(List<AlunoModel> alunos, String token) throws IOException, InterruptedException {
		ObjectMapper mapper = new ObjectMapper();

		for (AlunoModel aluno : alunos) {
			ResultadoAluno resultadoAluno = new ResultadoAluno();
			resultadoAluno.setCOD(aluno.getCodigo());
			resultadoAluno.setMEDIA(aluno.getMedia());
			resultadoAluno.setRESULTADO(aluno.getResultado());

			String jsonBody = mapper.writeValueAsString(resultadoAluno);

			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(RESULTADO_URL))
					.header("Content-Type", "application/json").header("Authorization", "Bearer " + token)
					.POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();

			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		
		}

	}
}
