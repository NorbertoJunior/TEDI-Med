package br.edu.tedi.model;
import java.time.LocalTime;
public class Medicamento {
    private final int id;
    private String nome, dosagem, orientacoes;
    private LocalTime horario;
    public Medicamento(int id, String nome, String dosagem, LocalTime horario, String orientacoes) {
        this.id=id; setNome(nome); setDosagem(dosagem); setHorario(horario); setOrientacoes(orientacoes);
    }
    private static String obrigatorio(String texto) {
        if(texto==null || texto.trim().isEmpty()) throw new IllegalArgumentException("Campo obrigatorio nao pode estar vazio.");
        return texto.trim();
    }
    public int getId(){return id;}
    public String getNome(){return nome;}
    public String getDosagem(){return dosagem;}
    public LocalTime getHorario(){return horario;}
    public String getOrientacoes(){return orientacoes;}
    public void setNome(String nome){this.nome=obrigatorio(nome);}
    public void setDosagem(String dosagem){this.dosagem=obrigatorio(dosagem);}
    public void setHorario(LocalTime horario){if(horario==null) throw new IllegalArgumentException("Horario obrigatorio."); this.horario=horario;}
    public void setOrientacoes(String orientacoes){this.orientacoes=obrigatorio(orientacoes);}
    @Override public String toString(){return "ID: "+id+" | "+nome+" | Dosagem: "+dosagem+" | Horario: "+horario+" | Orientacoes: "+orientacoes;}
}
