import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {

        setTitle("Gestión de Inventario");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JButton btnCategorias = new JButton("Categorías");
        btnCategorias.setBounds(100, 50, 200, 30);
        add(btnCategorias);

        btnCategorias.addActionListener(e -> {
            new VentanaCategorias();
        });

        JButton btnProductos = new JButton("Productos");
        btnProductos.setBounds(100, 100, 200, 30);
        add(btnProductos);

        btnProductos.addActionListener(e -> {
            new VentanaProductos();
        });

        JButton btnProveedores = new JButton("Proveedores");
        btnProveedores.setBounds(100, 150, 200, 30);
        add(btnProveedores);

        btnProveedores.addActionListener(e -> {
            new VentanaProveedores();

        });

        JButton btnTiposMovimiento = new JButton("Tipos de Movimiento");
        btnTiposMovimiento.setBounds(100, 200, 200, 30);
        add(btnTiposMovimiento);

        btnTiposMovimiento.addActionListener(e -> {
            new VentanaTiposMovimientos();
        });
        
        JButton btnMovimientos= new JButton("Movimientos");
        btnMovimientos.setBounds(100, 250, 200, 30);
        add(btnMovimientos);

        btnMovimientos.addActionListener(e -> {
            new VentanaMovimientoInventario();
        });             

        setVisible(true);

    }

}

