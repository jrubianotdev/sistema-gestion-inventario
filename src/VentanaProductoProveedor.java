import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class VentanaProductoProveedor extends JFrame{

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

            JComboBox<String> cmbIdProveedor= new JComboBox<>();

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
                            "VALUES("+
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
