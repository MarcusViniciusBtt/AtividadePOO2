package swing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Conexao {
	private Connection connection=null;
	private Statement statement=null;
	private ResultSetMetaData resultset=null;
	public Connection conectar()
	{ String retorno=null;
	String servidor="jdbc:mysql://127.0.0.1:3306/db_pedido";
	String usuario="root";
	String senha="@96622542Kate";
	String driver="com.mysql.cj.jdbc.Driver";
	try
	{
		Class.forName(driver) ;
		this.connection=DriverManager.getConnection(servidor,usuario,senha);
		this.statement=this.connection.createStatement();
		retorno="OK";
	}
	catch(Exception e)
	{ connection=null; }
	return connection;

	}
}


	