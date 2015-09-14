package uk.co.calvinwylie.chopperv2.physics;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;


public class Dynamics {

    private static Vector3 tempVector = new Vector3();

    public static void calcAirResistance(Vector3 vel, float airResistanceConst) {

        tempVector.X = -airResistanceConst * vel.X;
        tempVector.Y = -airResistanceConst * vel.Y;
        tempVector.Z = -airResistanceConst * vel.Z;

        vel.add(tempVector);

    }
}
