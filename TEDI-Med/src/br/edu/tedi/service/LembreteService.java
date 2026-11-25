package br.edu.tedi.service;
import br.edu.tedi.model.Medicamento;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
public class LembreteService {
    private final MedicamentoService medicamentos;
    public LembreteService(MedicamentoService medicamentos){this.medicamentos=medicamentos;}
    public List<Medicamento> consultarPorHorario(LocalTime horario){
        if(horario==null) throw new IllegalArgumentException("Horario obrigatorio.");
        List<Medicamento> resultado=new ArrayList<>();
        for(Medicamento m:medicamentos.listar()) if(m.getHorario().equals(horario)) resultado.add(m);
        return resultado;
    }
}
