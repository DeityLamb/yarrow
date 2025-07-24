package dev.deitylamb.fern.hooks;

public interface HookSubscriber<T> {

  default void onPlay() {
  }

  default void onPause() {
  }

  default void onReset() {
  }

  default void onTick(T gui, double delta) {
  }

  default void onTick(double delta) {
  }

  default void onProgress(T gui, double progress) {
  }

  default void onProgress(double progress) {
  }

  default void onClear(T gui) {
  }

  default void onClear() {
  }

  @FunctionalInterface
  interface OnPlay {
    void onPlay();

    default <T> HookSubscriber<T> asHookable() {
      return new HookSubscriber<T>() {
        @Override
        public void onPlay() {
          OnPlay.this.onPlay();
        }
      };
    }
  }

  @FunctionalInterface
  interface OnPause {
    void onPause();

    default <T> HookSubscriber<T> asHookable() {
      return new HookSubscriber<T>() {
        @Override
        public void onPause() {
          OnPause.this.onPause();
        }
      };
    }
  }

  @FunctionalInterface
  interface OnReset {
    void onReset();

    default <T> HookSubscriber<T> asHookable() {
      return new HookSubscriber<T>() {
        @Override
        public void onReset() {
          OnReset.this.onReset();
        }
      };
    }
  }

  @FunctionalInterface
  interface OnTick<T> {
    void onTick(T gui, double delta);

    default HookSubscriber<T> asHookable() {
      return new HookSubscriber<T>() {
        @Override
        public void onTick(T gui, double delta) {
          OnTick.this.onTick(gui, delta);
        }
      };
    }
  }

  @FunctionalInterface
  interface OnTickNoGui {
    void onTick(double delta);

    default <T> HookSubscriber<T> asHookable() {
      return new HookSubscriber<T>() {
        @Override
        public void onTick(double delta) {
          OnTickNoGui.this.onTick(delta);
        }
      };
    }
  }

  @FunctionalInterface
  interface OnProgress<T> {
    void onProgress(T gui, double progress);

    default HookSubscriber<T> asHookable() {
      return new HookSubscriber<T>() {
        @Override
        public void onProgress(T gui, double progress) {
          OnProgress.this.onProgress(gui, progress);
        }
      };
    }
  }

  @FunctionalInterface
  interface OnProgressNoGui {
    void onProgress(double progress);

    default <T> HookSubscriber<T> asHookable() {
      return new HookSubscriber<T>() {
        @Override
        public void onProgress(double progress) {
          OnProgressNoGui.this.onProgress(progress);
        }
      };
    }
  }

  @FunctionalInterface
  interface OnClear<T> {
    void onClear(T gui);

    default HookSubscriber<T> asHookable() {
      return new HookSubscriber<T>() {
        @Override
        public void onClear(T gui) {
          OnClear.this.onClear(gui);
        }
      };
    }
  }

  @FunctionalInterface
  interface OnClearNoGui {
    void onClear();

    default <T> HookSubscriber<T> asHookable() {
      return new HookSubscriber<T>() {
        @Override
        public void onClear() {
          OnClearNoGui.this.onClear();
        }
      };
    }
  }
}
