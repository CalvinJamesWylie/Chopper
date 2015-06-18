package uk.co.calvinwylie.chopperv2.gameObjects;


import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;

/**
 * Created by Calvin on 25/02/2015.
 */
public class Geometry{

    public static class Circle{
        public final Vector3 center;
        public final float radius;

        public Circle(Vector3 center, float radius){
            this.center = center;
            this.radius = radius;
        }

        public Circle scale(float scale){
            return new Circle(center, radius*scale);
        }
    }

    public static class Cylinder{
        public Vector3 center;
        public final float radius;
        public final  float height;

        public Cylinder(Vector3 center, float radius, float height){
            this.center = center;
            this.radius = radius;
            this.height = height;
        }
    }

    public static class Ray {
        public final Vector3 startPoint;
        public final Vector3 directionVector;

        public Ray(Vector3 point, Vector3 vector){
            this.startPoint = point;
            this.directionVector = vector;
        }
    }

    public static class Sphere{
        public final Vector3  center;
        public final float radius;

        public Sphere(Vector3 center, float radius){
            this.center = center;
            this.radius = radius;
        }
    }

    public static class Plane {
        public final Vector3 point;
        public final Vector3 normal;

        public Plane (Vector3 point, Vector3 normal){
            this.point = point;
            this.normal = normal;
        }
    }


    public static boolean intersects(Sphere sphere, Ray ray){
        return distanceBetween(sphere.center, ray) < sphere.radius;
    }

    public static float distanceBetween(Vector3 point, Ray ray){
        Vector3 p1ToPoint = Vector3.vectorBetween(ray.startPoint, point);
        Vector3 p2ToPoint = Vector3.vectorBetween(ray.startPoint.translate(ray.directionVector), point);

        float areaOfTriangleTimesTwo = p1ToPoint.crossProduct(p2ToPoint).length();
        float lengthOfBase = ray.directionVector.length();

        float distanceFromPointToRay = areaOfTriangleTimesTwo/lengthOfBase;
        return distanceFromPointToRay;

    }

    public static Vector3 intersectionPoint(Ray ray, Plane plane){
        Vector3 rayToPlaneVector = Vector3.vectorBetween(ray.startPoint, plane.point);

        float scaleFactor = rayToPlaneVector.dotProduct(plane.normal) / ray.directionVector.dotProduct(plane.normal);
        Vector3 intersectionPoint = ray.startPoint.translate(ray.directionVector.scaled(scaleFactor));
        return intersectionPoint;
    }
}
