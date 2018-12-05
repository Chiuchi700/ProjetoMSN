package msn;


public class Mensagem extends Conversa{
    
    private final String emissor;
    private final String texto;
    private final String nome;
    private String status;
    private final String Hrrecebida;
    
    public Mensagem(String emissor, String texto, String recebidaHr, String nome) {
        this.emissor = emissor;
        this.nome = nome;
        this.texto = texto;
        this.Hrrecebida = recebidaHr;
        this.status = "Está sendo enviada";
    }

    public String getEmissor() {
        return emissor;
    }

    public String getTexto() {
        return texto;
    }

    public String getStatus() {
        return status;
    }

    public String getHrrecebida() {
        return Hrrecebida;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return  nome + " (" + Hrrecebida + "):\n"+ texto + "\n Status: " + status +"\n\n" ;
    }
    
    
    
}
