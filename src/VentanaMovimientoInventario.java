import java.sql.*;
import javax.swing.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class VentanaMovimientoInventario extends JFrame {

    private static int id_movimiento;

    public VentanaMovimientoInventario() {

        try {
            Connection conn = ConexionDB.getConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NVL(MAX(ID_MOVIMIENTO), 5000) + 1 FROM MOVIMIENTO_INVENTARIO");
            if (rs.next()) {
                id_movimiento = rs.getInt(1);
            }
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar: " + e.getMessage());
        }

        setTitle("Movimientos");
        setSize(400, 350);
        setLayout(null);
        setLocationRelativeTo(null);

        JButton btnInsertar = new JButton("Insertar Datos");
        btnInsertar.setBounds(100, 50, 200, 30);
        add(btnInsertar);

        btnInsertar.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Insertar Movimiento", true);
            dialog.setSize(350, 350);
            dialog.setLayout(null);
            dialog.setLocationRelativeTo(this);

            JComboBox<String> cmbIdProducto = new JComboBox<>();

            try {
                Connection conn = ConexionDB.getConexion();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT NOMBRE FROM PRODUCTO");
                while (rs.next()) {
                    cmbIdProducto.addItem(rs.getString("NOMBRE"));
                }
                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al conectar: " + ex.getMessage());
            }

            JLabel lblIdProducto = new JLabel("Producto:");
            lblIdProducto.setBounds(20, 20, 100, 25);
            dialog.add(lblIdProducto);

            cmbIdProducto.setBounds(120, 20, 180, 25);
            dialog.add(cmbIdProducto);

            JComboBox<String> cmbIdTipoMov = new JComboBox<>();

            try {
                Connection conn = ConexionDB.getConexion();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT DESCRIPCION FROM TIPO_MOVIMIENTO");
                while (rs.next()) {
                    cmbIdTipoMov.addItem(rs.getString("DESCRIPCION"));
                }
                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al conectar: " + ex.getMessage());
            }

            JLabel lblIdTipoMov = new JLabel("Tipo Mov:");
            lblIdTipoMov.setBounds(20, 60, 100, 25);
            dialog.add(lblIdTipoMov);

            cmbIdTipoMov.setBounds(120, 60, 180, 25);
            dialog.add(cmbIdTipoMov);

            LocalDateTime fechaHoraActual = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaFormateada = fechaHoraActual.format(formato);

            JLabel lblCantidad = new JLabel("Cantidad:");
            lblCantidad.setBounds(20, 100, 100, 25);
            dialog.add(lblCantidad);

            JTextField txtCantidad = new JTextField();
            txtCantidad.setBounds(120, 100, 180, 25);
            dialog.add(txtCantidad);

            JLabel lblDescripcion = new JLabel("Descripcion:");
            lblDescripcion.setBounds(20, 140, 100, 25);
            dialog.add(lblDescripcion);

            JTextField txtDescripcion = new JTextField();
            txtDescripcion.setBounds(120, 140, 180, 25);
            dialog.add(txtDescripcion);

            JButton btnGuardar = new JButton("Guardar");
            btnGuardar.setBounds(100, 180, 120, 30);
            dialog.add(btnGuardar);

            btnGuardar.addActionListener(ev -> {

                String nombreProducto = (String) cmbIdProducto.getSelectedItem();
                String descripcionMovimiento = (String) cmbIdTipoMov.getSelectedItem();
                String fecha = fechaFormateada;
                String cantidad = txtCantidad.getText().trim();
                try {
                    Integer.parseInt(cantidad);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser numérica.");
                    return;
                }
                String descripcion = txtDescripcion.getText().trim();

                if (nombreProducto == null || descripcionMovimiento == null || cantidad.isEmpty()
                        || descripcion.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Todos los campos son obligatorios.");
                    return;
                }

                try {
                    Connection conn = ConexionDB.getConexion();
                    String sql = "INSERT INTO MOVIMIENTO_INVENTARIO " +
                            "(id_movimiento,id_producto,id_tipo_mov,fecha,cantidad,descripcion) " +
                            "VALUES(" +
                            id_movimiento +
                            ",(SELECT ID_PRODUCTO FROM PRODUCTO WHERE NOMBRE = '" + nombreProducto + "')" +
                            ",(SELECT ID_TIPO_MOV FROM TIPO_MOVIMIENTO WHERE DESCRIPCION = '" + descripcionMovimiento
                            + "')" +
                            ",TO_DATE('" + fecha + "','YYYY-MM-DD HH24:MI:SS')" +
                            "," + cantidad +
                            ",'" + descripcion + "')";

                    conn.createStatement().executeUpdate(sql);
                    conn.close();
                    JOptionPane.showMessageDialog(dialog, "Movimiento insertado correctamente.");
                    id_movimiento++;
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

            JDialog dialog = new JDialog(this, "Actualizar Movimiento", true);
            dialog.setSize(420, 380);
            dialog.setLayout(null);
            dialog.setLocationRelativeTo(this);

            JLabel lblMovimiento = new JLabel("Movimiento:");
            lblMovimiento.setBounds(20, 20, 100, 25);
            dialog.add(lblMovimiento);

            JComboBox<String> cmbMovimiento = new JComboBox<>();
            cmbMovimiento.setBounds(120, 20, 240, 25);
            dialog.add(cmbMovimiento);

            JLabel lblCantidad = new JLabel("Cantidad:");
            lblCantidad.setBounds(20, 70, 100, 25);
            dialog.add(lblCantidad);

            JTextField txtCantidad = new JTextField();
            txtCantidad.setBounds(120, 70, 240, 25);
            dialog.add(txtCantidad);

            JLabel lblDescripcion = new JLabel("Descripción:");
            lblDescripcion.setBounds(20, 110, 100, 25);
            dialog.add(lblDescripcion);

            JTextField txtDescripcion = new JTextField();
            txtDescripcion.setBounds(120, 110, 240, 25);
            dialog.add(txtDescripcion);

            try {

                Connection conn = ConexionDB.getConexion();
                Statement stmt = conn.createStatement();

                ResultSet rs = stmt.executeQuery(
                        "SELECT descripcion FROM MOVIMIENTO_INVENTARIO");

                while (rs.next()) {
                    cmbMovimiento.addItem(rs.getString("descripcion"));
                }

                conn.close();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage());
            }

            JButton btnActualizarFinal = new JButton("Actualizar");
            btnActualizarFinal.setBounds(140, 170, 120, 30);
            dialog.add(btnActualizarFinal);

            btnActualizarFinal.addActionListener(ev -> {

                String movimiento = (String) cmbMovimiento.getSelectedItem();
                String cantidad = txtCantidad.getText().trim();
                String descripcion = txtDescripcion.getText().trim();

                if (cantidad.isEmpty() || descripcion.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "No pueden haber campos vacíos");
                    return;
                }

                try {

                    Integer.parseInt(cantidad);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "La cantidad debe ser numérica");
                    return;
                }

                try {

                    Connection conn = ConexionDB.getConexion();

                    String sql = "UPDATE MOVIMIENTO_INVENTARIO SET " +
                            "cantidad=" + cantidad + ", " +
                            "descripcion='" + descripcion + "' " +
                            "WHERE descripcion='" + movimiento + "'";

                    int filas = conn.createStatement().executeUpdate(sql);

                    conn.close();

                    if (filas > 0) {
                        JOptionPane.showMessageDialog(dialog, "Movimiento actualizado");
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

            JDialog dialog = new JDialog(this, "Eliminar Movimiento", true);
            dialog.setSize(380, 220);
            dialog.setLayout(null);
            dialog.setLocationRelativeTo(this);

            JLabel lblMovimiento = new JLabel("Movimiento:");
            lblMovimiento.setBounds(20, 30, 100, 25);
            dialog.add(lblMovimiento);

            JComboBox<String> cmbMovimiento = new JComboBox<>();
            cmbMovimiento.setBounds(120, 30, 220, 25);
            dialog.add(cmbMovimiento);

            try {

                Connection conn = ConexionDB.getConexion();
                Statement stmt = conn.createStatement();

                ResultSet rs = stmt.executeQuery(
                        "SELECT descripcion FROM MOVIMIENTO_INVENTARIO");

                while (rs.next()) {
                    cmbMovimiento.addItem(rs.getString("descripcion"));
                }

                conn.close();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage());
            }

            JButton btnEliminarFinal = new JButton("Eliminar");
            btnEliminarFinal.setBounds(130, 90, 120, 30);
            dialog.add(btnEliminarFinal);

            btnEliminarFinal.addActionListener(ev -> {

                String movimiento = (String) cmbMovimiento.getSelectedItem();

                int respuesta = JOptionPane.showConfirmDialog(
                        dialog,
                        "¿Eliminar movimiento?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {

                    try {

                        Connection conn = ConexionDB.getConexion();

                        String sql = "DELETE FROM MOVIMIENTO_INVENTARIO WHERE descripcion='" + movimiento + "'";

                        int filas = conn.createStatement().executeUpdate(sql);

                        conn.close();

                        if (filas > 0) {
                            JOptionPane.showMessageDialog(dialog, "Movimiento eliminado");
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
