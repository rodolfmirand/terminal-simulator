package TerminalSimulator.repository;

import TerminalSimulator.models.Directory;
import TerminalSimulator.models.File;

import java.util.ArrayList;
import java.util.List;

public class Database {

    private Directory root;

    private String currentUser;


    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public Database() {
        this.root = new Directory("root", null);
    }

    public Directory getRoot() {
        return root;
    }

    public void createDirectory(ArrayList<String> path, String name) {
        Directory parent = findDirectory(root, path);
        if (parent != null) {
            parent.addDirectory(new Directory(name, parent));
        } else {
            System.out.println("Directory not found!");
        }
    }

    public Directory findDirectory(ArrayList<String> path) {
        return findDirectory(root, path);
    }

    private Directory findDirectory(Directory current, ArrayList<String> path) {
        if (path.size() == 1) {
            return current;
        }
        for (Directory child : current.getDirectories()) {
            if (child.getName().equals(path.get(1))) {
                path.removeFirst();
                return findDirectory(child, path);
            }
        }
        return null;
    }

    // Função que retorna a representação da árvore de diretórios como uma string
    public String tree() {
        StringBuilder builder = new StringBuilder();

        // inicia a construção da árvore a partir do diretório raíz
        buildTreeString(root, "", true, builder);
        return builder.toString();
    }

    /**
    *  Função recursiva que constrói a árvore
     * @param directory Diretório atual que está sendo processado.
     * @param prefix Prefixo usado para formatar a estrutura hierárquica.
     * @param isLast Indica se este é o último elementro dentro do nível atual.
     * @param builder StringBuilder que armazena a representação da árvore.
     */
    private void buildTreeString(Directory directory, String prefix, boolean isLast, StringBuilder builder) {
        // Adiciona o nome do diretório com o prefixo adequado
        builder.append(prefix).append(isLast ? "└── " : "├── ").append(directory.getName()).append("\n");

        // Atualiza o prefixo para os próximos elementos dentro deste diretório
        String newPrefix = prefix + (isLast ? "    " : "│   ");

        // Obtém as listas de subdiretórios e arquivos
        List<Directory> subDirs = directory.getDirectories();
        List<File> files = directory.getFiles();

        // Calcula o total de elementos (subdiretórios + arquivos) dentro do diretório atual
        int totalItems = subDirs.size() + files.size();

        // Itera sobre os subdiretórios e chama recursivamente a função para processá-los
        for (int i = 0; i < subDirs.size(); i++) {
            // Determina se este é o último item a ser processado no nível atual
            boolean lastItem = (i == totalItems - 1 && files.isEmpty());
            buildTreeString(subDirs.get(i), newPrefix, lastItem, builder);
        }

        // Itera sobre os arquivos e os adiciona à estrutura da árvore
        for (int i = 0; i < files.size(); i++) {
            boolean lastFile = (i == files.size() - 1);
            builder.append(newPrefix)
                    .append(lastFile ? "└── " : "├── ") // Define o símbolo correto dependendo da posição do arquivo
                    .append(files.get(i).getName())
                    .append("\n");
        }
    }
}
