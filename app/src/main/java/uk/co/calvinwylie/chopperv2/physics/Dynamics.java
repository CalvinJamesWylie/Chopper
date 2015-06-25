package uk.co.calvinwylie.chopperv2.physics;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;

/**
 * Created by Calvin on 08/06/2015.
 */
public class Dynamics {

    private static Vector3 tempVector = new Vector3();

    public static void calcAirResistance(Vector3 vel, float airResistanceConst) {

        float speed = vel.length();

        tempVector.X = airResistanceConst * vel.X * speed;
        tempVector.Y = airResistanceConst * vel.Y * speed;
        tempVector.Z = airResistanceConst * vel.Z * speed;

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
