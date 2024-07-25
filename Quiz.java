import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

interface Pontuavel {
    int getPontuacao();
    String getNome();
}

class Jogador implements Pontuavel {
    public Jogador(Pontuavel pontuavel) {
    }

    @Override
    public int getPontuacao() {
        return 10;
    }

    @Override
    public String getNome() {
        return "Bem-vindo Aluno de POO";
    }
}

abstract class Jogo implements ActionListener, Pontuavel {
    protected JFrame quadro;
    protected JPanel painel;
    protected int perguntaAtual = 0;
    protected int pontuacao = 0;
    protected String nomeUsuario;

    public void iniciarJogo() {
        SwingUtilities.invokeLater(() -> {
            criarGUI();
        });
    }

    protected abstract void criarGUI();
    protected abstract void atualizarConteudoPainel();
    protected abstract void exibirPlacarLideres();
    protected abstract void salvarPontuacaoNoArquivo();

    @Override
    public abstract void actionPerformed(ActionEvent e);
}

public class Quiz extends Jogo {

    private JFrame quadro;
    private JPanel painel;
    private int perguntaAtual = 0;
    private int pontuacao = 0;
    private String nomeUsuario; 

    String[] perguntas = {
            "Quais desses não está entre os pilares de POO?",
            "O que é um objeto em programação orientada a objetos?",
            "O que é polimorfismo?",
            "O que é a palavra-chave \"super\" usada em POO?",
            "Como o encapsulamento contribui para a segurança em POO?",
            "O que é composição em POO?",
            "O que é um método estático em POO?",
            "Na sobrecarga de método, o que é levado em consideração para determinar qual versão do método será chamada?",
            "O que é uma interface em POO?",
            "O que é um construtor em POO?"
    };

    String[][] respostas = {
            {"Herança", "Polimorfismo", "Abstração", "Interface"},
            {"Uma variável", "Um bloco de código", "Uma instância de uma classe", "Um operador"},
            {"Uma técnica para esconder a implementação de uma classe", "A capacidade de uma classe ter múltiplos métodos com o mesmo nome", "A capacidade de uma classe herdar de várias classes", "A capacidade de uma classe criar instâncias de outras classes"},
            {"Referenciar uma classe pai", "Denotar que um método é estático", "Iniciar uma variável estática", "Chamar um método na mesma classe"},
            {"Tornando os métodos privados", "Tornando os métodos públicos", "Tornando os atributos públicos", "Tornando os atributos privados e controlando o acesso através de métodos"},
            {"Uma forma de herança múltipla", "Uma técnica para criar objetos dentro de outros objetos", "Um método para ocultar a implementação de uma classe", "Um tipo de encapsulamento"},
            {"Um método que pode ser acessado sem criar uma instância da classe", "Um método que só pode ser acessado por subclasses", "Um método que só pode ser acessado por métodos da mesma classe", "Um método que é virtual e pode ser sobreposto"},
            {"A ordem de declaração dos métodos na classe", "A visibilidade dos métodos", "A quantidade e os tipos de parâmetros", "A quantidade de vezes que um método foi chamado anteriormente"},
            {"Uma classe concreta", "Um conjunto de métodos abstratos", "Um tipo de dado", "Um operador lógico"},
            {"Um método que remove objetos", "Um método que cria objetos", "Um método que realiza cálculos matemáticos", "Um método que altera o estado de um objeto"}
    };

    char[] itens = {'A', 'B', 'C', 'D'};

    char[] respostasCorretas = {'D', 'C', 'B', 'A', 'D', 'B', 'A', 'C', 'B', 'B'};

    public static void main(String[] args) {
        Quiz quiz = new Quiz();
        quiz.iniciarJogo();
        Jogador jogador = new Jogador(quiz);
        System.out.println(jogador.getNome());
        System.out.println("O quiz vale de 0 a " + jogador.getPontuacao());
    }

    @Override
    public int getPontuacao() {
        return pontuacao;
    }

    @Override
    public String getNome() {
        return nomeUsuario;
    }

    @Override
    protected void criarGUI() {
        quadro = new JFrame("");
        quadro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        quadro.setSize(400, 300);
        quadro.setResizable(false);

        painel = new JPanel(new GridBagLayout());
       
        JLabel tituloLabel = new JLabel("POO Adventure - QuizGame");
        tituloLabel.setFont(new Font("Roboto", Font.BOLD, 30));

        GridBagConstraints tituloConstraints = new GridBagConstraints();
        tituloConstraints.gridx = 0;
        tituloConstraints.gridy = 0;
        tituloConstraints.insets = new Insets(50, 0, 30, 0);
        painel.add(tituloLabel, tituloConstraints);

        JButton botaoJogar = new JButton("Jogar");

        GridBagConstraints botaoConstraints = new GridBagConstraints();
        botaoConstraints.gridx = 0;
        botaoConstraints.gridy = 1;
        botaoConstraints.insets = new Insets(10, 0, 10, 0);
        painel.add(botaoJogar, botaoConstraints);

        botaoConstraints.gridy = 2;

        quadro.add(painel, BorderLayout.CENTER);

        quadro.setVisible(true);

        botaoJogar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                perguntaAtual = 0;
                pontuacao = 0;
                nomeUsuario = JOptionPane.showInputDialog(quadro, "Digite seu nome:");
                if (nomeUsuario != null && !nomeUsuario.isEmpty()) {
                    atualizarConteudoPainel();
                } else {
                    JOptionPane.showMessageDialog(quadro, "Por favor, insira um nome válido.");
                }
            }
        });
    }

    protected void atualizarConteudoPainel() {
        painel.removeAll();

        JLabel novoTituloLabel = new JLabel("Questão " + (perguntaAtual + 1));
        novoTituloLabel.setFont(new Font("Arial", Font.BOLD, 20));

        GridBagConstraints novoTituloConstraints = new GridBagConstraints();
        novoTituloConstraints.gridx = 0;
        novoTituloConstraints.gridy = 0;
        novoTituloConstraints.insets = new Insets(20, 0, 10, 0);
        painel.add(novoTituloLabel, novoTituloConstraints);

        JLabel perguntaLabel = new JLabel(perguntas[perguntaAtual]);
        perguntaLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        GridBagConstraints perguntaConstraints = new GridBagConstraints();
        perguntaConstraints.gridx = 0;
        perguntaConstraints.gridy = 1;
        perguntaConstraints.insets = new Insets(10, 0, 20, 0);
        painel.add(perguntaLabel, perguntaConstraints);

        ButtonGroup grupo = new ButtonGroup();

        for (int i = 0; i < respostas[perguntaAtual].length; i++) {
            JButton novoBotao = new JButton(itens[i] + ". " + respostas[perguntaAtual][i]);
            novoBotao.addActionListener(this);

            GridBagConstraints novoBotaoConstraints = new GridBagConstraints();
            novoBotaoConstraints.gridx = 0;
            novoBotaoConstraints.gridy = i + 2;
            novoBotaoConstraints.insets = new Insets(10, 0, 10, 0);

            painel.add(novoBotao, novoBotaoConstraints);
            grupo.add(novoBotao);
        }

        if (perguntaAtual < perguntas.length - 1) {
            JButton proximaPerguntaButton = new JButton("Próxima Pergunta");
            proximaPerguntaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton botaoFonte = (JButton) e.getSource();
                    String respostaSelecionada = botaoFonte.getText();

                    char respostaCorreta = respostasCorretas[perguntaAtual];
                    char respostaSelecionadaChar = respostaSelecionada.charAt(0);

                    if (respostaSelecionadaChar == respostaCorreta) {
                        pontuacao += 10;
                    }

                    perguntaAtual++;
                    if (perguntaAtual < perguntas.length) {
                        atualizarConteudoPainel();
                    } else {
                        JOptionPane.showMessageDialog(quadro, "Quiz concluído! Pontuação: " + pontuacao);
                        exibirPlacarLideres();
                        quadro.dispose();
                    }
                }
            });

            GridBagConstraints proximaBotaoConstraints = new GridBagConstraints();
            proximaBotaoConstraints.gridx = 0;
            proximaBotaoConstraints.gridy = respostas[perguntaAtual].length + 2;
            proximaBotaoConstraints.anchor = GridBagConstraints.NORTHEAST;
            proximaBotaoConstraints.insets = new Insets(20, 0, 20, 10);
        }

        painel.revalidate();
        painel.repaint();
    }

    protected void exibirPlacarLideres() {
        salvarPontuacaoNoArquivo();
        JOptionPane.showMessageDialog(quadro, "Nome: " + nomeUsuario + "\nPontuação: " + pontuacao);
        System.out.println("Nome do Jogador: " + nomeUsuario);
        System.out.println("Pontuação do Jogador: " + pontuacao / 10 + "/10");
    }

    protected void salvarPontuacaoNoArquivo() {
        try (PrintWriter escritor = new PrintWriter(new FileWriter("C:\\Users\\uso\\eclipse-workspace\\QuizJava\\src\\pontuacao.txt", true))) {
            escritor.println("Nome: " + nomeUsuario + ", Pontuação: " + pontuacao);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botaoFonte = (JButton) e.getSource();
        String respostaSelecionada = botaoFonte.getText();

        char respostaCorreta = respostasCorretas[perguntaAtual];
        char respostaSelecionadaChar = respostaSelecionada.charAt(0);

        if (respostaSelecionadaChar == respostaCorreta) {
            pontuacao += 10;
        }

        perguntaAtual++;
        if (perguntaAtual < perguntas.length) {
            atualizarConteudoPainel();
        } else {
            JOptionPane.showMessageDialog(quadro, "Quiz concluído! Pontuação: " + pontuacao);
            exibirPlacarLideres();
            quadro.dispose();
        }
    }
}
