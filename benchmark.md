# Benchmark

## condtion

*   num(key1) = 10000
*   num(key2) = 100

## mapmap

*   1回のクエリ= key1 固定で key2 を 10個まとめての問い合わせ

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

*   a key = 16 bytes: key1 = 8 bytes + key2 = 8 bytes
*   1回のクエリ= key1 固定で key2 を変えつつ10回のSparkeyへ問い合わせ

> rate1 = 1.0; rate2 = 0.2; nKey2 = 10;

```
Avg. time: 1871 nanosec
Hit rate (query): 0.892583 (1785166/2000000)
Hit rate (result): 0.223979 (3998395/17851660)
```

> rate1 = 1.0; rate2 = 1.0; nKey2 = 10;

```
Avg. time: 2675 nanosec
Hit rate (query): 1.000000 (2000000/2000000)
Hit rate (result): 1.000000 (20000000/20000000)
```

> rate1 = 0.2; rate2 = 1.0; nKey2 = 10;

```
Avg. time: 1844 nanosec
Hit rate (query): 0.200213 (400425/2000000)
Hit rate (result): 1.000000 (4004250/4004250)
```

> rate1 = 0.2; rate2 = 0.2; nKey2 = 10;

```
Avg. time: 1593 nanosec
Hit rate (query): 0.178613 (357225/2000000)
Hit rate (result): 0.224033 (800303/3572250)
```
