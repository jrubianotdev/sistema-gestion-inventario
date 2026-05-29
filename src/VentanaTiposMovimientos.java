import java.sql.*;
import javax.swing.*;

public class VentanaTiposMovimientos extends JFrame{

    private static int id_tipo_mov;

    public VentanaTiposMovimientos(){

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
            
            JComboBox<String> cmbOperacion = new JComboBox<>(new String[] {"Entrada", "Salida"});
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
                    String sql = "INSERT INTO TIPO_MOVIMIENTO VALUES (" + id_tipo_mov + ", '" + operacionSeleccionada + "', '" + descripcion + "')";
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
