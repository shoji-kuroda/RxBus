# RxBus（ottoからの移行用）

## PUBLISHING
### otto
```
bus.post(new AnswerAvailableEvent(42));
```

### RxBus
```
rxBus.post(new AnswerAvailableEvent(42));
```

## SUBSCRIBING
### otto
```
@Override public void onResume() {
    bus.register(this);
}

@Subscribe public void answerAvailable(AnswerAvailableEvent event) {
    // TODO: React to the event somehow!
}

```

### RxBus
```
@Override public void onResume() {
    rxBus.register(this);
}

@Subscribe public void answerAvailable(AnswerAvailableEvent event) {
    // TODO: React to the event somehow!
}
```



