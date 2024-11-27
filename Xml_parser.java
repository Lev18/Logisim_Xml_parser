import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import edu.princeton.cs.algs4.StdDraw;
import java.util.List;
import java.util.ArrayList;

public class Xml_parser {
    private static class Points{
        public  Points(int x_1, int x_2, int y_1, int y_2) {
            this.x1 = x_1;
            this.x2 = x_2;
            this.y1 = y_1;
            this.y2 = y_2;
        }
        public int x1;
        public int x2;
        public int y1;
        public int y2;
    }
    private static String regex = "[,\\.\\s\\<\\\"\\=\\(\\)]";
    private static List<Points> lines = new ArrayList<>();  
    private static List<Point> pins = new ArrayList<>();
    private static List<Point> and_gates = new ArrayList<>();
    private static List<Point> or_gates = new ArrayList<>();
    private static List<Point> xor_gates = new ArrayList<>();

    public static String strip(String s) {
        int str_len = s.length();
        int i = 0;
        while (i < str_len && s.charAt(i) == ' ') {
            i++;
        }

        return s.substring(i, str_len);
    }

    public static void withdr_line_coord(String line) {
        String[] tokens = line.split(regex);
        int i = 0;
        
         while (i < tokens.length) {       
            if (tokens[i].equals("wire")) {
                int x1 = Integer.parseInt(tokens[5]);
                int y1 = Integer.parseInt(tokens[6]);
                int x2 = Integer.parseInt(tokens[12]);
                int y2 = Integer.parseInt(tokens[13]);

               lines.add(new Points(x1, x2, y1, y2));
                /*StdDraw.setPenRadius(0.05);
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.point(0.5, 0.5);      
               */
                break;
            }
            if (tokens[i].equals("comp")) {
               if (tokens.length == 17)  {
                 int x = Integer.parseInt(tokens[9]);
                 int y = Integer.parseInt(tokens[10]);
                 pins.add(new Point(x, y));
                 break;
              }

              if (tokens[15].equals("AND")) {
                 int x = Integer.parseInt(tokens[9]);
                 int y = Integer.parseInt(tokens[10]);
                 and_gates.add(new Point(x, y));
                 break;
              }
              if (tokens[15].equals("OR")) {
                 int x = Integer.parseInt(tokens[9]);
                 int y = Integer.parseInt(tokens[10]);
                 or_gates.add(new Point(x, y));
                 break;
              }
              if (tokens[15].equals("XOR")) {
                 int x = Integer.parseInt(tokens[9]);
                 int y = Integer.parseInt(tokens[10]);
                 xor_gates.add(new Point(x, y));
                 break;
              }

            }
            i++;
         }
    }
// arc​(double x, double y, double radius, double angle1, double angle2)
    public static void main(String[] args) {
        BufferedReader reader;
        try {
            
            reader = new BufferedReader(new FileReader("fuladder.circ"));
            String line = reader.readLine();
            while(line != null) {
                String striped = strip(line);
                withdr_line_coord(striped);
                line = reader.readLine();
            }

                StdDraw.setXscale(0, 1000);
                StdDraw.setYscale(0, 1000);
                StdDraw.setPenRadius(0.005);
                StdDraw.setPenColor(StdDraw.GREEN);
                for (Points p : lines) {
                    StdDraw.line(p.x1, p.y1, p.x2, p.y2); 
                }

                StdDraw.setPenRadius(0.025);
                for (Point pin : pins) {
                    pin.draw();
                }

                StdDraw.setPenRadius(0.005);
                for (Point ands : and_gates) {
                    StdDraw.arc(ands.get_x() - 39, ands.get_y(), 32.5, 270.0, 90.0);
                    // StdDraw.arc(ands.get_x() - 72, ands.get_y(), 36.5, 300.0, 60.0);
                    StdDraw.line(ands.get_x() - 39, ands.get_y() + 32.5, ands.get_x() - 39, ands.get_y() - 32.5); 
                } 
                for (Point or : or_gates) {
                    StdDraw.arc(or.get_x() - 39, or.get_y(), 32.5, 250.0, 110.0);
                    StdDraw.arc(or.get_x() - 72, or.get_y(), 36.5, 300.0, 60.0);
                } 
                for (Point xor : xor_gates) {
                    StdDraw.arc(xor.get_x() - 41, xor.get_y(), 32.5, 250.0, 110.0);
                    StdDraw.arc(xor.get_x() - 78, xor.get_y(), 36.5, 300.0, 60.0);
                    StdDraw.arc(xor.get_x(), xor.get_y(), 5.0, 0.0, 380.0);
                } 

               //StdDraw.line(0.2, 0.2, 0.8, 0.2);
                StdDraw.show();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
