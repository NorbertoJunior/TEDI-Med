package br.edu.tedi;
import br.edu.tedi.service.*;
import java.time.LocalTime;
public class TesteTEDIMed {
    private static void verificar(boolean condicao,String descricao){if(!condicao)throw new AssertionError(descricao);System.out.println("OK: "+descricao);}
    public static void main(String[] args){
        MedicamentoService s=new MedicamentoService();LembreteService l=new LembreteService(s);
        int id=s.cadastrar("Medicamento exemplo","1 comprimido",LocalTime.of(8,0),"Apos cafe").getId();
        verificar(s.listar().size()==1,"cadastro e listagem");
        verificar(s.buscarPorNome("exemplo").size()==1,"busca por nome");
        verificar(l.consultarPorHorario(LocalTime.of(8,0)).size()==1,"consulta de lembretes");
        verificar(s.editar(id,"Medicamento atualizado","2 comprimidos",LocalTime.of(9,0),"Conforme orientacao"),"edicao");
        verificar(l.consultarPorHorario(LocalTime.of(8,0)).isEmpty(),"horario antigo removido");
        verificar(s.excluir(id) && s.listar().isEmpty(),"exclusao");
        System.out.println("Todos os testes passaram.");
    }
}
