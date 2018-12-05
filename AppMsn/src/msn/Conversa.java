package msn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Conversa extends WhatsApp implements Serializable {

    private ArrayList<Mensagem> textos = new ArrayList();
    private String nomecontato;
    protected String contatoReceptor;
    private String ultimaVisu;

    public Conversa(String contatoReceptor, String NomeContato) {
        this.nomecontato = NomeContato;
        this.contatoReceptor = contatoReceptor;
        ultimaVisu = getDataAt();
    }

    public Conversa() {
    }

    //Altera o status da mensagem
    public void AlterarStatusMensagens() {

        //A cada vez q envia uma mensagem nova ele altera
        for (int i = 0; i < textos.size(); i++) {
            switch (textos.get(i).getStatus()) {
                case "Está sendo enviada":
                    textos.get(i).setStatus("Foi enviada");
                    break;
                case "Foi enviada":
                    textos.get(i).setStatus("Foi recebida");
                    break;
            }
        }

    }

    public String getContatoReceptor() {
        return contatoReceptor;
    }

    //Adiciona uma nova mensagem na ArrayList
    public void AddMensagem(String mensagem, String contatoEmissor, boolean emissor) {
        //Se o emissor estiver ativado quem vai receber a mensagem é o user principal
        if (emissor==true){
            contatoEmissor = WhatsApp.getNumero();
        }
        textos.add(new Mensagem(contatoEmissor, mensagem, getDataAt(), emissor == true ? WhatsApp.nome : nomecontato));
        AlterarStatusMensagens();
        
        if (emissor == false) {
            ultimaVisu();
        }
    }

    //Pesquisar e armazenar os resultados da pesquisa
    public ArrayList<Mensagem> pequisa(String pesquisar) {
        ArrayList<Mensagem> encontradas = new ArrayList<>();

        for (Mensagem texto : textos) {
            if (texto.getTexto().toUpperCase().contains(pesquisar.toUpperCase())) {
                encontradas.add(texto);
            }
        }
        return encontradas;
    }

    //Retorna a data atual
    public String getDataAt() {
        return (new Date()).toLocaleString();
    }

    //Atualiza a data atual, e deixa todas as mensagens como lidas
    public void ultimaVisu() {
        this.ultimaVisu = getDataAt();

        for (int i = 0; i < textos.size(); i++) {
            textos.get(i).setStatus("Foi lida");
        }
        
    }

    //Limpa todas as mensagens
    public void limparMensagens() {
        textos = new ArrayList<>();
    }

    public String getUltimaVisu() {
        return ultimaVisu;
    }

    public String getNomeContato() {
        return nomecontato;
    }

    public ArrayList<Mensagem> getTextos() {
        return textos;
    }

}
