package dev.deitylamb.yarrow.common;

import java.util.function.Function;

public class Easings {

    @FunctionalInterface
    public static interface Ease extends Function<Double, Double> {
    }

    public static Ease blend(Ease blend, Ease with) {
        return (alpha) -> with.apply(blend.apply(alpha));
    }

    public static Double linear(Double t) {
        return t;
    };

    public static Double easeInQuad(Double t) {
        return t * t;
    };

    public static Double easeOutQuad(Double t) {
        return 1 - (1 - t) * (1 - t);
    };

    public static Double easeInOutQuad(Double t) {
        return t < 0.5d ? 2 * t * t : -1 + (4 - 2 * t) * t;
    };

    public static Double easeInCubic(Double t) {
        return t * t * t;
    }

    public static Double easeOutCubic(Double t) {
        return 1 - Math.pow(1 - t, 3);
    }

    public static Double easeInOutCubic(Double t) {
        return t < 0.5d
                ? 4 * t * t * t
                : 1 - Math.pow(-2 * t + 2, 3) / 2;
    }

    public static Ease springEase(
            double mass, double stiffness, double damping) {
        return springEase(mass, stiffness, damping, 0);
    }

    public static Ease springEase(
            double mass, double stiffness, double damping, double initialVelocity) {

        double m = mass;
        double k = stiffness;
        double c = damping;
        double v0 = initialVelocity;

        double omega0 = Math.sqrt(k / m);
        double zeta = c / (2 * Math.sqrt(k * m));

        if (zeta < 1) {
            double omegaD = omega0 * Math.sqrt(1 - zeta * zeta);
            return t -> {
                double exp = Math.exp(-zeta * omega0 * t);
                return 1 - exp * ((Math.cos(omegaD * t)) +
                        ((zeta * omega0 + v0) / omegaD) * Math.sin(omegaD * t));
            };
        } else if (zeta == 1) {
            return t -> {
                double exp = Math.exp(-omega0 * t);
                return 1 - exp * (1 + (omega0 + v0) * t);
            };
        } else {
            double omegaD = omega0 * Math.sqrt(zeta * zeta - 1);
            return t -> {
                double exp1 = Math.exp((-zeta * omega0 + omegaD) * t);
                double exp2 = Math.exp((-zeta * omega0 - omegaD) * t);
                return 1 - (exp1 - exp2) / (2 * omegaD);
            };
        }
    }

}
