package lesson2;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SQLiteBase {
    /*
    ЗАДАНИЕ:
    1. Создать CRUD операции, 1 метод создани таблицы 2 метод для добавления записи 3 метод для получения записи 4 метод для удаления записи 5 удаление таблицы
    */
    private static Connection connection;
    private static Statement statement;

    public static void connect(String baseName) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:" + baseName;
        connection = DriverManager.getConnection(url);
        statement = connection.createStatement();
    }

    public static void disconnect() throws SQLException {
        connection.close();
    }

    //1 метод создани таблицы
    public static int createTable(String tableName, String columnData) throws SQLException {
        String request = String.format("CREATE TABLE IF NOT EXISTS %s (%s)", tableName, columnData);
        return statement.executeUpdate(request);
    }

    //2 метод для добавления записи
    public static int insertData(String tableName, String values) throws SQLException {
        return statement.executeUpdate(String.format("INSERT INTO %s VALUES (%s);", tableName, values));
    }

    public static int[] insertData(String tableName, String[] values) throws SQLException {
        connection.setAutoCommit(false);
        for (int i = 0; i < values.length; i++) {
            statement.addBatch(String.format("INSERT INTO %s VALUES (%s);", tableName, values[i]));
        }
        int[] n = statement.executeBatch();
        connection.setAutoCommit(true);
        return n;
    }

    //3 метод для получения записи
    public static String[][] getData(String tableName, String columnName, String find) throws SQLException {
        ResultSet rs = statement.executeQuery(String.format("SELECT * FROM %s WHERE %s IN ('%s')",tableName, columnName, find));
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        ArrayList<String[]> arr = new ArrayList<>();
        while (rs.next()) {
            String[] s = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                s[i - 1] = rs.getString(i);
            }
            arr.add(s);
        }
        return arr.toArray(new String[0][]);
    }

    public static String[][] getData(String tableName) throws SQLException {
        ResultSet rs = statement.executeQuery(String.format("SELECT * FROM %s", tableName));
        int columnCount = rs.getMetaData().getColumnCount();
        ArrayList<String[]> arr = new ArrayList<>();
        while (rs.next()) {
            String[] s = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                s[i - 1] = rs.getString(i);
            }
            arr.add(s);
        }
        return arr.toArray(new String[0][]);
    }

    //4 метод для удаления записи
    public static int removeNote(String tableName) throws SQLException {
        return statement.executeUpdate(String.format("DELETE FROM %s", tableName));
    }


    public static int removeNote(String tableName, String columnName, String removedElName) throws SQLException {
        return statement.executeUpdate(String.format("DELETE FROM %s WHERE %s IN ('%s')", tableName, columnName, removedElName));
    }


    //5 удаление таблицы
    public static int removeTable(String tableName) throws SQLException {
        return statement.executeUpdate(String.format("DROP TABLE IF EXISTS %s", tableName));
    }

    public static void main(String[] args) {


        try {
            connect("baseLesson2.db");
            removeTable("table1");
            createTable("table1", "id INT, name VARCHAR, price DOUBLE");

            //добавляем данные в таблицу по одной строке
            insertData("table1", "1, 'pepper', 10.0");
            insertData("table1", "2, 'cola', 20.0");
            insertData("table1", "3, 'cheez', 30.0");

            //добавляем массив данных в таблицу
            String[] arrS = {"4, 'ice cream', 40.0", "5, 'cake', 50.0", "6, 'roll', 60.0"};
            insertData("table1", arrS);


            //смотрим содержимое таблицы
            System.out.println(Arrays.deepToString(getData("table1")));

            //выводим данные строки в которой содержится cola
            System.out.println(Arrays.deepToString(getData("table1", "name", "cola")));

            //удаляем элемент с названием cola
            removeNote("table1", "name", "cola");

            //проверяем что удалился
            System.out.println(Arrays.deepToString(getData("table1")));

            //удалим содержимое таблицы
            removeNote("table1");

            //проверяем что удалилось
            System.out.println(Arrays.deepToString(getData("table1")));

            disconnect();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
