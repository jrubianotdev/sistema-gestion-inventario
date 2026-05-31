import java.sql.*;
import javax.swing.*;

public class VentanaCategorias extends JFrame {

    private static int id_categoria;

    public VentanaCategorias() {

        try {
            Connection conn = ConexionDB.getConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NVL(MAX(ID_CATEGORIA), 1000) + 1 FROM CATEGORIA");
            if (rs.next()) {
                id_categoria = rs.getInt(1);
            }
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar: " + e.getMessage());
        }

        setTitle("Categorías");
        setSize(400, 350);
        setLayout(null);
        setLocationRelativeTo(null);

        JButton btnInsertar = new JButton("Insertar Datos");
        btnInsertar.setBounds(100, 50, 200, 30);
        add(btnInsertar);

        btnInsertar.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Insertar Categoría", true);
            dialog.setSize(350, 220);
            dialog.setLayout(null);
            dialog.setLocationRelativeTo(this);

            JLabel lblNombre = new JLabel("Nombre:");
            lblNombre.setBounds(20, 20, 100, 25);
            dialog.add(lblNombre);

            JTextField txtNombre = new JTextField();
            txtNombre.setBounds(120, 20, 180, 25);
            dialog.add(txtNombre);

            JLabel lblDescripcion = new JLabel("Descripción:");
            lblDescripcion.setBounds(20, 60, 100, 25);
            dialog.add(lblDescripcion);

            JTextField txtDescripcion = new JTextField();
            txtDescripcion.setBounds(120, 60, 180, 25);
            dialog.add(txtDescripcion);

            JButton btnGuardar = new JButton("Guardar");
            btnGuardar.setBounds(100, 110, 120, 30);
            dialog.add(btnGuardar);

            btnGuardar.addActionListener(ev -> {
                String nombre = txtNombre.getText().trim();
                String descripcion = txtDescripcion.getText().trim();

                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "El nombre es obligatorio.");
                    return;
                }

                try {
                    Connection conn = ConexionDB.getConexion();
                    String sql = "INSERT INTO CATEGORIA (id_categoria, nombre, descripcion) VALUES (" + id_categoria + ", '" + nombre + "', '" + descripcion
                            + "')";
                    conn.createStatement().executeUpdate(sql);
                    conn.close();
                    JOptionPane.showMessageDialog(dialog, "Categoría insertada correctamente.");
                    id_categoria++;
                    dialog.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
                }
            });

            dialog.setVisible(true);
        });

        JButton btnActualizar = new JButton("Actualizar Datos");
        btnActualizar.setBounds(100, 100, 200, 30);
        add(btnActualizar);

        btnActualizar.addActionListener(e -> {
        });

        JButton btnEliminar = new JButton("Eliminar Datos");
        btnEliminar.setBounds(100, 150, 200, 30);
        add(btnEliminar);

        btnEliminar.addActionListener(e -> {

        });

        JButton btnConsultar = new JButton("Consultar Información");
        btnConsultar.setBounds(100, 200, 200, 30);
        add(btnConsultar);

        btnConsultar.addActionListener(e -> {

        });

        setVisible(true);
    }

}
