import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ProjectTimeTrackingApp extends JFrame {
    private JTextField projectNameField;
    private JButton startButton;
    private JButton stopButton;

    private Connection connection;

    private ProjectTimeTrackingApp() {
        setTitle("Project Time Tracking App");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);

        projectNameField = new JTextField(20);
        panel.add(projectNameField);

        startButton = new JButton("Start Project");
        panel.add(startButton);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startProject();
            }
        });

        stopButton = new JButton("Stop Project");
        panel.add(stopButton);
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopProject();
            }
        });

        // Bağlantı bilgilerini al
        String[] connectionInfo = getConnectionInfo();

        if (connectionInfo != null && connectionInfo.length == 4) {
            try {
                Class.forName(connectionInfo[3]);
                connection = DriverManager.getConnection(connectionInfo[0], connectionInfo[1], connectionInfo[2]);
                System.out.println("Connected to database");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Error connecting to database: " + e.getMessage());
            }
        } else {
            System.err.println("Error getting connection info.");
        }
    }

    // Bağlantı bilgilerini döndür
    private String[] getConnectionInfo() {
        String url = "jdbc:mysql://localhost:3306/proje";
        String username = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";

        return new String[]{url, username, password, driver};
    }

    private void startProject() {
        if (connection == null) {
            System.err.println("Connection is null, cannot start project.");
            return;
        }

        String projectName = projectNameField.getText();
        String query = "INSERT INTO sureler (proje_adi, baslangic_zamani) VALUES (?, NOW())";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, projectName);
            preparedStatement.executeUpdate();
            System.out.println("Project started: " + projectName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void stopProject() {
        if (connection == null) {
            System.err.println("Connection is null, cannot stop project.");
            return;
        }

        String query = "UPDATE sureler SET bitis_zamani = NOW() WHERE bitis_zamani IS NULL ORDER BY id DESC LIMIT 1";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Project stopped");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProjectTimeTrackingApp app = new ProjectTimeTrackingApp();
            app.setVisible(true);
        });
    }
}
