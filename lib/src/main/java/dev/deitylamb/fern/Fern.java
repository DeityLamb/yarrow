package dev.deitylamb.fern;

import java.util.Arrays;
import java.util.List;

import dev.deitylamb.fern.flows.CompositeFlow;
import dev.deitylamb.fern.flows.FocusFlow;
import dev.deitylamb.fern.transitions.Transition;
import dev.deitylamb.fern.transitions.Transitionable;
import dev.deitylamb.fern.tweens.Tween;
import dev.deitylamb.fern.tweens.TweenBuilder.TweenFactory;

public class Fern {

    public static <T> Transitionable<T> transition(float duration, Tween<T> tween) {
        return new Transition<>(duration, tween);
    }

    public static <T> Transitionable<T> transition(float duration) {
        return new Transition<>(duration, Tween.empty());
    }

    public static <T> Transitionable<T> transition(float duration, TweenFactory<T> tween) {
        return transition(duration, tween.create());
    }

    public static <T> FocusFlow<T> focus(Transitionable<T> hover, Transitionable<T> blur) {
        return new FocusFlow<>(hover, blur);
    }

    public static <T> FocusFlow<T> focus(Transitionable<T> transition) {
        return focus(transition, transition.reverse());
    }

    public static <T> FocusFlow<T> focus(float duration, Tween<T> tween) {
        Transitionable<T> transition = transition(duration, tween);
        return focus(transition, transition.reverse());
    }

    public static <T> FocusFlow<T> focus(float duration, TweenFactory<T> tween) {
        Transitionable<T> transition = transition(duration, tween);
        return focus(transition, transition.reverse());
    }

    public static <T> CompositeFlow<T> composite(
            Flowable<T> one,
            Flowable<T> two) {
        return new CompositeFlow<T>(Arrays.asList(one, two));
    }

    public static <T> CompositeFlow<T> composite(
            Flowable<T> one,
            Flowable<T> two,
            Flowable<T> three) {
        return new CompositeFlow<T>(Arrays.asList(one, two, three));
    }

    public static <T> CompositeFlow<T> composite(
            Flowable<T> one,
            Flowable<T> two,
            Flowable<T> three,
            Flowable<T> four) {
        return new CompositeFlow<T>(Arrays.asList(one, two, three, four));
    }

    public static <T> CompositeFlow<T> composite(
            Flowable<T> one,
            Flowable<T> two,
            Flowable<T> three,
            Flowable<T> four,
            Flowable<T> five) {
        return new CompositeFlow<T>(Arrays.asList(one, two, three, four, five));
    }

    public static <T> CompositeFlow<T> composite(List<Flowable<T>> transitions) {
        return new CompositeFlow<T>(transitions);
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(val, max));
    }

    public static float lerp(float alpha, float start, float end) {
        return (1 - alpha) * start + alpha * end;
    }

    public static int lerpColor(float alpha, int from, int to) {
        int aA = (from >> 24) & 0xFF;
        int rA = (from >> 16) & 0xFF;
        int gA = (from >> 8) & 0xFF;
        int bA = from & 0xFF;

        int aB = (to >> 24) & 0xFF;
        int rB = (to >> 16) & 0xFF;
        int gB = (to >> 8) & 0xFF;
        int bB = to & 0xFF;

        int a = (int) ((aB - aA) * alpha + aA);
        int r = (int) ((rB - rA) * alpha + rA);
        int g = (int) ((gB - gA) * alpha + gA);
        int b = (int) ((bB - bA) * alpha + bA);

        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static String lerpHexColor(float alpha, String from, String to) {
        return colorToHex(lerpColor(alpha, hexToColor(from), hexToColor(to)));
    }

    public static String colorToHex(int color) {
        return String.format("#%08X", color);
    }

    public static int hexToColor(String hex) {
        return (int) Long.parseLong(hex.replace("#", ""), 16);
    }

}
