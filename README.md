# README

JavaのThread使用時の挙動確認

## 基本
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

## ExecutorServiceを利用する方法
### メインスレッド側
- `Future<Generics> future`にスレッドからの戻り値の型を指定
- `future.get()`にタイムアウト時間を指定可能
```
ThreadFactory daemon = new ThreadFactory() {
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        // when main thread exit, also daemon thread exit
        t.setDaemon(true);
        return t;
    }
};
ExecutorService es = Executors.newSingleThreadExecutor(daemon);
try {
    Future<String> future = es.submit(new MyThread());
    try {
        String result = future.get(6, TimeUnit.SECONDS);
        System.out.println(result);
    } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
    } catch (TimeoutException te) {
        System.out.println("timeout!");
    }
} finally {
    es.shutdown();
}
```

### スレッド側
- Callableを実装、call()をオーバーライドしてやりたい処理を実装  
- ジェネリクスに戻り値の方を指定
```
public class MyThread implements Callable<String> {
    public String call () {
        try {
        #
        #
        #
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "return value";
    }
}
```