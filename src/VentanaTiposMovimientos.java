import java.sql.*;
import javax.swing.*;

public class VentanaTiposMovimientos extends JFrame {

    private static int id_tipo_mov;

    public VentanaTiposMovimientos() {

        try {
            Connection conn = ConexionDB.getConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NVL(MAX(ID_TIPO_MOV), 4000) + 1 FROM TIPO_MOVIMIENTO");
            if (rs.next()) {
                id_tipo_mov = rs.getInt(1);
            }
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar: " + e.getMessage());
        }

        setTitle("Tipos de Movimientos");
        setSize(400, 350);
        setLayout(null);
        setLocationRelativeTo(null);

        JButton btnInsertar = new JButton("Insertar Datos");
        btnInsertar.setBounds(100, 50, 200, 30);
        add(btnInsertar);

        btnInsertar.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Insertar Tipo de Movimiento", true);
            dialog.setSize(350, 330);
            dialog.setLayout(null);
            dialog.setLocationRelativeTo(this);

            JLabel lblOperacion = new JLabel("Operación:");
            lblOperacion.setBounds(20, 20, 100, 25);
            dialog.add(lblOperacion);

            JComboBox<String> cmbOperacion = new JComboBox<>(new String[] { "Entrada", "Salida" });
            cmbOperacion.setBounds(120, 20, 180, 25);
            dialog.add(cmbOperacion);

            JLabel lblDescripcion = new JLabel("Descripción:");
            lblDescripcion.setBounds(20, 60, 100, 25);
            dialog.add(lblDescripcion);

            JTextField txtDescripcion = new JTextField();
            txtDescripcion.setBounds(120, 60, 180, 25);
            dialog.add(txtDescripcion);

            JButton btnGuardar = new JButton("Guardar");
            btnGuardar.setBounds(100, 230, 120, 30);
            dialog.add(btnGuardar);

            btnGuardar.addActionListener(ev -> {
                String operacionSeleccionada = (String) cmbOperacion.getSelectedItem();
                if (operacionSeleccionada.equals("Entrada")) {
                    operacionSeleccionada = "E";
                } else if (operacionSeleccionada.equals("Salida")) {
                    operacionSeleccionada = "S";
                }
                String descripcion = txtDescripcion.getText().trim();

                if (operacionSeleccionada.isEmpty() || descripcion.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "No pueden haber campos vacíos");
                    return;
                }

                try {
                    Connection conn = ConexionDB.getConexion();
                    String sql = "INSERT INTO TIPO_MOVIMIENTO (id_tipo_mov, operacion, descripcion) VALUES ("
                            + id_tipo_mov + ", '" + operacionSeleccionada + "', '" + descripcion + "')";
                    conn.createStatement().executeUpdate(sql);
                    conn.close();
                    JOptionPane.showMessageDialog(dialog, "Tipo de Movimiento insertado correctamente.");
                    id_tipo_mov++;
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

            JDialog dialog = new JDialog(this, "Actualizar Tipo de Movimiento", true);
            dialog.setSize(400, 280);
            dialog.setLayout(null);
            dialog.setLocationRelativeTo(this);

            JLabel lblTipo = new JLabel("Tipo:");
            lblTipo.setBounds(20, 20, 100, 25);
            dialog.add(lblTipo);

            JComboBox<String> cmbTipo = new JComboBox<>();
            cmbTipo.setBounds(120, 20, 220, 25);
            dialog.add(cmbTipo);

            JLabel lblOperacion = new JLabel("Operación:");
            lblOperacion.setBounds(20, 70, 100, 25);
            dialog.add(lblOperacion);

            JComboBox<String> cmbOperacion = new JComboBox<>(new String[] { "Entrada", "Salida" });
            cmbOperacion.setBounds(120, 70, 220, 25);
            dialog.add(cmbOperacion);

            JLabel lblDescripcion = new JLabel("Descripción:");
            lblDescripcion.setBounds(20, 110, 100, 25);
            dialog.add(lblDescripcion);

            JTextField txtDescripcion = new JTextField();
            txtDescripcion.setBounds(120, 110, 220, 25);
            dialog.add(txtDescripcion);

            try {

                Connection conn = ConexionDB.getConexion();
                Statement stmt = conn.createStatement();

                ResultSet rs = stmt.executeQuery(
                        "SELECT descripcion FROM TIPO_MOVIMIENTO");

                while (rs.next()) {
                    cmbTipo.addItem(rs.getString("descripcion"));
                }

                conn.close();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage());
            }

            JButton btnActualizarFinal = new JButton("Actualizar");
            btnActualizarFinal.setBounds(130, 170, 120, 30);
            dialog.add(btnActualizarFinal);

            btnActualizarFinal.addActionListener(ev -> {

                String tipo = (String) cmbTipo.getSelectedItem();
                String operacion = (String) cmbOperacion.getSelectedItem();
                String descripcion = txtDescripcion.getText().trim();

                if (descripcion.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "No pueden haber campos vacíos");
                    return;
                }

                if (operacion.equals("Entrada")) {
                    operacion = "E";
                } else {
                    operacion = "S";
                }

                try {

                    Connection conn = ConexionDB.getConexion();

                    String sql = "UPDATE TIPO_MOVIMIENTO SET " +
                            "operacion='" + operacion + "', " +
                            "descripcion='" + descripcion + "' " +
                            "WHERE descripcion='" + tipo + "'";

                    int filas = conn.createStatement().executeUpdate(sql);

                    conn.close();

                    if (filas > 0) {
                        JOptionPane.showMessageDialog(dialog, "Tipo de movimiento actualizado");
                        dialog.dispose();
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
                }

            });

            dialog.setVisible(true);
        });

        JButton btnEliminar = new JButton("Eliminar Datos");
        btnEliminar.setBounds(100, 150, 200, 30);
        add(btnEliminar);

        btnEliminar.addActionListener(e -> {

            JDialog dialog = new JDialog(this, "Eliminar Tipo de Movimiento", true);
            dialog.setSize(350, 200);
            dialog.setLayout(null);
            dialog.setLocationRelativeTo(this);

            JLabel lblTipo = new JLabel("Tipo:");
            lblTipo.setBounds(20, 30, 100, 25);
            dialog.add(lblTipo);

            JComboBox<String> cmbTipo = new JComboBox<>();
            cmbTipo.setBounds(120, 30, 180, 25);
            dialog.add(cmbTipo);

            try {

                Connection conn = ConexionDB.getConexion();
                Statement stmt = conn.createStatement();

                ResultSet rs = stmt.executeQuery(
                        "SELECT descripcion FROM TIPO_MOVIMIENTO");

                while (rs.next()) {
                    cmbTipo.addItem(rs.getString("descripcion"));
                }

                conn.close();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage());
            }

            JButton btnEliminarFinal = new JButton("Eliminar");
            btnEliminarFinal.setBounds(100, 90, 120, 30);
            dialog.add(btnEliminarFinal);

            btnEliminarFinal.addActionListener(ev -> {

                String tipo = (String) cmbTipo.getSelectedItem();

                int respuesta = JOptionPane.showConfirmDialog(
                        dialog,
                        "¿Eliminar tipo de movimiento?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {

                    try {

                        Connection conn = ConexionDB.getConexion();

                        String sql = "DELETE FROM TIPO_MOVIMIENTO WHERE descripcion='" + tipo + "'";

                        int filas = conn.createStatement().executeUpdate(sql);

                        conn.close();

                        if (filas > 0) {
                            JOptionPane.showMessageDialog(dialog, "Tipo eliminado");
                            dialog.dispose();
                        }

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
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
