# delay
```java
@Override
    public void seek(double duration) {

        double modulo = FernUtils.modulo(duration, this.duration());

        if (modulo > delay) {
            inner.seek(modulo - delay);
            this.elapsed = delay;
            return;
        }

        this.elapsed = modulo;
    }
```

# repeat
```java
@Override
    public void seek(double duration) {

        double modulo = FernUtils.modulo(duration, this.duration());

        this.repeats = (int) Math.floor(modulo / inner.duration());

        inner.seek(modulo % inner.duration());
    }
```

# sequence

```java
    @Override
    public void seek(double duration) {

        boolean run = isRunning();

        this.reset();
        this.pause();

        double capacity = FernUtils.modulo(duration, this.duration());

        for (Transitionable<T> transition : edges) {

            if (capacity >= transition.duration()) {
                capacity -= transition.duration();
                continue;
            }

            transition.seek(capacity);
            active = Optional.of(transition);
            if (run) {
                transition.play();
            }
            return;

        }

    }
```java
    @Override
    public void seek(double duration) {
        this.elapsed = FernUtils.modulo(duration, this.duration);

        if (alpha() == 1d) {
            this.pause();
        }
    }
```