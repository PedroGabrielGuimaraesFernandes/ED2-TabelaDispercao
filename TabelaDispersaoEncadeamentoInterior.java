import java.util.Arrays;

public class TabelaDispersaoEncadeamentoInterior {
    private static final int CAPACIDADE = 7; // tamanho fixo (pequeno para testar)
    private String[] chaves;
    private int[] valores;
    private int[] prox;         // índice do próximo elemento na cadeia (-1 = nenhum)
    private boolean[] ocupado;  // indica se a posição está ocupada
    private int tamanho;

    public TabelaDispersaoEncadeamentoInterior() {
        chaves = new String[CAPACIDADE];
        valores = new int[CAPACIDADE];
        prox = new int[CAPACIDADE];
        ocupado = new boolean[CAPACIDADE];
        Arrays.fill(prox, -1);
        tamanho = 0;
    }

    // ---- FUNÇÃO DE DISPERSÃO (método da divisão) ----
    private int hash(String chave) {
        int h = Math.abs(chave.hashCode());
        return h % CAPACIDADE;
    }

    // ---- INSERIR ----
    public void inserir(String chave, int valor) {
        int pos = hash(chave);

        // se posição está livre, coloca direto
        if (!ocupado[pos]) {
            chaves[pos] = chave;
            valores[pos] = valor;
            ocupado[pos] = true;
            tamanho++;
            return;
        }

        // se já existe, atualiza
        int atual = pos;
        int anterior = -1;
        while (atual != -1) {
            if (chaves[atual].equals(chave)) {
                valores[atual] = valor;
                return;
            }
            anterior = atual;
            atual = prox[atual];
        }

        // procurar posição livre
        int livre = procurarLivre();
        if (livre == -1) {
            System.out.println("Tabela cheia! Não é possível inserir " + chave);
            return;
        }

        chaves[livre] = chave;
        valores[livre] = valor;
        ocupado[livre] = true;
        prox[livre] = -1;
        prox[anterior] = livre;
        tamanho++;
    }

    public Integer buscar(String chave) {
        int pos = hash(chave);
        int atual = pos;
        while (atual != -1) {
            if (ocupado[atual] && chaves[atual].equals(chave)) {
                return valores[atual];
            }
            atual = prox[atual];
        }
        return null;
    }

    public void remover(String chave) {
        int pos = hash(chave);
        int atual = pos;
        int anterior = -1;

        while (atual != -1) {
            if (ocupado[atual] && chaves[atual].equals(chave)) {
                // remove o elemento
                if (anterior == -1) {
                    // se está na posição principal
                    if (prox[atual] == -1) {
                        limpar(atual);
                    } else {
                        int proxIndice = prox[atual];
                        chaves[atual] = chaves[proxIndice];
                        valores[atual] = valores[proxIndice];
                        prox[atual] = prox[proxIndice];
                        limpar(proxIndice);
                    }
                } else {
                    prox[anterior] = prox[atual];
                    limpar(atual);
                }
                tamanho--;
                return;
            }
            anterior = atual;
            atual = prox[atual];
        }
    }

    private void limpar(int i) {
        chaves[i] = null;
        valores[i] = 0;
        ocupado[i] = false;
        prox[i] = -1;
    }

    private int procurarLivre() {
        for (int i = 0; i < CAPACIDADE; i++) {
            if (!ocupado[i]) return i;
        }
        return -1;
    }

    public void mostrarTabela() {
        System.out.println("Índice | Chave   | Valor | Próx");
        for (int i = 0; i < CAPACIDADE; i++) {
            System.out.printf("%6d | %-7s | %-5s | %d\n",
                    i,
                    ocupado[i] ? chaves[i] : "—",
                    ocupado[i] ? valores[i] : "—",
                    prox[i]);
        }
        System.out.println();
    }

    // ---- TESTE ----
    public static void main(String[] args) {
        TabelaDispersaoEncadeamentoInterior tabela = new TabelaDispersaoEncadeamentoInterior();

        tabela.inserir("joao", 10);
        tabela.inserir("maria", 20);
        tabela.inserir("ana", 30);
        tabela.inserir("carlos", 40);
        tabela.inserir("bruno", 50);

        tabela.mostrarTabela();

        System.out.println("Buscar maria -> " + tabela.buscar("maria"));
        System.out.println("Buscar joao -> " + tabela.buscar("joao"));

        System.out.println("\nRemovendo maria...");
        tabela.remover("maria");
        tabela.mostrarTabela();

        System.out.println("Inserindo lara (para testar encadeamento)");
        tabela.inserir("lara", 99);
        tabela.mostrarTabela();
    }
}
