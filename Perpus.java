import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class View extends JFrame{
    JPanel control_panel_1 = new JPanel();
    JPanel control_panel_2 = new JPanel();
    JPanel main_panel = new JPanel();
    JButton tambah_button = new JButton("Tambah");
    JButton ubah_button = new JButton("Ubah");
    JButton hapus_button = new JButton("Hapus");
    JLabel label_judul = new JLabel("Judul Buku");
    JLabel label_penulis = new JLabel("Penulis Buku");
    JLabel label_rating = new JLabel("Rating");
    JLabel label_harga = new JLabel("Harga");
    JTextField field_judul = new JTextField();
    JTextField field_penulis = new JTextField();
    JTextField field_rating = new JTextField();
    JTextField field_harga = new JTextField();
    String[] kolom = {"Judul","Penulis","Rating","Harga"};
    String[][] data = new String[10][4]; 
    JTable tabel_data = new JTable(data,kolom);
    JScrollPane scroll = new JScrollPane(tabel_data);

    String DBurl = "jdbc:mysql://localhost:3306/perpustakaan";
    String DBuser = "root";
    String DBpass = "";
    
    public View(){
        setTitle("");
        setSize(650, 500);
        setLocation(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        tambah_button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String judul = field_judul.getText();
                String penulis = field_penulis.getText();
                Double rating = 0.0;
                int harga = 0;
                tambah_data(judul,penulis,rating,harga);
            }
        });


        control_panel_1.setLayout(new GridLayout(4,2));
        control_panel_1.add(label_judul);
        control_panel_1.add(field_judul);
        control_panel_1.add(label_penulis);
        control_panel_1.add(field_penulis);
        control_panel_1.add(label_rating);
        control_panel_1.add(field_rating);
        control_panel_1.add(label_harga);
        control_panel_1.add(field_harga);

        control_panel_2.setLayout(new FlowLayout());
        control_panel_2.add(tambah_button);
        control_panel_2.add(ubah_button);
        control_panel_2.add(hapus_button);

        main_panel.setLayout(new BorderLayout());
        main_panel.add(scroll, BorderLayout.CENTER);
        main_panel.add(control_panel_1, BorderLayout.NORTH);
        main_panel.add(control_panel_2, BorderLayout.SOUTH);
        add(main_panel);

        lihat_data();
    }

    public void lihat_data(){
        try{
            Connection koneksi = DriverManager.getConnection(DBurl, DBuser, DBpass);
            Statement syntax = koneksi.createStatement();
            String sql = "Select * from buku";
            ResultSet result = syntax.executeQuery(sql);
            int i = 0;
            while(result.next()){
                data[i][0] = result.getString("judul");
                data[i][1] = result.getString("penulis");
                data[i][2] = Double.toString(result.getDouble("rating"));
                //data[i][3] = String.valueof(result.getInt("harga"));
                i++;
            }
            syntax.close();
            koneksi.close();
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal Ditampilkan", "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void tambah_data(String judul, String penulis, Double rating, int harga){
        try{
            Connection koneksi = DriverManager.getConnection(DBurl, DBuser, DBpass);
            Statement syntax = koneksi.createStatement();
            String sql = "Insert into buku values('" + judul + "',"+ penulis + ","+ rating + "," + harga +")";            
            syntax.executeUpdate(sql);
            syntax.close();
            koneksi.close();
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal Dimasukan", "Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}




public class Perpus{
    public static void main(String[] args){
        new View();
    }
}
