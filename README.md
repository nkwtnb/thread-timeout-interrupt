# README

JavaのThread使用時の挙動確認

- 以下2種類のスレッド属性がある
  - `ユーザースレッド`
    - メインスレッドが終了しても、自分自身の処理が終了するまで終わらない  
    デフォルトでは、作成するスレッドはユーザースレッドとなる
  - `デーモンスレッド`
      - メインスレッドが終了したら、自分自身の処理が途中でも強制終了する
- スレッド属性は`thread.setDaemon(bool)`で指定する
- スレッドの停止は`thread.stop()`ではなくなった（=非推奨となった）
  - `while()` で繰り返し処理させるようなスレッドの場合は、以下のように実行中フラグを管理し、メインスレッド側から`stopThread()`を呼び出して終了させる
    ```
    private AtomicBoolean running = new AtomicBoolean(false);
    #
    #
    #
    public void run() {
        while(running.get()) {
            running.set(true);
            #
            #
            #
        }
    }
    public void stopThread() {
        running.set(false)
    }
    ```
  - 繰り返し処理しない場合は、`thread.interrupt()`で割り込み中断させる
