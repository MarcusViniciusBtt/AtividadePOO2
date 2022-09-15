package swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Swinguera extends JFrame {

	private JPanel contentPane;
	private JTextField id_valor;
	private JTextField dtc_valor;
	private JTextField deescricao_valor;
	private JTable tabProduto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Swinguera frame = new Swinguera();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Swinguera() {
		getContentPane().setLayout(null);

		JButton TesteConexao = new JButton("Teste de Conex\u00E3o");
		TesteConexao.setBounds(125, 71, 158, 102);
		getContentPane().add(TesteConexao);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 467, 284);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel ID = new JLabel("ID");
		ID.setBounds(25, 28, 27, 21);
		contentPane.add(ID);

		JLabel data_cadastro = new JLabel("Data Cadastro");
		data_cadastro.setBounds(116, 28, 88, 21);
		contentPane.add(data_cadastro);

		id_valor = new JTextField();
		id_valor.setEditable(false);
		id_valor.setFont(new Font("Tahoma", Font.BOLD, 12));
		id_valor.setBounds(46, 27, 58, 20);
		contentPane.add(id_valor);
		id_valor.setColumns(10);

		dtc_valor = new JTextField();
		dtc_valor.setEditable(false);
		dtc_valor.setFont(new Font("Tahoma", Font.BOLD, 12));
		dtc_valor.setColumns(10);
		dtc_valor.setBounds(198, 27, 103, 20);
		contentPane.add(dtc_valor);

		JLabel deescricao = new JLabel("Deescri\u00E7\u00E3o");
		deescricao.setBounds(10, 77, 65, 21);
		contentPane.add(deescricao);

		deescricao_valor = new JTextField();
		deescricao_valor.setEditable(false);
		deescricao_valor.setFont(new Font("Tahoma", Font.BOLD, 12));
		deescricao_valor.setColumns(10);
		deescricao_valor.setBounds(74, 77, 103, 20);
		contentPane.add(deescricao_valor);

		JButton NOVO = new JButton("NOVO");
		NOVO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deescricao_valor.setEditable(true);
				deescricao_valor.setText(null);
				id_valor.setText(null);
				dtc_valor.setText(null);
				deescricao_valor.requestFocus();

			}
		});
		NOVO.setBounds(187, 76, 65, 23);
		contentPane.add(NOVO);

		JButton GRAVAR = new JButton("GRAVAR");
		GRAVAR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					gravar();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		GRAVAR.setBounds(262, 76, 76, 23);
		contentPane.add(GRAVAR);

		JButton EXCLUIR = new JButton("EXCLUIR");
		EXCLUIR.setBounds(347, 76, 81, 23);
		contentPane.add(EXCLUIR);

		JButton Teste = new JButton("TesteConex\u00E3o");
		Teste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Conexao con=new Conexao();
				Connection retorno= con.conectar();
				if(retorno!=null)
					JOptionPane.showMessageDialog(null,retorno);
			}
		});
		Teste.setBounds(422, 226, 119, 65);
		contentPane.add(Teste);

		tabProduto = new JTable();
		tabProduto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				linhaSelecionada();
			}
		});
		tabProduto.setCellSelectionEnabled(true);
		tabProduto.setBounds(37, 117, 391, 98);
		contentPane.add(tabProduto);


	}
	private void listarProduto() throws SQLException 
	{ Connection con=null;
	Conexao objconexao=new Conexao();
	con=objconexao.conectar();
	if(con ==null)
	{ JOptionPane.showMessageDialog(null,"conexão não realizada");
	}
	else
	{ Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery("SELECT * FROM db_pedido.produto");
	String[] colunasTabela = new String[]{ "ID", "Descrição", "Pontuação" }; 
	DefaultTableModel modeloTabela = new DefaultTableModel(null,colunasTabela);
	modeloTabela.addRow(new String[] {"ID", "DESCRIÇÃO", "CADASTRO"}); 
	if(rs != null) {
		while(rs.next()) {
			modeloTabela.addRow(new String[] { 
					String.valueOf(rs.getInt("ID")), 
					rs.getString("descricao"), 
					rs.getString("data_cadastro")
			}); 
		}
	}
	tabProduto.setModel(modeloTabela);
	}
	}

private void linhaSelecionada()
{
	desabilitarText();
	DefaultTableModel tableModel = (DefaultTableModel) tabProduto.getModel();
	int row = tabProduto.getSelectedRow();
	if (tableModel.getValueAt(row, 0).toString()!="ID")
	{ id_valor.setText(tableModel.getValueAt(row, 0).toString());
	deescricao_valor.setText(tableModel.getValueAt(row, 1).toString());
	dtc_valor.setText(tableModel.getValueAt(row, 2).toString());
	}
}



private void desabilitarText()
{
	deescricao_valor.setEditable(false);
	id_valor.setEditable(false);
	dtc_valor.setEditable(false);
}


private void gravar() throws SQLException
{
	Connection con=null;
	Swinguera objconexao=new Swinguera();
	try
	{con=objconexao.Conexao();
	if(con ==null)
	{ JOptionPane.showMessageDialog(null,"conexão não realizada");
	}
	else
	{ Statement stmt = con.createStatement();
	String query="insert into loja.produto(descricao) values('"+deescricao_valor.getText()+"')";
	stmt.executeUpdate(query);
	listarProduto();
	deescricao_valor.setText(null);
	desabilitarText();
	}
	}
	catch(Exception ex)
	{con.close();
	JOptionPane.showMessageDialog(null,"Não foi possível gravar. "+ex.getMessage());
	}
}

private Connection Conexao() {
	// TODO Auto-generated method stub
	return null;
} 

private void showData() throws SQLException
{
	Connection con=null;
	Swinguera objconexao=new Swinguera();
	try
	{con=objconexao.Conexao();
	if(con ==null)
	{ JOptionPane.showMessageDialog(null,"conexão não realizada");
	}
	else
	{ Statement stmt = con.createStatement();
	String query="select * from produto";
	stmt.executeUpdate(query);
	listarProduto();
	deescricao_valor.setText(null);
	desabilitarText();
	}
	}
	catch(Exception ex)
	{con.close();
	JOptionPane.showMessageDialog(null,"Não foi possível gravar. "+ex.getMessage());
	}
}
}

