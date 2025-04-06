import java.io.*;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Monitor de Perda de Pacotes via Ping
 *
 * Este programa realiza testes de conectividade enviando um único pacote (ping)
 * para uma lista de hosts (servidores DNS e um roteador local). Caso seja detectada
 * perda de pacote (baseada em palavras-chave na saída do comando), o programa gera um
 * alerta contendo a data, hora, host testado e o resultado do teste. O alerta é exibido
 * no console e também registrado em um arquivo de log.
 *
 * <p>
 * As palavras-chave "Esgotado" ou "perdidos = 1" na saída do comando ping indicam que houve
 * perda de pacote.
 * </p>
 *
 * <p>
 * Bibliotecas utilizadas:
 * <ul>
 *   <li>java.io.* - Para leitura da saída do comando e escrita de logs em arquivo.</li>
 *   <li>java.text.Normalizer - Para normalizar a saída e remover acentos e caracteres especiais.</li>
 *   <li>java.text.SimpleDateFormat e java.util.Date - Para formatar a data e hora no alerta.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Uso no terminal:
 * <ol>
 *   <li>Compile: javac Main.java</li>
 *   <li>Execute: java Main</li>
 * </ol>
 * </p>
 *
 * @author Lucas Melo
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        // Lista de hosts a serem testados (servidores DNS e roteador local)
        String[] hosts = {
                "8.8.8.8", "8.8.4.4",         // Google DNS
                "1.1.1.1", "1.0.0.1",         // Cloudflare DNS
                "208.67.222.222", "208.67.220.220", // OpenDNS
                "9.9.9.9",                    // Quad9
                "4.2.2.2",                    // Level3
                "8.26.56.26",                 // Comodo
                "156.154.70.1",               // Neustar UltraDNS
                "192.168.5.1"                 // Roteador local
        };

        // Caminho do arquivo de log para registrar os alertas
        String caminhoLog = "log_alertas.txt";

        // Mensagens iniciais no console
        System.out.println(" > Programa iniciado com sucesso!");
        System.out.println(" > Testando... Qualquer novidade iremos te avisar! \n\n");

        // Loop infinito para testes contínuos
        while (true) {
            try {
                // Itera sobre cada host na lista
                for (String host : hosts) {
                    // Cria um processo para executar o comando ping
                    ProcessBuilder processBuilder = new ProcessBuilder("ping", "-n", "1", host);
                    Process process = processBuilder.start();

                    // Lê a saída do comando ping
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    boolean perdaPacote = false;
                    StringBuilder resultadoTeste = new StringBuilder();

                    // Processa cada linha da saída
                    while ((line = reader.readLine()) != null) {
                        // Normaliza a linha, removendo acentos e caracteres não-ASCII
                        String normalizedLine = Normalizer.normalize(line, Normalizer.Form.NFD)
                                .replaceAll("[^\\p{ASCII}]", "");
                        resultadoTeste.append(normalizedLine).append("\n");

                        // Detecta perda de pacote através de palavras-chave
                        if (normalizedLine.contains("Esgotado") || normalizedLine.contains("perdidos = 1")) {
                            perdaPacote = true;
                        }
                    }

                    // Se perda de pacote foi detectada, gera e registra o alerta
                    if (perdaPacote) {
                        // Cria um timestamp com data e hora
                        String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
                        // Formata a mensagem de alerta
                        String alerta = " *** ALERTA [" + timestamp + "] - HOST: " + host + " - Perda de pacote detectada! ***\n"
                                + resultadoTeste + "--------------------------------------------------------------------------------\n";

                        // Exibe o alerta no console
                        System.out.println(alerta);

                        // Escreve o alerta no arquivo de log (modo append)
                        try (FileWriter fw = new FileWriter(caminhoLog, true);
                             BufferedWriter bw = new BufferedWriter(fw);
                             PrintWriter out = new PrintWriter(bw)) {
                            out.println(alerta);
                        } catch (IOException e) {
                            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
                        }
                    }

                    reader.close();
                    // Thread.sleep pode ser reativado se necessário para espaçar os testes
                    // Thread.sleep(Math.round(intervaloSegundos * 1000));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
