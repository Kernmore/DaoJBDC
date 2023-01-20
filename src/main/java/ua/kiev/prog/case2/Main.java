package ua.kiev.prog.case2;

import ua.kiev.prog.shared.Client;
import ua.kiev.prog.shared.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            // remove this
            try {
                try (Statement st = conn.createStatement()) {
                    st.execute("DROP TABLE IF EXISTS Clients");
                    //st.execute("CREATE TABLE Clients (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20) NOT NULL, age INT)");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            ClientDAOImpl2 dao = new ClientDAOImpl2(conn, "Clients");
            dao.createTable(Client.class);

            Client c = new Client("test", 1);
            Client b = new Client("te", 1);
            Client d = new Client("ttt", 3);
            Client a = new Client("sss", 2);
            dao.add(a);
            dao.add(b);
            dao.add(d);
            dao.add(c);

            // int id = c.getId(); >>>
            List<Client> clientsList = new ArrayList<>(List.of(d, c, b, a));
            for (Client cl : clientsList) {
                System.out.println(cl.getName() + ", ID=" + cl.getId());
            }

            List<Client> list = dao.getAll(Client.class);
            for (Client cli : list)
                System.out.println(cli);
            System.out.println();

            //call method with different parameters>>>
            dao.getAll(Client.class, "age", "name");
            List<Client> names = dao.getAll(Client.class, "name");
            for (Client cli : names)
                System.out.println(cli.getName());

            list.get(0).setAge(55);
            dao.update(list.get(0));
            dao.delete(list.get(0));
            /*

            List<Client> list = dao.getAll(Client.class, "name", "age");
            List<Client> list = dao.getAll(Client.class, "age");
            for (Client cli : list)
                System.out.println(cli);

             */


        }
    }
}
