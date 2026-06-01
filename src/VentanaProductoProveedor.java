import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class VentanaProductoProveedor extends JFrame {

    public VentanaProductoProveedor() {

        setTitle("Asignar Proveedor a Producto");
        setSize(400, 350);
        setLayout(null);
        setLocationRelativeTo(null);

        JButton btnInsertar = new JButton("Insertar Datos");
        btnInsertar.setBounds(100, 50, 200, 30);
        add(btnInsertar);

        btnInsertar.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Asignar Proveedor a Producto", true);
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

            JComboBox<String> cmbIdProveedor = new JComboBox<>();

            try {
                Connection conn = ConexionDB.getConexion();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT NOMBRE FROM PROVEEDOR");
                while (rs.next()) {
                    cmbIdProveedor.addItem(rs.getString("NOMBRE"));
                }
                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al conectar: " + ex.getMessage());
            }

            JLabel lblIdProveedor = new JLabel("Proveedor:");
            lblIdProveedor.setBounds(20, 60, 100, 25);
            dialog.add(lblIdProveedor);

            cmbIdProveedor.setBounds(120, 60, 180, 25);
            dialog.add(cmbIdProveedor);

            LocalDateTime fechaHoraActual = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaFormateada = fechaHoraActual.format(formato);

            JLabel lblCantidad = new JLabel("Cantidad:");
            lblCantidad.setBounds(20, 100, 100, 25);
            dialog.add(lblCantidad);

            JTextField txtCantidad = new JTextField();
            txtCantidad.setBounds(120, 100, 180, 25);
            dialog.add(txtCantidad);

            JLabel lblPrecioCompra = new JLabel("Precio Compra:");
            lblPrecioCompra.setBounds(20, 140, 100, 25);
            dialog.add(lblPrecioCompra);

            JTextField txtPrecioCompra = new JTextField();
            txtPrecioCompra.setBounds(120, 140, 180, 25);
            dialog.add(txtPrecioCompra);

            JButton btnGuardar = new JButton("Guardar");
            btnGuardar.setBounds(100, 180, 120, 30);
            dialog.add(btnGuardar);

            btnGuardar.addActionListener(ev -> {

                String nombreProducto = (String) cmbIdProducto.getSelectedItem();
                String nombreProveedor = (String) cmbIdProveedor.getSelectedItem();
                String precio_compra = txtPrecioCompra.getText().trim();
                String fecha = fechaFormateada;
                String cantidad = txtCantidad.getText().trim();

                if (nombreProducto.isEmpty() || nombreProveedor.isEmpty() || precio_compra.isEmpty() || fecha.isEmpty()
                        || cantidad.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Todos los campos son obligatorios.");
                    return;
                }

                try {
                    Connection conn = ConexionDB.getConexion();
                    String sql = "INSERT INTO PRODUCTO_PROVEEDOR " +
                            "(id_producto,id_proveedor,precio_compra,fecha,cantidad) " +
                            "VALUES(" +
                            "(SELECT ID_PRODUCTO FROM PRODUCTO WHERE NOMBRE = '" + nombreProducto + "')" +
                            ",(SELECT ID_PROVEEDOR FROM PROVEEDOR WHERE NOMBRE= '" + nombreProveedor + "')" +
                            "," + precio_compra +
                            ",TO_DATE('" + fecha + "','YYYY-MM-DD HH24:MI:SS')" +
                            "," + cantidad + ")";
                    conn.createStatement().executeUpdate(sql);
                    conn.close();
                    JOptionPane.showMessageDialog(dialog, "Provedor asignado correctamente al Producto.");
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

            JDialog dialog = new JDialog(this,
                    "Actualizar Producto-Proveedor", true);

            dialog.setSize(400, 350);
            dialog.setLayout(null);
            dialog.setLocationRelativeTo(this);

            JLabel lblProducto = new JLabel("Producto:");

            lblProducto.setBounds(20, 20, 100, 25);
            dialog.add(lblProducto);

            JComboBox<String> cmbProducto = new JComboBox<>();

            cmbProducto.setBounds(120, 20, 200, 25);
            dialog.add(cmbProducto);

            try {

                Connection conn = ConexionDB.getConexion();

                Statement stmt = conn.createStatement();

                ResultSet rs = stmt.executeQuery(
                        "SELECT NOMBRE FROM PRODUCTO");

                while (rs.next()) {

                    cmbProducto.addItem(
                            rs.getString("NOMBRE"));

                }

                conn.close();

            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(
                        dialog,
                        ex.getMessage());

            }

            JLabel lblCantidad = new JLabel("Cantidad:");

            lblCantidad.setBounds(
                    20, 70, 100, 25);

            dialog.add(lblCantidad);

            JTextField txtCantidad = new JTextField();

            txtCantidad.setBounds(
                    120, 70, 200, 25);

            dialog.add(txtCantidad);

            JLabel lblPrecio = new JLabel("Precio:");

            lblPrecio.setBounds(
                    20, 120, 100, 25);

            dialog.add(lblPrecio);

            JTextField txtPrecio = new JTextField();

            txtPrecio.setBounds(
                    120, 120, 200, 25);

            dialog.add(txtPrecio);

            JButton btnActualizarFinal = new JButton("Actualizar");

            btnActualizarFinal.setBounds(
                    120, 190, 120, 30);

            dialog.add(btnActualizarFinal);

            btnActualizarFinal.addActionListener(ev -> {

                String producto = (String) cmbProducto.getSelectedItem();

                String cantidad = txtCantidad.getText();

                String precio = txtPrecio.getText();

                if (cantidad.isEmpty() ||
                        precio.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            dialog,
                            "Campos vacíos");

                    return;

                }

                try {

                    Connection conn = ConexionDB.getConexion();

                    String sql = "UPDATE PRODUCTO_PROVEEDOR " +
                            "SET cantidad=" + cantidad +
                            ", precio_compra=" + precio +
                            " WHERE id_producto=(" +
                            "SELECT id_producto " +
                            "FROM PRODUCTO " +
                            "WHERE nombre='" +
                            producto + "')";

                    int filas = conn.createStatement()
                            .executeUpdate(sql);

                    conn.close();

                    if (filas > 0) {

                        JOptionPane.showMessageDialog(
                                dialog,
                                "Registro actualizado");

                        dialog.dispose();

                    }

                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(
                            dialog,
                            ex.getMessage());

                }

            });

            dialog.setVisible(true);

        });

        JButton btnEliminar = new JButton("Eliminar Datos");
        btnEliminar.setBounds(100, 150, 200, 30);
        add(btnEliminar);

        btnEliminar.addActionListener(e -> {

            JDialog dialog = new JDialog(this,
                    "Eliminar Producto-Proveedor",
                    true);

            dialog.setSize(350, 200);
            dialog.setLayout(null);
            dialog.setLocationRelativeTo(this);

            JLabel lblProducto = new JLabel("Producto:");

            lblProducto.setBounds(
                    20, 30, 100, 25);

            dialog.add(lblProducto);

            JComboBox<String> cmbProducto = new JComboBox<>();

            try {

                Connection conn = ConexionDB.getConexion();

                Statement stmt = conn.createStatement();

                ResultSet rs = stmt.executeQuery(
                        "SELECT nombre FROM producto");

                while (rs.next()) {

                    cmbProducto.addItem(
                            rs.getString("nombre"));

                }

                conn.close();

            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(
                        dialog,
                        ex.getMessage());

            }

            cmbProducto.setBounds(
                    120, 30, 180, 25);

            dialog.add(cmbProducto);

            JButton btnEliminarFinal = new JButton("Eliminar");

            btnEliminarFinal.setBounds(
                    100, 90, 120, 30);

            dialog.add(btnEliminarFinal);

            btnEliminarFinal.addActionListener(ev -> {

                String producto = (String) cmbProducto.getSelectedItem();

                int respuesta = JOptionPane.showConfirmDialog(
                        dialog,
                        "¿Eliminar relación?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {

                    try {

                        Connection conn = ConexionDB.getConexion();

                        String sql = "DELETE FROM PRODUCTO_PROVEEDOR " +
                                "WHERE id_producto=(" +
                                "SELECT id_producto " +
                                "FROM producto " +
                                "WHERE nombre='" +
                                producto + "')";

                        int filas = conn.createStatement()
                                .executeUpdate(sql);

                        conn.close();

                        if (filas > 0) {

                            JOptionPane.showMessageDialog(
                                    dialog,
                                    "Relación eliminada");

                            dialog.dispose();

                        }

                    } catch (SQLException ex) {

                        JOptionPane.showMessageDialog(
                                dialog,
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
