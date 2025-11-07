package graficoframe;

import java.awt.BorderLayout;
import java.sql.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;

        
public class GraficoD1 extends javax.swing.JFrame {
    
    private final String url ="jdbc:mysql://localhost:3306/componentes?useSSL=false", user ="root", pword="Chap04";
    

    public GraficoD1() {
        initComponents();
        boogie.setLayout(new BorderLayout());
        cargarProductos();
        mostrarGraficoGeneral();
        agregarEventoLista();
    }
    
    private void cargarProductos(){
        DefaultListModel<String> model = new DefaultListModel<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection cnn = DriverManager.getConnection(url,user,pword);
            
            Statement st = cnn.createStatement();
            ResultSet rs = st.executeQuery("SELECT DISTINCT producto FROM Ventas ORDER BY producto");
            
            while (rs.next()) {                
                model.addElement(rs.getString("producto"));
            }
            
            List.setModel(model);
            cnn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar productos" + e.getMessage());
            
        }        
    }
    
    private void agregarEventoLista(){
        List.addListSelectionListener(new ListSelectionListener(){
        
        @Override
            public void valueChanged(ListSelectionEvent e){
                if (!e.getValueIsAdjusting()){
                    List<String> selectioned = List.getSelectedValuesList();
                    if(!selectioned.isEmpty()){
                        mostrarGraficoPorProductos(selectioned);
                    }
                }
            }
        
        });

    }
    
    private void mostrarGraficoGeneral(){
        DefaultCategoryDataset dset = new DefaultCategoryDataset();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection cnn = DriverManager.getConnection(url, user, pword);
            
            String sql ="Select producto, SUM(cantidad) AS total FROM Ventas GROUP BY producto ORDER BY producto";
            Statement st = cnn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                dset.addValue(rs.getInt("total"), "Ventas", rs.getString("producto"));
            }
            cnn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        actualizarGrafico(dset, "Ventas Totales por producto");
    }
    
    private void mostrarGraficoPorProductos(List<String> productos){
        DefaultCategoryDataset dset = new DefaultCategoryDataset();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection cnn = DriverManager.getConnection(url, user, pword);
            
            String placeholders = String.join(",", productos.stream().map(p -> "?").toArray(String[]::new));
            String sql ="SELECT producto, SUM(cantidad) AS total FROM Ventas WHERE producto IN (" + placeholders + ") GROUP BY producto";
            PreparedStatement ps = cnn.prepareStatement(sql);
            
            for(int i = 0; i < productos.size(); i++){
                ps.setString(i+1, productos.get(i));
            }
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dset.addValue(rs.getInt("total"), "Ventas", rs.getString("producto"));
            }
            
            cnn.close();
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
        actualizarGrafico(dset, "Ventas Totales por producto");
    }
       
    private void actualizarGrafico(DefaultCategoryDataset dset, String tittle){
        JFreeChart c = ChartFactory.createBarChart(
                tittle,
                "Producto",
                "Cantidad",
                dset
        );
        
        ChartPanel cpanel = new ChartPanel(c);
        cpanel.setPreferredSize(new java.awt.Dimension(850, 400));
        
        boogie.removeAll();
        boogie.add(cpanel, BorderLayout.CENTER);
        boogie.revalidate();
        boogie.repaint();
    }
            
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        List = new javax.swing.JList<>();
        boogie = new javax.swing.JPanel();

        setLocationByPlatform(true);
        setResizable(false);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("GRÁFICO DE VENTAS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 190, 30));

        jLabel2.setText("Seleccionar un producto:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 170, 160, 20));

        jScrollPane1.setViewportView(List);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 200, 130, 160));

        javax.swing.GroupLayout boogieLayout = new javax.swing.GroupLayout(boogie);
        boogie.setLayout(boogieLayout);
        boogieLayout.setHorizontalGroup(
            boogieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
        );
        boogieLayout.setVerticalGroup(
            boogieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );

        jPanel1.add(boogie, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 620, 360));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

     
    
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GraficoD1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> List;
    private javax.swing.JPanel boogie;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
