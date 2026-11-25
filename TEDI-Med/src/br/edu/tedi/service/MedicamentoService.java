package br.edu.tedi.service;
import br.edu.tedi.model.Medicamento;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
public class MedicamentoService {
    private final List<Medicamento> medicamentos=new ArrayList<>();
    private int proximoId=1;
    public Medicamento cadastrar(String nome,String dosagem,LocalTime horario,String orientacoes){
        Medicamento m=new Medicamento(proximoId,nome,dosagem,horario,orientacoes);
        medicamentos.add(m); proximoId++; return m;
    }
    public List<Medicamento> listar(){return new ArrayList<>(medicamentos);}
    public Medicamento buscarPorId(int id){for(Medicamento m:medicamentos) if(m.getId()==id) return m; return null;}
    public List<Medicamento> buscarPorNome(String termo){
        if(termo==null || termo.trim().isEmpty()) throw new IllegalArgumentException("Informe o nome para busca.");
        List<Medicamento> encontrados=new ArrayList<>();
        for(Medicamento m:medicamentos) if(m.getNome().toLowerCase().contains(termo.trim().toLowerCase())) encontrados.add(m);
        return encontrados;
    }
    public boolean editar(int id,String nome,String dosagem,LocalTime horario,String orientacoes){
        Medicamento atual=buscarPorId(id); if(atual==null) return false;
        // Valida todos os campos antes de modificar o cadastro existente.
        Medicamento validado=new Medicamento(id,nome,dosagem,horario,orientacoes);
        atual.setNome(validado.getNome());atual.setDosagem(validado.getDosagem());
        atual.setHorario(validado.getHorario());atual.setOrientacoes(validado.getOrientacoes());return true;
    }
    public boolean excluir(int id){Medicamento m=buscarPorId(id);return m!=null && medicamentos.remove(m);}
}
