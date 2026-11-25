package br.edu.tedi;
import br.edu.tedi.model.Medicamento;
import br.edu.tedi.service.MedicamentoService;
import br.edu.tedi.service.LembreteService;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
public class MainTerminal {
    private static final Scanner entrada=new Scanner(System.in);
    private static final MedicamentoService servico=new MedicamentoService();
    private static final LembreteService lembretes=new LembreteService(servico);
    private static final DateTimeFormatter FORMATO=DateTimeFormatter.ofPattern("HH:mm");
    private static String texto(String pergunta){System.out.print(pergunta);return entrada.nextLine().trim();}
    private static int numero(String pergunta){return Integer.parseInt(texto(pergunta));}
    private static LocalTime horario(){try{return LocalTime.parse(texto("Horario (HH:mm): "),FORMATO);}catch(DateTimeParseException e){throw new IllegalArgumentException("Horario invalido. Use HH:mm (ex.: 08:30).");}}
    private static void exibir(List<Medicamento> lista){if(lista.isEmpty()) System.out.println("Nenhum medicamento encontrado.");else for(Medicamento m:lista) System.out.println(m);}
    private static void cadastrar(){System.out.println(servico.cadastrar(texto("Nome: "),texto("Dosagem: "),horario(),texto("Orientacoes: ")));}
    private static void editar(){int id=numero("ID para editar: ");if(servico.buscarPorId(id)==null){System.out.println("ID nao encontrado.");return;}
        boolean ok=servico.editar(id,texto("Novo nome: "),texto("Nova dosagem: "),horario(),texto("Novas orientacoes: "));
        System.out.println(ok?"Atualizado.":"ID nao encontrado.");}
    public static void main(String[] args){
        System.out.println("TEDI Med - Demonstracao do back-end (dados apenas em memoria)");
        while(true){System.out.println("\n1 Cadastrar | 2 Listar | 3 Buscar nome | 4 Editar | 5 Excluir | 6 Lembretes por horario | 0 Sair");
            try {String op=texto("Opcao: ");switch(op){
                case "1":cadastrar();break;
                case "2":exibir(servico.listar());break;
                case "3":exibir(servico.buscarPorNome(texto("Nome para buscar: ")));break;
                case "4":editar();break;
                case "5":System.out.println(servico.excluir(numero("ID para excluir: "))?"Excluido.":"ID nao encontrado.");break;
                case "6":exibir(lembretes.consultarPorHorario(horario()));break;
                case "0":System.out.println("Encerrado.");return;
                default:System.out.println("Opcao invalida.");
            }}catch(IllegalArgumentException e){System.out.println("Erro: "+e.getMessage());}
        }
    }
}
