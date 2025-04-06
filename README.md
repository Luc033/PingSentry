# PingSentry

**PingSentry** é um sistema de monitoramento de conectividade desenvolvido em Java. O programa realiza testes de ping em uma lista de hosts (incluindo servidores DNS públicos e um roteador local) e detecta perda de pacotes. Quando um problema é identificado, o sistema gera um alerta que é exibido em tempo real no console e registrado em um arquivo de log.

## Funcionalidades

- **Monitoramento Contínuo:** Testa continuamente a conectividade dos hosts.
- **Detecção de Perda de Pacotes:** Identifica perda de pacotes com base em palavras-chave na saída do comando ping.
- **Registro de Alertas:** Exibe alertas no console e salva os registros em um arquivo `log_alertas.txt`.
- **Suporte a Múltiplos Hosts:** Monitoramento de diversos servidores DNS (Google, Cloudflare, OpenDNS, Quad9, Level3, Comodo, Neustar) e um roteador local.

## Lista de Hosts Monitorados

- **Google DNS:** `8.8.8.8`, `8.8.4.4`
- **Cloudflare DNS:** `1.1.1.1`, `1.0.0.1`
- **OpenDNS:** `208.67.222.222`, `208.67.220.220`
- **Quad9:** `9.9.9.9`
- **Level3:** `4.2.2.2`
- **Comodo:** `8.26.56.26`
- **Neustar UltraDNS:** `156.154.70.1`
- **Roteador Local:** `192.168.5.1`

## Pré-Requisitos

- Java Development Kit (JDK) 8 ou superior instalado.
- Sistema operacional Windows (o comando `ping` utilizado é compatível com Windows).

## Como Executar
Abra o arquivo `PingSentry` e entre na pasta `src` pelo terminal.

1. **Compile o código:**

   ```bash
   javac Main.java
   ```
   
2. **Execute o programa:**

   ```bash
   java Main
   ```

## Funcionamento

Ao iniciar, o programa exibe uma mensagem indicando que o monitoramento foi iniciado. Em seguida, ele realiza um teste de ping para cada host definido na lista. Se a saída do comando indicar perda de pacote (através das palavras-chave "Esgotado" ou "perdidos = 1"), o programa:
- Gera um alerta contendo a data, hora, host e resultado do teste.
- Exibe o alerta no console.
- Registra o alerta no arquivo `log_alertas.txt`.

## Exemplo de Alerta

```
 *** ALERTA [05/04/2025 15:00:02] - HOST: 8.8.8.8 - Perda de pacote detectada! ***
Resposta de 8.8.8.8: bytes=32 tempo=25ms TTL=52
Estatisticas do Ping para 8.8.8.8:
    Pacotes: Enviados = 1, Recebidos = 0, Perdidos = 1 (100% de perda)
--------------------------------------------------------------------------------
```

## Possíveis Melhorias Futuras

- **Intervalo de Teste Ajustável:** Habilitar configuração do intervalo entre testes.
- **Interface Gráfica:** Desenvolvimento de uma interface com Swing ou JavaFX para monitoramento visual.
- **Notificações:** Envio de alertas por e-mail, Telegram ou outros canais.
- **Relatórios:** Geração de relatórios históricos de conectividade (CSV, PDF ou painel web).

## Contribuição

Sinta-se à vontade para contribuir com melhorias ou relatar problemas. Abra uma *issue* ou envie um *pull request*.

## Licença

Este projeto é licenciado sob a [MIT License](LICENSE).

---

Desenvolvido por **[Lucas Melo](https://www.linkedin.com/in/lucas-melo-dev)**.
```