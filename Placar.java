import java.util.ArrayList;
import java.util.List;

public class Placar {
    private List<PontuacaoJogador> pontuacoes;

    public Placar() {
        pontuacoes = new ArrayList<>();
    }

    public void adicionarPontuacao(String nomeJogador, int pontos) {
        PontuacaoJogador pontuacaoExistente = encontrarPontuacaoJogador(nomeJogador);

        if (pontuacaoExistente != null) {
            pontuacaoExistente.setPontos(pontuacaoExistente.getPontos() + pontos);
        } else {
            PontuacaoJogador novaPontuacao = new PontuacaoJogador(nomeJogador, pontos);
            pontuacoes.add(novaPontuacao);
        }
    }

    public List<PontuacaoJogador> getPontuacoes() {
        return new ArrayList<>(pontuacoes);
    }

    private PontuacaoJogador encontrarPontuacaoJogador(String nomeJogador) {
        for (PontuacaoJogador pontuacao : pontuacoes) {
            if (pontuacao.getNomeJogador().equals(nomeJogador)) {
                return pontuacao;
            }
        }
        return null;
    }

    public static class PontuacaoJogador {
        private String nomeJogador;
        private int pontos;

        public PontuacaoJogador(String nomeJogador, int pontos) {
            this.nomeJogador = nomeJogador;
            this.pontos = pontos;
        }

        public String getNomeJogador() {
            return nomeJogador;
        }

        public int getPontos() {
            return pontos;
        }

        public void setPontos(int pontos) {
            this.pontos = pontos;
        }

        @Override
        public String toString() {
            return nomeJogador + ": " + pontos;
        }
    }
}
