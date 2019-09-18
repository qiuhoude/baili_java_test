package com.houde.eventbus;

/**
 * Created by I on 2017/7/26.
 */
public class EventTest {

    public static void main(String[] args) {
        WjEventBus.getInit().subscribe("1", 9,String.class, new EventLister() {
            @Override
            public void postResult(Object eventVaule) {
                System.out.println("优先级是9-----------");

            }
        });
        WjEventBus.getInit().post("0", "425");
        WjEventBus.getInit().post("1", "425");
        WjEventBus.getInit().subscribe("0", 2, String.class, new EventLister() {
            @Override
            public void postResult(Object eventVaule) {
                System.out.println("优先级是2-----------");

            }
        });
        WjEventBus.getInit().subscribe("0", 1, String.class, new EventLister() {
            @Override
            public void postResult(Object eventVaule) {
                System.out.println("优先级是1-----------");

            }
        });
        WjEventBus.getInit().subscribe("0", 0, String.class, new EventLister() {
            @Override
            public void postResult(Object eventVaule) {
                System.out.println("优先级是0-----------");

            }
        });
        WjEventBus.getInit().subscribeNext("0", 4, String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println("--------4粘性动画" + eventVaule);
            }
        });
        WjEventBus.getInit().subscribeNext("0", 5, String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println("--------5粘性动画" + eventVaule);
                WjEventBus.getInit().subscribeNext("0", 4, String.class, new EventLister() {
                    @Override
                    public void postResult(final Object eventVaule) {
                        System.out.println("--------4粘性动画" + eventVaule);
                    }
                });
            }
        });
        WjEventBus.getInit().subscribeNext("0", 3, String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println("--------3粘性动画" + eventVaule);
            }
        });
        WjEventBus.getInit().subscribeNext("0", 4, String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println("--------4粘性动画" + eventVaule);
            }
        });
        WjEventBus.getInit().post("0", "425");

        WjEventBus.getInit().subscribe("1", String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println(eventVaule + "----------接收");

            }
        });
        WjEventBus.getInit().subscribe("2", String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println(eventVaule + "----------接收");
            }
        });
        WjEventBus.getInit().subscribe("2", 2, String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println(eventVaule + "----------优先级2接收");
            }
        });
        WjEventBus.getInit().subscribe("3", String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println(eventVaule + "----------接收");
            }
        });

        WjEventBus.getInit().remove("1");
        WjEventBus.getInit().remove("2", 2);
    }
}
