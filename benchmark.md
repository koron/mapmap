# Benchmark

## condtion

*   num(key1) = 10000
*   num(key2) = 100

## mapmap

> rate1 = 1.0; rate2 = 0.2; nKey2 = 10;

```
Avg. time: 915 nanosec
Hit rate (query): 1.000000 (2000000/2000000)
Hit rate (result): 0.199885 (3997704/20000000)
```

> rate1 = 1.0; rate2 = 1.0; nKey2 = 10;

```
Avg. time: 1426 nanosec
Hit rate (query): 1.000000 (2000000/2000000)
Hit rate (result): 1.000000 (20000000/20000000)
```

> rate1 = 0.2; rate2 = 1.0; nKey2 = 10;

```
Avg. time: 393 nanosec
Hit rate (query): 0.200170 (400339/2000000)
Hit rate (result): 1.000000 (4003390/4003390)
```

> rate1 = 0.2; rate2 = 0.2; nKey2 = 10;

```
Avg. time: 272 nanosec
Hit rate (query): 0.199941 (399882/2000000)
Hit rate (result): 0.199929 (799482/3998820)
```

## sparkey

> rate1 = 1.0; rate2 = 0.2; nKey2 = 10;

```
Avg. time: 2057 nanosec
Hit rate (query): 0.892838 (1785676/2000000)
Hit rate (result): 0.200011 (4000228/20000000)
```

> rate1 = 1.0; rate2 = 1.0; nKey2 = 10;

```
Avg. time: 2764 nanosec
Hit rate (query): 1.000000 (2000000/2000000)
Hit rate (result): 1.000000 (20000000/20000000)
```

> rate1 = 0.2; rate2 = 1.0; nKey2 = 10;

```
Avg. time: 1975 nanosec
Hit rate (query): 0.199320 (398639/2000000)
Hit rate (result): 0.199320 (3986390/20000000)
```

> rate1 = 0.2; rate2 = 0.2; nKey2 = 10;

```
```
