package uk.co.calvinwylie.chopperv2.physics;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;


public class Dynamics {

    private static Vector3 tempVector = new Vector3();

    public static void calcAirResistance(Vector3 vel, float airResistanceConst, float deltaTime) {

        float speed = vel.length();

        tempVector.X = -airResistanceConst * vel.X * speed * deltaTime;
        tempVector.Y = -airResistanceConst * vel.Y * speed * deltaTime;
        tempVector.Z = -airResistanceConst * vel.Z * speed * deltaTime;

        vel.add(tempVector);

//        if (vel.isZero()){
//            return vel;
//        }
//
//        return new Vector3(
//                (vel.X * vel.X) * Constants.AIR_RESISTANCE,
//                (vel.Y * vel.Y) * Constants.AIR_RESISTANCE,
//                (vel.Z * vel.Z) * Constants.AIR_RESISTANCE
//        );
    }
}
