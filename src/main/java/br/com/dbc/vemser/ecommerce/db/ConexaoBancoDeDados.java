package br.com.dbc.vemser.ecommerce.db;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class ConexaoBancoDeDados {
    @Value("${db.server}")
    private static String SERVER;

    @Value("${db.port}")
    private static String PORT;

    @Value("${db.database}")
    private static String DATABASE;

    @Value("${db.username}")
    private static String USER;

    @Value("${db.password}")
    private static String PASS;
    private static final String SCHEMA = "EQUIPE_1";

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@" + SERVER + ":" + PORT + ":" + DATABASE;
        // jdbc:oracle:thin:@localhost:1521:xe

        // Abre-se a conexão com o Banco de Dados
        Connection con = DriverManager.getConnection(url, USER, PASS);

        // sempre usar o schema vem_ser
        con.createStatement().execute("alter session set current_schema=" + SCHEMA);

        return con;
    }
    public static void main(String[] args) {
        try {
            // Obtém a conexão do método getConnection()
            Connection con = getConnection();

            // Se chegarmos até aqui, a conexão foi estabelecida com sucesso
            System.out.println("Conexão bem-sucedida!");

            // Não se esqueça de fechar a conexão quando terminar de usá-la
            con.close();
        } catch (SQLException e) {
            // Trata a exceção, caso ocorra algum problema na conexão
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}
