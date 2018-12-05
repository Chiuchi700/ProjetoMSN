package msn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

public class WhatsApp {

    protected static String nome = "";
    protected static String numero = "";
    private static String statusAt = "Disponível";
    private static String recado = "#GrupoHashCode";
    private static ArrayList<Conversa> lista = null;

    public static String getNome() {
        return nome;
     }

    public static void setNumero(String numero) {
        WhatsApp.numero = numero;
    }

    public static String getNumero() {
        return numero;
    }

    public static ArrayList<Conversa> getLista() {
        return lista;
    }

    public static void setStatusAt(String statusAt) {
        WhatsApp.statusAt = statusAt;
    }

    public static void setNome(String nome) {
        WhatsApp.nome = nome;
    }

    public static String getRecado() {
        return recado;
    }

    public static String getStatusAt() {
        return statusAt;
    }
    
    

    public static void setRecado(String recado) {
        WhatsApp.recado = recado;
    }

    //removendo a conversa da ArrayList
    public static void removerConversa(String receptor) {
        for (Conversa conversa : lista) {
            if (conversa.getContatoReceptor().equals(receptor)) {
                lista.remove(conversa);
            }
        }

    }
    
    //Pegando o visualisado por ultimo da conversa
    public static String getVisualisado(Conversa atual){
        return lista.get(lista.indexOf(atual)).getUltimaVisu();
    }

    //Verificando se a conversa existe
    public static boolean conversaExiste(String receptor) {
        for (Conversa conversa : lista) {
            
            if (conversa.getContatoReceptor().equals(receptor)) {
                return false;
            }
            
        }
        return true;
    }

    //Iniciando uma conversa
    public static boolean inicarConversa(String receptor, String nome) {

        if (conversaExiste(receptor)) {
            lista.add(new Conversa(receptor, nome));
            return true;
        } else {
            return false;
        }

    }

    //Manda uma mensagem
    public static void mandarMensagem(String contato, String texto, boolean emissor) {
     
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getContatoReceptor().equals(contato)) {
                lista.get(i).AddMensagem(texto, contato, emissor);
            }
        }

    }

    //Procura as mensagens e retorna uma string
    public static String receberMensagem(String contato, String texto) {
            
        StringBuilder mensagens = new StringBuilder();
        for (Conversa conversa : lista) {
        
            if (contato.equals(conversa.getContatoReceptor())){
               
                for (Mensagem textoAt : conversa.getTextos()) {
                    mensagens.append(textoAt);
                }
                
            }
         
        }
        return mensagens.toString();
    }

    //retorna a conversa selecionada
    public static Conversa conversaAt(String receptor) {

        for (Conversa conversa : lista) {
            if (conversa.getContatoReceptor().equals(receptor)) {
                
                //Coloca a conversa em primeiro na Arraylist
                lista.remove(conversa);
                lista.add(0, conversa);
                return conversa;
            }
        }
        return null;
    }
    
    //Retorna o numero de acordo com o nome recebido
    public static String getContato (String nome){
        for (Conversa conversa : lista) {
            if (conversa.getNomeContato().equals(nome))
                return conversa.getContatoReceptor();
        }
        return null;
    }
    
    //Lista todos os contatos
    public static DefaultListModel listaContatos(){
        DefaultListModel atual = new DefaultListModel();
        
        for (Conversa conversa : lista) {
            atual.addElement(conversa.getNomeContato());
        }
        
        return atual;
    }
    
    //Pesquisa em todas as conversas a palavra chave
    public static String pesquisa(String pesquisar){
        StringBuilder aux = new StringBuilder("");
        
        for (Conversa conversa : lista) {
            for (Mensagem mensagem : conversa.pequisa(pesquisar)) {
                aux.append("Conversa com ").append(conversa.getNomeContato()).append("\n").append(mensagem);
            }
        }
        
        return aux.toString();
    }
    

//Salvando conversas
    public static void salvarConversas(){
        
        //Salvando as conversas
        File conversas = new File("conversas.dat");
        
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(conversas));){
        out.writeObject(lista);
        }catch (IOException e){
        }
       
        //Salvando dados do perfil
        String dados_user[] = {getNome(), getNumero(), getRecado(), getStatusAt()};
        
        File user = new File("user.txt");
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(user));){
        out.writeObject(dados_user);
        }catch (IOException e){
        }
        
    }
    
    //Carregando os dados
    public static void carregarConversas(){
        
        //Carregando as conversas
        File conversas = new File("conversas.dat");
        
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(conversas));){
           lista = (ArrayList) input.readObject();
        }catch (IOException | ClassNotFoundException e){
            lista = new ArrayList<>();
        }
        
        //Carregando os dados do perfil
        File user = new File("user.txt");
        
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(user));){
             
       String[] dados_user = (String[]) input.readObject();
            setNome(dados_user[0]);
            setNumero(dados_user[1]);
            setRecado(dados_user[2]);
            setStatusAt(dados_user[3]);
       
        }catch (IOException | ClassNotFoundException e){
        }
    }
    
    //Remove as mensagens de todas as conversas
    public static void removerMensagens(boolean all, String contato){
        //Se o all for true ele vai apagar a de todas, caso cotrario ele verifica pelo contato recebido
        for (int i = 0; i < lista.size(); i++) {
            if (all == true || contato.equals(lista.get(i).contatoReceptor)){
            lista.get(i).limparMensagens();
            }
        }
    }
    
    //Remove todos os contatos
    public static void removerContatos(){
        lista = new ArrayList<>();
    }
    
    //Apagar conta
    public static void apagarUser(){
        
        removerContatos();
        nome = "";
        numero = "";
        statusAt = "Disponível";
        recado = "#GrupoHashCode";
        
    }
    
    
}
