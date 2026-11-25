package br.edu.tedi.view;

import br.edu.tedi.model.Medicamento;
import br.edu.tedi.service.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.*;
import java.util.List;

/** Interface gráfica; delega as regras de negócio aos serviços existentes. */
public class TelaPrincipal extends JFrame {
    private final MedicamentoService servico = new MedicamentoService();
    private final LembreteService lembretes = new LembreteService(servico);
    private final DefaultTableModel modelo = new DefaultTableModel(new String[]{"ID", "Medicamento", "Dosagem", "Horário", "Orientações"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabela = new JTable(modelo);
    private final JLabel status = new JLabel("Nenhum medicamento cadastrado.");
    private static final Color AZUL = new Color(25, 76, 108);
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);

    public TelaPrincipal() {
        super("TEDI Med — Meus medicamentos");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) {
                if (JOptionPane.showConfirmDialog(TelaPrincipal.this, "Os dados desta sessão serão perdidos. Deseja sair?", "Encerrar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) dispose();
            }
        });
        JPanel raiz = new JPanel(new BorderLayout(12,12));
        raiz.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        JPanel topo = new JPanel(new GridLayout(2,1)); topo.setBackground(AZUL);
        topo.setBorder(BorderFactory.createEmptyBorder(16,18,16,18));
        JLabel titulo = new JLabel("TEDI Med"); titulo.setFont(new Font("SansSerif",Font.BOLD,32)); titulo.setForeground(Color.WHITE);
        JLabel subtitulo = new JLabel("Organize seus medicamentos e horários"); subtitulo.setForeground(Color.WHITE); subtitulo.setFont(new Font("SansSerif",Font.PLAIN,19));
        topo.add(titulo); topo.add(subtitulo); raiz.add(topo,BorderLayout.NORTH);
        JPanel centro = new JPanel(new BorderLayout(10,10));
        JPanel botoes = new JPanel(new GridLayout(2,3,10,10));
        botao(botoes,"Cadastrar",()->formulario(null)); botao(botoes,"Listar todos",this::atualizar);
        botao(botoes,"Buscar por nome",this::buscar); botao(botoes,"Editar selecionado",()->{Medicamento m=selecionado(); if(m!=null) formulario(m);});
        botao(botoes,"Excluir selecionado",this::excluir); botao(botoes,"Consultar horário",this::consultar);
        centro.add(botoes,BorderLayout.NORTH);
        tabela.setRowHeight(36); tabela.setFont(new Font("SansSerif",Font.PLAIN,17)); tabela.getTableHeader().setFont(new Font("SansSerif",Font.BOLD,16));
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); tabela.setAutoCreateRowSorter(true);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(40); tabela.getColumnModel().getColumn(4).setPreferredWidth(240);
        centro.add(new JScrollPane(tabela),BorderLayout.CENTER); raiz.add(centro,BorderLayout.CENTER);
        JPanel rodape=new JPanel(new GridLayout(2,1,0,6)); status.setFont(new Font("SansSerif",Font.BOLD,16)); rodape.add(status);
        rodape.add(new JLabel("Dados temporários: serão apagados ao fechar. Consulta de horários sem alarmes.")); raiz.add(rodape,BorderLayout.SOUTH);
        setContentPane(raiz); setMinimumSize(new Dimension(780,480)); setSize(1000,620); setLocationRelativeTo(null);
    }
    private void botao(JPanel painel,String texto,Runnable acao) {
        JButton b=new JButton(texto); b.setFont(new Font("SansSerif",Font.BOLD,17)); b.setPreferredSize(new Dimension(220,52)); b.addActionListener(e->acao.run()); painel.add(b);
    }
    private void mostrar(List<Medicamento> lista,String contexto) {
        modelo.setRowCount(0); for(Medicamento m:lista) modelo.addRow(new Object[]{m.getId(),m.getNome(),m.getDosagem(),m.getHorario().format(FORMATO),m.getOrientacoes()});
        status.setText(contexto+": "+lista.size()+" medicamento(s).");
    }
    private void atualizar(){mostrar(servico.listar(),"Todos os cadastros");}
    private void aviso(String texto){JOptionPane.showMessageDialog(this,texto);}
    private Medicamento selecionado(){int linha=tabela.getSelectedRow(); if(linha<0){aviso("Selecione um medicamento na tabela.");return null;} return servico.buscarPorId((Integer)modelo.getValueAt(tabela.convertRowIndexToModel(linha),0));}
    public static LocalTime interpretarHorario(String texto){try{return LocalTime.parse(texto.trim(),FORMATO);}catch(DateTimeParseException e){throw new IllegalArgumentException("Informe um horário válido (HH:mm), por exemplo 08:30.");}}
    private void formulario(Medicamento m){
        JTextField nome=new JTextField(m==null?"":m.getNome(),25), dose=new JTextField(m==null?"":m.getDosagem()), hora=new JTextField(m==null?"08:00":m.getHorario().format(FORMATO)), orient=new JTextField(m==null?"":m.getOrientacoes());
        JPanel p=new JPanel(new GridLayout(0,1,0,5));
        String[] rotulos={"Nome do medicamento","Dosagem","Horário (HH:mm)","Orientações de uso"}; JTextField[] campos={nome,dose,hora,orient};
        for(int i=0;i<campos.length;i++){JLabel l=new JLabel(rotulos[i]);l.setLabelFor(campos[i]);p.add(l);p.add(campos[i]);}
        while(JOptionPane.showConfirmDialog(this,p,m==null?"Cadastrar medicamento":"Editar medicamento",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE)==JOptionPane.OK_OPTION){
            try{LocalTime h=interpretarHorario(hora.getText()); if(m==null) servico.cadastrar(nome.getText(),dose.getText(),h,orient.getText()); else servico.editar(m.getId(),nome.getText(),dose.getText(),h,orient.getText()); atualizar(); aviso("Medicamento salvo com sucesso."); return;}catch(IllegalArgumentException e){aviso(e.getMessage());}
        }
    }
    private void buscar(){String termo=JOptionPane.showInputDialog(this,"Digite parte do nome:");if(termo!=null)try{mostrar(servico.buscarPorNome(termo),"Resultado da busca");}catch(IllegalArgumentException e){aviso(e.getMessage());}}
    private void consultar(){String hora=JOptionPane.showInputDialog(this,"Informe o horário (HH:mm):");if(hora!=null)try{mostrar(lembretes.consultarPorHorario(interpretarHorario(hora)),"Lembretes às "+hora.trim());}catch(IllegalArgumentException e){aviso(e.getMessage());}}
    private void excluir(){Medicamento m=selecionado();if(m!=null && JOptionPane.showConfirmDialog(this,"Excluir "+m.getNome()+"?","Confirmar exclusão",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){servico.excluir(m.getId());atualizar();aviso("Medicamento excluído.");}}
}
