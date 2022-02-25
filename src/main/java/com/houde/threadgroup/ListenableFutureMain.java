package com.houde.threadgroup;

import com.google.common.util.concurrent.*;
import org.apache.zookeeper.data.Id;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author qiukun
 * @date 2022/1/12
 */
public class ListenableFutureMain {


    static void baseTest() {
        ExecutorService execService = Executors.newSingleThreadExecutor();
        ListeningExecutorService lExecService = MoreExecutors.listeningDecorator(execService);

        ListenableFuture<Integer> asyncTask = lExecService.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(500); // long running task
            return 5;
        });


        Futures.addCallback(asyncTask, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(@Nullable Integer result) {
                System.out.println("onSuccess result:" + result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("onFailure:" + t);

            }
        }, lExecService);
    }

    static ListenableFutureTask<String> fetchConfigListenableTask(String configKey) {
        return ListenableFutureTask.create(() -> {
            TimeUnit.MILLISECONDS.sleep(30);
            if (configKey == null) {
                throw new NullPointerException("configKey is null");
            }
            return String.format("%s.%d", configKey, new Random().nextInt(Integer.MAX_VALUE));
        });

    }

    static void fanInTest() {
        ListeningExecutorService lExecService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        ListenableFutureTask<String> task1 = fetchConfigListenableTask("test.1_");
        ListenableFutureTask<String> task2 = fetchConfigListenableTask("test.2_");
        ListenableFutureTask<String> task3 = fetchConfigListenableTask(null);
        lExecService.submit(task1);
        lExecService.submit(task2);
        lExecService.submit(task3);


//        ListenableFuture<List<String>> allTask = Futures.allAsList(task1, task2, task3);
        ListenableFuture<List<String>> allTask = Futures.successfulAsList(task1, task2, task3); // 不管是否失败都会整合

//        Futures.whenAllSucceed()
        Futures.addCallback(allTask, new FutureCallback<List<String>>() {
            @Override
            public void onSuccess(@Nullable List<String> result) {
                for (String s : result) {
                    System.out.println(s);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t);
            }
        }, lExecService);
    }


    //
    static class MServerInfo {
        List<Integer> composeId = new ArrayList<>();
        int mId;
    }


    static class Player {
        int id;
        String name;
        String data;

        @Override
        public String toString() {
            return "Player{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", data='" + data + '\'' +
                    '}';
        }
    }

    static class MData {
        int mId;
        Map<Integer, Map<Integer, Player>> playerData = new HashMap<>();
    }

    static class Pair<A, B> {
        A a;
        B b;

        public Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }
    }


    static ListenableFuture<List<Pair<Integer, String>>> queryName(ListeningExecutorService executor, int subServerId) {
        return executor.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(1000);
            List<Pair<Integer, String>> res = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                res.add(new Pair<>(i, "name_" + i + "_subServerId:" + subServerId));
            }
            return res;
        });
    }

    static ListenableFuture<List<Integer>> queryId(ListeningExecutorService executor) {
        return executor.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(1000);
            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                res.add(i);
            }
            return res;
        });
    }

    static ListenableFuture<List<Pair<Integer, String>>> queryData(ListeningExecutorService executor, int subServerId) {
        return executor.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(1000);
//            throw new RuntimeException("queryData 查询失败 serverId:" + subServerId);
            List<Pair<Integer, String>> res = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                res.add(new Pair<>(i, "byte:roleId:" + i + ", subServerId" + subServerId));
            }
            return res;

        });
    }

    static ListenableFuture<Pair<Integer, Map<Integer, Player>>> queryPlayer(ListeningExecutorService executor, int subServerId) {
        ListenableFuture<List<Integer>> taskId = queryId(executor);
        ListenableFuture<List<Pair<Integer, String>>> taskName = queryName(executor, subServerId);
        ListenableFuture<List<Pair<Integer, String>>> taskData = queryData(executor, subServerId);

        ListenableFuture<Pair<Integer, Map<Integer, Player>>> future = Futures.whenAllSucceed(taskId, taskName, taskData).call(() -> {
            Map<Integer, Player> m = new HashMap<>();
            List<Integer> idList = Futures.getDone(taskId);
            for (Integer id : idList) {
                Player player = new Player();
                player.id = id;
                m.put(id, player);
            }
            List<Pair<Integer, String>> nameList = Futures.getDone(taskName);
            for (Pair<Integer, String> e : nameList) {
                Player player = m.get(e.a);
                if (player != null) {
                    player.name = e.b;
                }
            }
            List<Pair<Integer, String>> dataList = Futures.getDone(taskData);
            for (Pair<Integer, String> e : dataList) {
                Player player = m.get(e.a);
                if (player != null) {
                    player.data = e.b;
                }
            }

            return new Pair<>(subServerId, m);
        }, executor);
        return future;
    }

    static ListenableFuture<Integer> saveId(ListeningExecutorService executor, List<Integer> ids, int mId) {
        return executor.submit(() -> {
            System.out.println(mId + "服 savePlayerId size:" + ids.size());
            for (Integer id : ids) {
                System.out.println("id:" + id);
            }
            System.out.println("==============");
            TimeUnit.MILLISECONDS.sleep(1000);
            return ids.size();
        });
    }

    static ListenableFuture<Integer> saveName(ListeningExecutorService executor, List<String> names, int mId) {
        return executor.submit(() -> {
            System.out.println(mId + "服 savePlayerName size:" + names.size());
            for (String e : names) {
                System.out.println("name:" + e);
            }
            System.out.println("==============");
            TimeUnit.MILLISECONDS.sleep(1000);
            return names.size();
        });
    }

    static ListenableFuture<Integer> saveData(ListeningExecutorService executor, List<String> data, int mId) {
        return executor.submit(() -> {
            System.out.println(mId + "服 savePlayerData size:" + data.size());
            for (String e : data) {
                System.out.println("data:" + e);
            }
            System.out.println("==============");
            TimeUnit.MILLISECONDS.sleep(1000);
            return data.size();
        });
    }

    static ListenableFuture<String> saveAll(ListeningExecutorService executor, List<Player> allPlayer, int mId) {

        List<Integer> ids = allPlayer.stream().map(p -> p.id).collect(Collectors.toList());
        List<String> names = allPlayer.stream().map(p -> p.name).collect(Collectors.toList());
        List<String> data = allPlayer.stream().map(p -> p.data).collect(Collectors.toList());

        ListenableFuture<Integer> taskId = saveId(executor, ids, mId);
        ListenableFuture<Integer> taskName = saveName(executor, names, mId);
        ListenableFuture<Integer> taskData = saveData(executor, data, mId);
        return Futures.whenAllSucceed(taskId, taskName, taskData).call(() -> {
            StringBuilder sb = new StringBuilder();
            sb.append(Futures.getDone(taskId) + "条 id保存\n");
            sb.append(Futures.getDone(taskName) + "条 name保存\n");
            sb.append(Futures.getDone(taskData) + "条 data保存\n");
            return sb.toString();
        }, executor);


    }

    static ListenableFuture<String> masterTask(ListeningExecutorService executor, MServerInfo mServerInfo) {
        List<ListenableFuture<Pair<Integer, Map<Integer, Player>>>> listFuture = new ArrayList<>();
        for (Integer subServerId : mServerInfo.composeId) {
            listFuture.add(queryPlayer(executor, subServerId));
        }
        ListenableFuture<MData> mDataFuture = Futures.whenAllComplete(listFuture).call(() -> {
            MData mData = new MData();
            mData.mId = mServerInfo.mId;
            for (ListenableFuture<Pair<Integer, Map<Integer, Player>>> future : listFuture) {
                Pair<Integer, Map<Integer, Player>> pair1 = Futures.getDone(future);
                mData.playerData.put(pair1.a, pair1.b);
            }
            // 处理数据
            TimeUnit.MILLISECONDS.sleep(1000);
            return mData;
        }, executor);
        // 保存数据
        AsyncFunction<MData, String> tFunc = (mData) -> {
            List<Player> allPlayer = mData.playerData.values().stream().flatMap(m -> m.values().stream()).collect(Collectors.toList());
            return saveAll(executor, allPlayer, mServerInfo.mId);
        };

        return Futures.transformAsync(mDataFuture, tFunc, executor);
    }

    static final int cpuNum = Runtime.getRuntime().availableProcessors();

    static void demoMerge() {

        ListeningExecutorService lExecService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(cpuNum));
        MServerInfo m101 = new MServerInfo();
        m101.mId = 101;
        m101.composeId.add(101);
        m101.composeId.add(102);
        m101.composeId.add(103);

        ListenableFuture<String> futureM101 = masterTask(lExecService, m101);

        Futures.addCallback(futureM101, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                System.out.println("成功:\n" + result);

            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("失败:" + t);
            }
        }, lExecService);

    }

    public static void main(String[] args) throws IOException {

//        baseTest();
//        fanInTest();
        demoMerge();
//        ClosingFuture.submit().finishToFuture();
//        FluentFuture.from(futureM101);

        System.in.read();

    }
}
