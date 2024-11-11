package com.example.demo.elements;

import java.util.Random;

public class Gauss {
    private double min;
    private double max;
    private double stdDev;
    private double mean;

    public Gauss(double min, double max, double stdDev) {
        this.min = min;
        this.max = max;
        this.stdDev = stdDev;
        this.mean = (max + min) / 2;
    }

    public int gaussianRandom() {
        Random random = new Random();
        double gaussianValue = random.nextGaussian();  // Valor generado con distribución gaussiana
        double normalizedValue = mean + gaussianValue * stdDev;

        // Clampea el valor dentro del rango [min, max]
        if (normalizedValue < min ||normalizedValue > max) {
            normalizedValue = gaussianRandom();
        }

        return (int) Math.round(normalizedValue);
    }
}