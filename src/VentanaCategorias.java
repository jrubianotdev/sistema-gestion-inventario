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
                    String sql = "INSERT INTO CATEGORIA (id_categoria, nombre, descripcion) VALUES (" + id_categoria
                            + ", '" + nombre + "', '" + descripcion
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

            JDialog dialog = new JDialog(this, "Actualizar Categoría", true);
            dialog.setSize(350, 280);
            dialog.setLayout(null);
            dialog.setLocationRelativeTo(this);

            JLabel lblCategoria = new JLabel("Categoría:");
            lblCategoria.setBounds(20, 20, 100, 25);
            dialog.add(lblCategoria);

            JComboBox<String> cmbCategoria = new JComboBox<>();

            try {

                Connection conn = ConexionDB.getConexion();

                Statement stmt = conn.createStatement();

                ResultSet rs = stmt.executeQuery(
                        "SELECT nombre FROM categoria");

                while (rs.next()) {
                    cmbCategoria.addItem(
                            rs.getString("nombre"));
                }

                conn.close();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                        dialog,
                        ex.getMessage());
            }

            cmbCategoria.setBounds(120, 20, 180, 25);
            dialog.add(cmbCategoria);

            JLabel lblNombre = new JLabel("Nuevo nombre:");
            lblNombre.setBounds(20, 70, 100, 25);
            dialog.add(lblNombre);

            JTextField txtNombre = new JTextField();
            txtNombre.setBounds(120, 70, 180, 25);
            dialog.add(txtNombre);

            JLabel lblDescripcion = new JLabel("Descripción:");

            lblDescripcion.setBounds(20, 110, 100, 25);
            dialog.add(lblDescripcion);

            JTextField txtDescripcion = new JTextField();

            txtDescripcion.setBounds(
                    120, 110, 180, 25);

            dialog.add(txtDescripcion);

            JButton btnGuardar = new JButton("Actualizar");

            btnGuardar.setBounds(
                    100, 170, 120, 30);

            dialog.add(btnGuardar);

            btnGuardar.addActionListener(ev -> {

                String categoriaVieja = (String) cmbCategoria.getSelectedItem();

                String nombreNuevo = txtNombre.getText().trim();

                String descripcionNueva = txtDescripcion.getText().trim();

                try {

                    Connection conn = ConexionDB.getConexion();

                    String sql = "UPDATE categoria SET " +
                            "nombre='" + nombreNuevo + "'," +
                            "descripcion='" + descripcionNueva + "' " +

                            "WHERE nombre='" +
                            categoriaVieja + "'";

                    int filas = conn.createStatement()
                            .executeUpdate(sql);

                    conn.close();

                    if (filas > 0) {

                        JOptionPane.showMessageDialog(
                                dialog,
                                "Categoría actualizada");

                        dialog.dispose();

                    }

                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(
                            dialog,
                            "Error: " + ex.getMessage());

                }

            });

            dialog.setVisible(true);

        });

        JButton btnEliminar = new JButton("Eliminar Datos");
        btnEliminar.setBounds(100, 150, 200, 30);
        add(btnEliminar);

        btnEliminar.addActionListener(e -> {

            JDialog dialog = new JDialog(this, "Eliminar Categoría", true);
            dialog.setSize(350, 200);
            dialog.setLayout(null);
            dialog.setLocationRelativeTo(this);

            JLabel lblCategoria = new JLabel("Categoría:");

            lblCategoria.setBounds(20, 30, 100, 25);
            dialog.add(lblCategoria);

            JComboBox<String> cmbCategoria = new JComboBox<>();

            try {

                Connection conn = ConexionDB.getConexion();

                Statement stmt = conn.createStatement();

                ResultSet rs = stmt.executeQuery(
                        "SELECT nombre FROM categoria");

                while (rs.next()) {

                    cmbCategoria.addItem(
                            rs.getString("nombre"));

                }

                conn.close();

            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(
                        dialog,
                        ex.getMessage());

            }

            cmbCategoria.setBounds(
                    120, 30, 180, 25);

            dialog.add(cmbCategoria);

            JButton btnEliminarFinal = new JButton("Eliminar");

            btnEliminarFinal.setBounds(
                    100, 90, 120, 30);

            dialog.add(btnEliminarFinal);

            btnEliminarFinal.addActionListener(ev -> {

                String categoria = (String) cmbCategoria.getSelectedItem();

                int respuesta = JOptionPane.showConfirmDialog(
                        dialog,
                        "¿Eliminar categoría?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {

                    try {

                        Connection conn = ConexionDB.getConexion();

                        String sql = "DELETE FROM categoria " +
                                "WHERE nombre='" +
                                categoria + "'";

                        int filas = conn.createStatement()
                                .executeUpdate(sql);

                        conn.close();

                        if (filas > 0) {

                            JOptionPane.showMessageDialog(
                                    dialog,
                                    "Categoría eliminada");

                            dialog.dispose();

                        }

                    } catch (SQLException ex) {

                        JOptionPane.showMessageDialog(
                                dialog,
                                "Error: " +
                                        ex.getMessage());

                    }

                }

            });

            dialog.setVisible(true);

        });

        JButton btnConsultar = new JButton("Consultar Información");
        btnConsultar.setBounds(100, 200, 200, 30);
        add(btnConsultar);

        btnConsultar.addActionListener(e -> {

        });

        setVisible(true);
    }

}
