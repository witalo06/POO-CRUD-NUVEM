package crud;

	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.util.Scanner;

	// Aluno: Misael Witalo 
	// RA: 0025369
	// CRUD de produtos com banco de dados 

	public class Produto {

	    public static void main(String[] args) {

	        // Conexao local com H2 Database (cria o arquivo dbproduto.mv.db na pasta do projeto)
	        String url = "jdbc:h2:./dbproduto";
	        String usuariobanco = "sa";
	        String bancosenha = "7657";

	        Scanner sc = new Scanner(System.in);

	        try {
	            Connection conexao = DriverManager.getConnection(url, usuariobanco, bancosenha);
	            System.out.println("Conectado ao banco local H2 com sucesso!");

	            // Cria a tabela automaticamente, caso ainda nao exista
	            String sqlTabela = "CREATE TABLE IF NOT EXISTS PRODUTO (" +
	                               "CODIGO INT PRIMARY KEY, " +
	                               "NOME VARCHAR(100), " +
	                               "PRECO FLOAT)";
	            conexao.createStatement().executeUpdate(sqlTabela);

	            int opcao = -1;
	            while (opcao != 0) {
	                System.out.println("\n1 - Inserir | 2 - Listar | 3 - Atualizar | 4 - Deletar | 0 - Sair");
	                System.out.print("Escolha uma opção: ");
	                opcao = sc.nextInt();

	                switch (opcao) {
	                    case 1:
	                        System.out.print("Código do produto: ");
	                        int codigo = sc.nextInt();
	                        sc.nextLine(); // Limpa o buffer do teclado para aceitar nomes com espaço

	                        System.out.print("Nome do Produto: ");
	                        String nome = sc.nextLine();

	                        System.out.print("Preço do produto: ");
	                        float preco = sc.nextFloat();

	                        String sql = "INSERT INTO PRODUTO(CODIGO, NOME, PRECO) VALUES(?, ?, ?)";
	                        PreparedStatement stmt = conexao.prepareStatement(sql);
	                        stmt.setInt(1, codigo);
	                        stmt.setString(2, nome);
	                        stmt.setFloat(3, preco);
	                        stmt.executeUpdate();
	                        System.out.println("Registro inserido com sucesso!");
	                        break;

	                    case 2:
	                        System.out.print("Digite o código do produto: ");
	                        int codigo2 = sc.nextInt();

	                        String sql2 = "SELECT * FROM PRODUTO WHERE CODIGO = ?";
	                        PreparedStatement stmt2 = conexao.prepareStatement(sql2);
	                        stmt2.setInt(1, codigo2);
	                        ResultSet rs = stmt2.executeQuery();

	                        if (rs.next()) {
	                            System.out.println("Nome Produto: " + rs.getString("nome") + " | Preço: R$ " + rs.getFloat("preco"));
	                        } else {
	                            System.out.println("Produto não localizado.");
	                        }
	                        break;

	                    case 3:
	                        System.out.print("Digite o código do produto para atualizar: ");
	                        int codPesquisa = sc.nextInt();

	                        System.out.print("Digite o novo preço do produto: ");
	                        float novopreco = sc.nextFloat();

	                        String sqlUpd = "UPDATE PRODUTO SET PRECO = ? WHERE CODIGO = ?";
	                        PreparedStatement stmtUp = conexao.prepareStatement(sqlUpd);
	                        stmtUp.setFloat(1, novopreco);
	                        stmtUp.setInt(2, codPesquisa);
	                        int linhaAfetada = stmtUp.executeUpdate();

	                        if (linhaAfetada > 0) {
	                            System.out.println("Preço atualizado com sucesso!");
	                        } else {
	                            System.out.println("Produto não localizado.");
	                        }
	                        break;

	                    case 4:
	                        System.out.print("Digite o código do produto para deletar: ");
	                        int coddelete = sc.nextInt();

	                        String sqlDel = "DELETE FROM PRODUTO WHERE CODIGO = ?";
	                        PreparedStatement stmtDele = conexao.prepareStatement(sqlDel);
	                        stmtDele.setInt(1, coddelete);
	                        int linhaAfetada2 = stmtDele.executeUpdate();

	                        if (linhaAfetada2 > 0) {
	                            System.out.println("Deletado com sucesso!");
	                        } else {
	                            System.out.println("Produto não localizado.");
	                        }
	                        break;

	                    case 0:
	                        System.out.println("Saindo...");
	                        break;

	                    default:
	                        System.out.println("Opção inválida.");
	                }
	            }

	            conexao.close();
	            sc.close();

	        } catch (Exception e) {
	            System.out.println("Erro: " + e.getMessage());
	        }
	    }
	}


