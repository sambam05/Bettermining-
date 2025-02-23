package com.sheath.utility.math;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class Vec3dToVec3i {
    public static Vec3i convert(Vec3d vec3d) {
        return new Vec3i((int) Math.floor(vec3d.x), (int) Math.floor(vec3d.y), (int) Math.floor(vec3d.z));
    }
}
