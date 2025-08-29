package dev.deitylamb.fern.hooks;

import dev.deitylamb.fern.Flow;

public interface HookSubscriber<T> {

  default void onPlay() {
  }

  default void onPlay(Flow<T> flow) {
  }

  default void onPause() {
  }

  default void onPause(Flow<T> flow) {
  }

  default void onReset() {
  }

  default void onReset(Flow<T> flow) {
  }

  default void onStop() {
  }

  default void onRestart() {
  }

  default void onComplete(T graphics, Flow<T> flow) {
  }

  default void onComplete(Flow<T> flow) {
  }

  default void onComplete() {
  }

  default void onTick(T graphics, Flow<T> flow) {
  }

  default void onTick(Flow<T> flow) {
  }

  default void onTick() {
  }

  default void onProgress(T graphics, Flow<T> flow) {
  }

  default void onProgress(Flow<T> flow) {
  }

  default void onProgress() {
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
    void onTick(T graphics, Flow<T> flow);

    default HookSubscriber<T> asHookable() {
      return new HookSubscriber<T>() {
        @Override
        public void onTick(T graphics, Flow<T> flow) {
          OnTick.this.onTick(graphics, flow);
        }
      };
    }
  }

  @FunctionalInterface
  interface OnTickNoGui<T> {
    void onTick(Flow<T> flow);

    default HookSubscriber<T> asHookable() {
      return new HookSubscriber<T>() {
        @Override
        public void onTick(Flow<T> flow) {
          OnTickNoGui.this.onTick(flow);
        }
      };
    }
  }

  @FunctionalInterface
  interface OnProgress<T> {
    void onProgress(T graphics, Flow<T> flow);

    default HookSubscriber<T> asHookable() {
      return new HookSubscriber<T>() {
        @Override
        public void onProgress(T graphics, Flow<T> flow) {
          OnProgress.this.onProgress(graphics, flow);
        }
      };
    }
  }

  @FunctionalInterface
  interface OnProgressNoGui<T> {
    void onProgress(Flow<T> flow);

    default HookSubscriber<T> asHookable() {
      return new HookSubscriber<T>() {
        @Override
        public void onProgress(Flow<T> flow) {
          OnProgressNoGui.this.onProgress(flow);
        }
      };
    }
  }

}
