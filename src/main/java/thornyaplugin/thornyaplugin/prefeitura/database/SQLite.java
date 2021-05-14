package thornyaplugin.thornyaplugin.prefeitura.database;

import thornyaplugin.thornyaplugin.ThornyaPlugin;

import java.sql.*;

public class SQLite {

    private final ThornyaPlugin pl;
    String urlLeis = null;
    String urlAvisos = null;
    String urlVotos = null;
    public SQLite(ThornyaPlugin main){
        this.pl = main;
        urlAvisos = "jdbc:sqlite:" + pl.getDataFolder() + "/infratores.db";
        urlLeis = "jdbc:sqlite:" + pl.getDataFolder() + "/leis.db";
        urlVotos = "jdbc:sqlite:" + pl.getDataFolder() + "/votos.db";
        criarDbLeis();
        criarDbVotacao();
        updateLeis();
    }
    private Connection connectVotos() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(urlVotos);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    private Connection connectInfratores() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(urlAvisos);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    private Connection connectLeis() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(urlLeis);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    ////////////////////////////////////////////////////////////////////////////////////////
    public void criarDbVotacao() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS votos (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	nick string NOT NULL,\n"
                + "	vote string NOT NULL,\n"
                + "	voted integer NOT NULL\n"
                + ");";

        try (Connection conn =  connectVotos();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connectVotos().close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
    public void createVoted(String nick, String vote) {
        String sql = "INSERT INTO votos (nick, vote, voted) VALUES(?, ?, 1)";
        try (Connection conn = this.connectVotos();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nick);
            pstmt.setString(2, vote);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void restartVotes(){
        String sql = "DELETE FROM votos";

        try (Connection conn = this.connectVotos();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public Boolean hasVoted(String nick){
        boolean exist = false;
        String sql = "SELECT COUNT(*) FROM votos WHERE nick = ?";
        try (Connection conn = this.connectVotos();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, nick);//
            ResultSet rs  = pstmt.executeQuery();
            while(rs.next()){
                if(rs.getInt(1) != 0){
                    exist = true;
                }
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exist;
    }
    public int getVotes(String candidato){
        int exist = 0;
        String sql = "SELECT COUNT(*) FROM votos WHERE vote = ?";
        try (Connection conn = this.connectVotos();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, candidato);//
            ResultSet rs  = pstmt.executeQuery();
            while(rs.next()){
                if(rs.getInt(1) != 0){
                    exist = rs.getInt(1);
                }
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exist;
    }
    ////////////////////SQLITE LEIS//////////////////////
    ////////////////////SQLITE LEIS//////////////////////
    ////////////////////SQLITE LEIS//////////////////////
    public void criarDbLeis() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS leis (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	regra string NOT NULL,\n"
                + "	descricao string NOT NULL,\n"
                + "	multa integer NOT NULL,\n"
                + "	punicao string NOT NULL,\n"
                + "	criador string NOT NULL\n"
                + ");";

        try (Connection conn =  connectLeis();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connectLeis().close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
    public void createLei(String regra, String descricao, String nick) {
        String sql = "INSERT INTO leis (regra, descricao, multa, punicao, criador) VALUES(?, ?, 0, 0, ?)";

        try (Connection conn = this.connectLeis();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, regra);
            pstmt.setString(2, descricao);
            pstmt.setString(3, nick);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateLeis(){
        String sql = "SELECT id, regra, descricao, multa, punicao, criador FROM leis";

        try (Connection conn = this.connectLeis();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            ResultSet rs  = pstmt.executeQuery();

            while (rs.next()) {
                pl.leisVar.id.add(rs.getInt("id"));
                pl.leisVar.regra.add(rs.getString("regra"));
                pl.leisVar.descricao.add(rs.getString("descricao"));
                pl.leisVar.multa.add(rs.getInt("multa"));
                pl.leisVar.punicao.add(rs.getString("punicao"));
                pl.leisVar.criador.add(rs.getString("criador"));
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public Boolean hasID(String id){
        boolean exist = false;
        String sql = "SELECT COUNT(*) FROM leis WHERE id = ?";
        try (Connection conn = this.connectLeis();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, id);//
            ResultSet rs  = pstmt.executeQuery();
            while(rs.next()){
                if(rs.getInt(1) != 0){
                    exist = true;
                }
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exist;
    }
    public Boolean hasRule(String regra){
        boolean exist = false;
        String sql = "SELECT COUNT(*) FROM leis WHERE regra = ?";

        try (Connection conn = this.connectLeis();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, regra);//
            ResultSet rs  = pstmt.executeQuery();
            while(rs.next()){
                if(rs.getInt(1) != 0){
                    exist = true;
                }
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exist;
    }
    public void deleteLei(String id) {
        String sql = "DELETE FROM leis WHERE id = ?";

        try (Connection conn = this.connectLeis();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public String getCreated(String nick){
        String created = null;
        String sql = "SELECT";

        try (Connection conn = this.connectLeis();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, nick);//
            ResultSet rs  = pstmt.executeQuery();

            while (rs.next()) {
                nick = rs.getString("nick");
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return nick;
    }


}
