import java.io.File;

public class NBody {
  

  private NBody() {

  }
  
  public static double readRadius(String file) {
    In in = new In(new File(file));
    in.readInt();
  
    return in.readDouble();
  }

  public static Planet[] readPlanets(String file) {
    In in = new In(new File(file));
    int num = in.readInt();
    in.readDouble();
    Planet[] planets = new Planet[num];

    for (int i = 0; i < num; i ++) {
      double xP = in.readDouble();
      double yP = in.readDouble();
      double xV = in.readDouble();
      double yV = in.readDouble();
      double m  = in.readDouble();
      String img = in.readString();
      planets[i] = new Planet(xP, yP, xV, yV, m, img);
    }
  
    return planets;
  }


  public static void main(String[] args) {
    double T = Double.parseDouble(args[0]);
    double dt = Double.parseDouble(args[1]);
    String filename = args[2];
    Planet[] planets = readPlanets(filename);
    double radius = readRadius(filename);
    int num = planets.length;

    StdDraw.setScale(-radius, radius);
    StdAudio.loop("audio/2001.mid");

    for (int i = 0; i < T; i += dt) {
      double[] xForce = new double[num];
      double[] yForce = new double[num];

      StdDraw.picture(0, 0, "images/starfield.jpg");
      for (int j = 0; j < num; j++) {
        xForce[j] = planets[j].calcNetForceExertedByX(planets);
        yForce[j] = planets[j].calcNetForceExertedByY(planets);
      }

      for (int j = 0; j < num; j++) {
        planets[j].update(dt, xForce[j], yForce[j]);
        planets[j].draw();
      }

      StdDraw.show(10);
    }
    StdOut.printf("%d\n", planets.length);
    StdOut.printf("%.2e\n", radius);
    for (int i = 0; i < planets.length; i++) {
      StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
          planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
    }		

    StdAudio.close();
  }
}
