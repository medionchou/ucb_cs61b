

import java.lang.Math;

public class Planet {

  private static double G = 6.67E-11;

  double xxPos;
  double yyPos;
  double xxVel;
  double yyVel;
  double mass;
  String imgFileName;

  public Planet(double xP, double yP, double xV, double yV, double m, String img) {
    xxPos       = xP;
    yyPos       = yP;
    xxVel       = xV;
    yyVel       = yV;
    mass        = m;
    imgFileName = img;
  }

  public Planet(Planet p) {
    xxPos       = p.xxPos;
    yyPos       = p.yyPos;
    xxVel       = p.xxVel;
    yyVel       = p.yyVel;
    mass        = p.mass;
    imgFileName = p.imgFileName;
  }

  public double calcDistance(Planet p) {
    return Math.sqrt((this.xxPos - p.xxPos) * (this.xxPos - p.xxPos) +  (this.yyPos - p.yyPos) * (this.yyPos - p.yyPos));
  }

  public double calcForceExertedBy(Planet p) {
    return G * mass * p.mass / (calcDistance(p) * calcDistance(p));
  }

  public double calcForceExertedByX(Planet p) {
    return calcForceExertedBy(p) * (p.xxPos - xxPos) / calcDistance(p);
  }

  public double calcForceExertedByY(Planet p) {
    return calcForceExertedBy(p) * (p.yyPos - yyPos) / calcDistance(p);
  }

  public double calcNetForceExertedByX(Planet[] ap) {
    double netF = 0.0d;

    for (int i = 0; i < ap.length; i++) {
      if (!this.equals(ap[i])) {
        netF += calcForceExertedByX(ap[i]);
      }
    }
    return netF;
  }

  public double calcNetForceExertedByY(Planet[] ap) {
    double netF = 0.0d;

    for (int i = 0; i < ap.length; i++) {
      if (!this.equals(ap[i])) {
        netF += calcForceExertedByY(ap[i]);
      }
    }
    return netF;
  }

  public void update(double dt, double fX, double fY) {
    double ax = fX / mass;
    double ay = fY / mass;

    xxVel = xxVel + dt * ax;
    yyVel = yyVel + dt * ay;
    xxPos = xxPos + dt * xxVel;
    yyPos = yyPos + dt * yyVel;
  }

  public void draw() {
    StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
  }
}
