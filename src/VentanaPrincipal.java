import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {

        setTitle("Gestión de Inventario");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JButton btnCategorias = new JButton("Categorías");
        btnCategorias.setBounds(75, 50, 250, 30);
        add(btnCategorias);

        btnCategorias.addActionListener(e -> {
            new VentanaCategorias();
        });

        JButton btnProductos = new JButton("Productos");
        btnProductos.setBounds(75, 100, 250, 30);
        add(btnProductos);

        btnProductos.addActionListener(e -> {
            new VentanaProductos();
        });

        JButton btnProveedores = new JButton("Proveedores");
        btnProveedores.setBounds(75, 150, 250, 30);
        add(btnProveedores);

        btnProveedores.addActionListener(e -> {
            new VentanaProveedores();

        });

        JButton btnTiposMovimiento = new JButton("Tipos de Movimiento");
        btnTiposMovimiento.setBounds(75, 200, 250, 30);
        add(btnTiposMovimiento);

        btnTiposMovimiento.addActionListener(e -> {
            new VentanaTiposMovimientos();
        });
        
        JButton btnMovimientos= new JButton("Movimientos");
        btnMovimientos.setBounds(75, 250, 250, 30);
        add(btnMovimientos);

        btnMovimientos.addActionListener(e -> {
            new VentanaMovimientoInventario();
        });             

        JButton btnProductoProveedor= new JButton("Asignar Proveedor a Producto");
        btnProductoProveedor.setBounds(75, 300, 250, 30);
        add(btnProductoProveedor);

        btnProductoProveedor.addActionListener(e -> {
            new VentanaProductoProveedor();
        });       

        setVisible(true);

    }

}

