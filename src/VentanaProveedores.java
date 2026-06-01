import java.sql.*;
import javax.swing.*;

public class VentanaProveedores extends JFrame {

    private static int id_proveedor;

    public VentanaProveedores() {

        try {
            Connection conn = ConexionDB.getConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NVL(MAX(ID_PROVEEDOR), 3000) + 1 FROM PROVEEDOR");
            if (rs.next()) {
                id_proveedor = rs.getInt(1);
            }
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar: " + e.getMessage());
        }

        setTitle("Proveedores");
        setSize(400, 350);
        setLayout(null);
        setLocationRelativeTo(null);

        JButton btnInsertar = new JButton("Insertar Datos");
        btnInsertar.setBounds(100, 50, 200, 30);
        add(btnInsertar);

        btnInsertar.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Insertar Proveedor", true);
            dialog.setSize(350, 330);
            dialog.setLayout(null);
            dialog.setLocationRelativeTo(this);

            JLabel lblNombre = new JLabel("Nombre:");
            lblNombre.setBounds(20, 20, 100, 25);
            dialog.add(lblNombre);

            JTextField txtNombre = new JTextField();
            txtNombre.setBounds(120, 20, 180, 25);
            dialog.add(txtNombre);

            JLabel lblTelefono = new JLabel("Telefono:");
            lblTelefono.setBounds(20, 60, 100, 25);
            dialog.add(lblTelefono);

            JTextField txtTelefono = new JTextField();
            txtTelefono.setBounds(120, 60, 180, 25);
            dialog.add(txtTelefono);

            JLabel lblDireccion = new JLabel("Dirección:");
            lblDireccion.setBounds(20, 100, 100, 25);
            dialog.add(lblDireccion);

            JTextField txtDireccion = new JTextField();
            txtDireccion.setBounds(120, 100, 180, 25);
            dialog.add(txtDireccion);

            JLabel lblEmail = new JLabel("Email:");
            lblEmail.setBounds(20, 140, 100, 25);
            dialog.add(lblEmail);

            JTextField txtEmail = new JTextField();
            txtEmail.setBounds(120, 140, 180, 25);
            dialog.add(txtEmail);

            JButton btnGuardar = new JButton("Guardar");
            btnGuardar.setBounds(100, 230, 120, 30);
            dialog.add(btnGuardar);

            btnGuardar.addActionListener(ev -> {
                String nombre = txtNombre.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String direccion = txtDireccion.getText().trim();
                String email = txtEmail.getText().trim();

                if (nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "No pueden haber campos vacíos");
                    return;
                }

                try {
                    Connection conn = ConexionDB.getConexion();
                    String sql = "INSERT INTO PROVEEDOR (id_proveedor, nombre, telefono, direccion, email) VALUES ("
                            + id_proveedor + ", '" + nombre + "', '" + telefono + "', '" + direccion + "','" + email
                            + "')";
                    conn.createStatement().executeUpdate(sql);
                    conn.close();
                    JOptionPane.showMessageDialog(dialog, "Proveedor insertado correctamente.");
                    id_proveedor++;
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

            JDialog dialog = new JDialog(this, "Actualizar Proveedor", true);
            dialog.setSize(400, 320);
            dialog.setLayout(null);
            dialog.setLocationRelativeTo(this);

            JLabel lblProveedor = new JLabel("Proveedor:");
            lblProveedor.setBounds(20, 20, 100, 25);
            dialog.add(lblProveedor);

            JComboBox<String> cmbProveedor = new JComboBox<>();
            cmbProveedor.setBounds(120, 20, 220, 25);
            dialog.add(cmbProveedor);

            JLabel lblNombre = new JLabel("Nombre:");
            lblNombre.setBounds(20, 70, 100, 25);
            dialog.add(lblNombre);

            JTextField txtNombre = new JTextField();
            txtNombre.setBounds(120, 70, 220, 25);
            dialog.add(txtNombre);

            JLabel lblTelefono = new JLabel("Teléfono:");
            lblTelefono.setBounds(20, 110, 100, 25);
            dialog.add(lblTelefono);

            JTextField txtTelefono = new JTextField();
            txtTelefono.setBounds(120, 110, 220, 25);
            dialog.add(txtTelefono);

            JLabel lblDireccion = new JLabel("Dirección:");
            lblDireccion.setBounds(20, 150, 100, 25);
            dialog.add(lblDireccion);

            JTextField txtDireccion = new JTextField();
            txtDireccion.setBounds(120, 150, 220, 25);
            dialog.add(txtDireccion);

            JLabel lblEmail = new JLabel("Email:");
            lblEmail.setBounds(20, 190, 100, 25);
            dialog.add(lblEmail);

            JTextField txtEmail = new JTextField();
            txtEmail.setBounds(120, 190, 220, 25);
            dialog.add(txtEmail);

            try {

                Connection conn = ConexionDB.getConexion();
                Statement stmt = conn.createStatement();

                ResultSet rs = stmt.executeQuery(
                        "SELECT nombre FROM PROVEEDOR");

                while (rs.next()) {
                    cmbProveedor.addItem(rs.getString("nombre"));
                }

                conn.close();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage());
            }

            JButton btnActualizarFinal = new JButton("Actualizar");
            btnActualizarFinal.setBounds(130, 240, 120, 30);
            dialog.add(btnActualizarFinal);

            btnActualizarFinal.addActionListener(ev -> {

                String proveedor = (String) cmbProveedor.getSelectedItem();
                String nombre = txtNombre.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String direccion = txtDireccion.getText().trim();
                String email = txtEmail.getText().trim();

                if (nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "No pueden haber campos vacíos");
                    return;
                }

                try {

                    Connection conn = ConexionDB.getConexion();

                    String sql = "UPDATE PROVEEDOR SET " +
                            "nombre='" + nombre + "', " +
                            "telefono='" + telefono + "', " +
                            "direccion='" + direccion + "', " +
                            "email='" + email + "' " +
                            "WHERE nombre='" + proveedor + "'";

                    int filas = conn.createStatement().executeUpdate(sql);

                    conn.close();

                    if (filas > 0) {
                        JOptionPane.showMessageDialog(dialog, "Proveedor actualizado");
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

            JDialog dialog = new JDialog(this, "Eliminar Proveedor", true);
            dialog.setSize(350, 200);
            dialog.setLayout(null);
            dialog.setLocationRelativeTo(this);

            JLabel lblProveedor = new JLabel("Proveedor:");
            lblProveedor.setBounds(20, 30, 100, 25);
            dialog.add(lblProveedor);

            JComboBox<String> cmbProveedor = new JComboBox<>();
            cmbProveedor.setBounds(120, 30, 180, 25);
            dialog.add(cmbProveedor);

            try {

                Connection conn = ConexionDB.getConexion();
                Statement stmt = conn.createStatement();

                ResultSet rs = stmt.executeQuery(
                        "SELECT nombre FROM PROVEEDOR");

                while (rs.next()) {
                    cmbProveedor.addItem(rs.getString("nombre"));
                }

                conn.close();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage());
            }

            JButton btnEliminarFinal = new JButton("Eliminar");
            btnEliminarFinal.setBounds(100, 90, 120, 30);
            dialog.add(btnEliminarFinal);

            btnEliminarFinal.addActionListener(ev -> {

                String proveedor = (String) cmbProveedor.getSelectedItem();

                int respuesta = JOptionPane.showConfirmDialog(
                        dialog,
                        "¿Eliminar proveedor?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {

                    try {

                        Connection conn = ConexionDB.getConexion();

                        String sql = "DELETE FROM PROVEEDOR WHERE nombre='" + proveedor + "'";

                        int filas = conn.createStatement().executeUpdate(sql);

                        conn.close();

                        if (filas > 0) {
                            JOptionPane.showMessageDialog(dialog, "Proveedor eliminado");
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
