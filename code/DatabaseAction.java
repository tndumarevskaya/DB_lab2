package sample;

import java.sql.*;


public class DatabaseAction {
    private   Statement st;
    private Connection c;

    public void connection(String user, String password) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/mysql";
        c = DriverManager.getConnection(url, user, password);
        //c = DriverManager.getConnection("jdbc:mysql://localhost:3306/?allowMultiQueries=true&user=" + user + "&password=" + password);
        st = c.createStatement();
    }

    public void createDB(String dbName) throws  SQLException {
        st.executeUpdate("CREATE DATABASE " + dbName + " DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;");
        st.executeUpdate("USE " + dbName);
        String toExecute = "CREATE TABLE customer (\n" +
                "   id INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                "   first_name VARCHAR(255) NOT NULL,\n" +
                "   last_name VARCHAR(255) NOT NULL, \n" +
                "   discount_card INT UNSIGNED NOT NULL,\n" +
                "   mobile_phone INT UNSIGNED NOT NULL,\n" +
                "   email VARCHAR(255) NOT NULL,\n" +
                "   PRIMARY KEY (id)\n" +
                ");\n";
        st.executeUpdate(toExecute);
        toExecute =
                "CREATE TABLE order_info (\n" +
                        "   id INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                        "   customer_id INT UNSIGNED NOT NULL,\n" +
                        "   price FLOAT UNSIGNED,\n" +
                        "   payment ENUM('credit_card', 'cash') NOT NULL,\n" +
                        "   PRIMARY KEY (id)\n" +
                        ");\n";
        st.executeUpdate(toExecute);
        toExecute =
                "CREATE TABLE product(\n" +
                        "   id INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                        "   price FLOAT UNSIGNED NOT NULL,\n" +
                        "   name VARCHAR(255) NOT NULL,\n" +
                        "   size ENUM('XS', 'S', 'M', 'L', 'XL') NOT NULL,\n" +
                        "   PRIMARY KEY (id)\n" +
                        ");\n";
        st.executeUpdate(toExecute);
        toExecute =
                "CREATE TABLE order_details (\n" +
                        "   id INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                        "   order_id INT UNSIGNED NOT NULL,\n" +
                        "   product_id INT UNSIGNED NOT NULL,\n" +
                        "   PRIMARY KEY (id),\n" +
                        "   CONSTRAINT order_info\n" +
                        "   FOREIGN KEY (order_id) REFERENCES customer (id)\n" +
                        "   ON UPDATE RESTRICT\n" +
                        "   ON DELETE RESTRICT,\n" +
                        "   CONSTRAINT order_product\n" +
                        "   FOREIGN KEY (product_id) REFERENCES product (id)\n" +
                        "   ON UPDATE RESTRICT\n" +
                        "   ON DELETE RESTRICT\n" +
                        ");\n";
        st.executeUpdate(toExecute);
        toExecute =
                "CREATE INDEX index_email ON customer (email);\n";
        st.executeUpdate(toExecute);
        toExecute =
                "CREATE TRIGGER order_insert AFTER INSERT ON order_details\n" +
                        "  FOR EACH ROW\n" +
                        "  BEGIN\n" +
                        "    UPDATE order_info SET price = price + (SELECT price FROM product WHERE NEW.product_id = product.id AND NEW.order_id = order_info.id);\n" +
                        "  END;\n";
        st.executeUpdate(toExecute);
        toExecute =
                "CREATE TRIGGER order_delete AFTER DELETE ON order_details\n" +
                        "  FOR EACH ROW\n" +
                        "  BEGIN\n" +
                        "    UPDATE order_info SET price = price - (SELECT price FROM product WHERE OLD.product_id = product.id AND OLD.order_id = order_info.id);\n" +
                        "  END;\n";
        st.executeUpdate(toExecute);
    }

    public void dropDB(String dbName) throws  SQLException {
        st.executeUpdate("DROP DATABASE " + dbName + ";");
    }

    public Statement getSt() {
        return st;
    }
    public void useDB(String dbName) throws  SQLException {
        st.execute("USE " + dbName + ";");
    }

    public void delete(String tableName, String id) throws SQLException{
        st.executeUpdate("DELETE FROM " + tableName + " WHERE id = " + id + ";");
    }

    public void insertCustomer(String id, String first_name, String last_name, String discount_card, String mobile_phone, String email) throws SQLException{
        st.executeUpdate("INSERT INTO customer (id, first_name, last_name, discount_card, mobile_phone, email)" +
                " VALUES ( " + id + ", '" +  first_name +  "', '" + last_name + "', " + discount_card + ", " +  mobile_phone + ", '" + email + "' );");
    }

    public void insertOrderInfo(String id, String customer_id, String price, String payment) throws SQLException{
        System.out.println("INSERT INTO order_info (customer_id, price, payment)" +
                " VALUES ( " + customer_id +  ", " + price + ", '" + payment + "' );");
        st.executeUpdate("INSERT INTO order_info (customer_id, price, payment)" +
                " VALUES ( " + customer_id +  ", " + price + ", '" + payment + "' );");
    }

    public void insertOrderDetails(String id, String order_id, String product_id) throws SQLException{
        st.executeUpdate("INSERT INTO order_details (order_id, product_id)" +
                " VALUES ( " + order_id +  ", " + product_id + " );");
    }

    public void insertProduct(String id, String name, String price, String size) throws SQLException{
        st.executeUpdate("INSERT INTO product (name, price, size)" +
                " VALUES ( '" + name +  "', " + price + ", '" + size + "' );");
    }

    public void update(String tableName, int id, String column, String newValue, boolean mode) throws SQLException{
        System.out.println("UPDATE " + tableName + " SET " + column + " = " + newValue + " WHERE id = " + id + ";");
        if (mode)
            st.executeUpdate("UPDATE " + tableName + " SET " + column + " = " + newValue + " WHERE id = " + id + ";");
        else
            st.executeUpdate("UPDATE " + tableName + " SET " + column + " = '" + newValue + "' WHERE id = " + id + ";");

    }

}
