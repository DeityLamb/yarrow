package dev.deitylamb.fern.hooks;

public interface HookSubscriber<T> {

  default void onPlay() {
  }

  default void onPause() {
  }

  default void onTick(T graphics, double delta) {
  }

  default void onTick(double delta) {
  }

  default void onProgress(T graphics, double progress) {
  }

  default void onProgress(double progress) {
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
  interface OnTick<T> {
    void onTick(T graphics, double delta);

    default HookSubscriber<T> asHookable() {
      return new HookSubscriber<T>() {
        @Override
        public void onTick(T graphics, double delta) {
          OnTick.this.onTick(graphics, delta);
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
    void onProgress(T graphics, double progress);

    default HookSubscriber<T> asHookable() {
      return new HookSubscriber<T>() {
        @Override
        public void onProgress(T graphics, double progress) {
          OnProgress.this.onProgress(graphics, progress);
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

}
