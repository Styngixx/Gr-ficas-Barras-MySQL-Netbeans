package graficoframe;

import java.sql.*;
import javax.swing.JOptionPane;
import org.jfree.chart.*;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.*;

public class GraficoFrame extends javax.swing.JFrame {

    public GraficoFrame() {
        initComponents();
        mostrarGrafico();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        panelGráfico = new javax.swing.JPanel();

        setLocationByPlatform(true);
        setResizable(false);

        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout panelGráficoLayout = new javax.swing.GroupLayout(panelGráfico);
        panelGráfico.setLayout(panelGráficoLayout);
        panelGráficoLayout.setHorizontalGroup(
            panelGráficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );
        panelGráficoLayout.setVerticalGroup(
            panelGráficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        bg.add(panelGráfico, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 640, 450));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void mostrarGrafico(){
        String url="jdbc:mysql://localhost:3306/componentes", user = "root", psword = "root";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        try (Connection cnn = DriverManager.getConnection(url, user, psword)){
            String sql ="SELECT * FROM ventas"; Statement st = cnn.createStatement(); ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                dataset.addValue(rs.getInt("cantidad"), "ventas", rs.getString("producto"));
            }
        // Creación del gráfico :)
        JFreeChart chart = ChartFactory.createBarChart("Ventas por Producto", "Producto", "Cantidad Vendida", dataset);
        
        // Visualización en un panel dentro del JFrame
        ChartPanel cp = new ChartPanel(chart); cp.setPreferredSize(new java.awt.Dimension(600,400));
        
        panelGráfico.removeAll();panelGráfico.setLayout(new java.awt.BorderLayout());panelGráfico.add(cp, java.awt.BorderLayout.CENTER);
        panelGráfico.validate(); panelGráfico.repaint();
        
        cnn.close();    
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Something went wrong");
        }
      
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GraficoFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JPanel panelGráfico;
    // End of variables declaration//GEN-END:variables
}
