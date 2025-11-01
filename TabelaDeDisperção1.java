import java.util.LinkedList;

class HashTable<K, V> {
    private class Entry<K, V> {
        K key;
        V value;
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        @Override
        public String toString() {
            return key + " => " + value;
        }
    }

    private LinkedList<Entry<K, V>>[] table;
    private int size; // número de elementos

    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
    }

    // Função de hash (método da divisão)
    private int hash(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    // Inserir ou atualizar um elemento
    public void put(K key, V value) {
        int index = hash(key);
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                entry.value = value; // atualiza se a chave já existe
                return;
            }
        }
        table[index].add(new Entry<>(key, value));
        size++;
    }

    // Buscar um valor pela chave
    public V get(K key) {
        int index = hash(key);
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null; // não encontrado
    }

    // Remover um elemento
    public boolean remove(K key) {
        int index = hash(key);
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                table[index].remove(entry);
                size--;
                return true;
            }
        }
        return false;
    }

    // Mostrar tabela
    public void printTable() {
        for (int i = 0; i < table.length; i++) {
            System.out.print(i + ": ");
            for (Entry<K, V> entry : table[i]) {
                System.out.print(entry + " -> ");
            }
            System.out.println("null");
        }
    }

    public int size() {
        return size;
    }
}

public class Main {
    public static void main(String[] args) {
        HashTable<Integer, String> hash = new HashTable<>(7); // tamanho primo

        hash.put(13, "Alice");
        hash.put(27, "Bob");
        hash.put(35, "Carol");
        hash.put(42, "David");
        hash.put(58, "Eve");

        System.out.println("Tabela Hash:");
        hash.printTable();

        System.out.println("\nBuscar chave 58: " + hash.get(58));
        System.out.println("Remover chave 13: " + hash.remove(13));

        System.out.println("\nTabela após remoção:");
        hash.printTable();
    }
}
