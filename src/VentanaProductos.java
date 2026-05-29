import java.sql.*;
import javax.swing.*;

public class VentanaProductos extends JFrame {

    private static int id_producto;

    public VentanaProductos() {

        try {
            Connection conn = ConexionDB.getConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NVL(MAX(ID_PRODUCTO), 2000) + 1 FROM PRODUCTO");
            if (rs.next()) {
                id_producto = rs.getInt(1);
            }
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar: " + e.getMessage());
        } 

        setTitle("Productos");
        setSize(400, 350);
        setLayout(null);
        setLocationRelativeTo(null);

        JButton btnInsertar = new JButton("Insertar Datos");
        btnInsertar.setBounds(100, 50, 200, 30);
        add(btnInsertar);

        btnInsertar.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Insertar producto", true);
            dialog.setSize(350, 330);
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

            JLabel lblStock = new JLabel("Stock:");
            lblStock.setBounds(20, 100, 100, 25);
            dialog.add(lblStock);

            JTextField txtStock = new JTextField();
            txtStock.setBounds(120, 100, 180, 25);
            dialog.add(txtStock);

            JLabel lblPrecioVenta = new JLabel("Precio Venta:");
            lblPrecioVenta.setBounds(20, 140, 100, 25);
            dialog.add(lblPrecioVenta);

            JTextField txtPrecioVenta = new JTextField();
            txtPrecioVenta.setBounds(120, 140, 180, 25);
            dialog.add(txtPrecioVenta);

            JComboBox<String> cmbCategoria = new JComboBox<>();
            
            try {
                Connection conn = ConexionDB.getConexion();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT NOMBRE FROM CATEGORIA");
                while (rs.next()) {
                    cmbCategoria.addItem(rs.getString("NOMBRE"));
                }
                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al conectar: " + ex.getMessage());
            }

            JLabel lblCategoria = new JLabel("Categoria:");
            lblCategoria.setBounds(20, 180, 100, 25);
            dialog.add(lblCategoria);

            cmbCategoria.setBounds(120, 180, 180, 25);
            dialog.add(cmbCategoria);

            JButton btnGuardar = new JButton("Guardar");
            btnGuardar.setBounds(100, 230, 120, 30);
            dialog.add(btnGuardar);

            btnGuardar.addActionListener(ev -> {
                String nombre = txtNombre.getText().trim();
                String descripcion = txtDescripcion.getText().trim();
                String stock = txtStock.getText().trim();
                String precio_venta = txtPrecioVenta.getText().trim();
                String categoriaSeleccionada = (String) cmbCategoria.getSelectedItem();

                if (nombre.isEmpty() || descripcion.isEmpty() || stock.isEmpty() || precio_venta.isEmpty() || categoriaSeleccionada.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "No pueden haber campos vacíos");
                    return;
                }

                try {
                    Connection conn = ConexionDB.getConexion();
                    String sql = "INSERT INTO PRODUCTO VALUES (" + id_producto + ", '" + nombre + "', '" + descripcion + "', " + stock + "," + precio_venta + 
                    ", (SELECT ID_CATEGORIA FROM CATEGORIA WHERE NOMBRE = '" + categoriaSeleccionada +"'))";
                    conn.createStatement().executeUpdate(sql);
                    conn.close();
                    JOptionPane.showMessageDialog(dialog, "Producto insertado correctamente.");
                    id_producto++;
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
