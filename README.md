# Обробка потокової інформації

## Set up env:

1. Start ZooKeeper `bin/zookeeper-server-start.sh config/zookeeper.properties`
2. Start Kafka `bin/kafka-server-start.sh config/server.properties`
3. Create topic `bin/kafka-topics.sh --create --topic my-topic --bootstrap-server localhost:9092`
4. Read events `bin/kafka-console-consumer.sh --topic my-topic --from-beginning --bootstrap-server localhost:9092`
5. Run producer/consumer (If you use WSL to run the broker, you should also run producer/consumer in WSL due to some problem with interaction)
6. Stop Kafka broker
7. Stop ZooKeeper
8. Delete data of local env `rm -rf /tmp/kafka-logs /tmp/zookeeper`

## Лабораторна робота №1

Написати код продюсера, що імітує потік даних, який генерується в певній предметній області
(наприклад, показники масиву сенсорів, дані про угоди купівлі/продажу валюти або цінних паперів, логи тощо),
та надсилає її до певного топіку Кафка.

## Лабораторна робота №2

Написати код конс’юмера, що отримує потік даних, який генерується продюсером,
та здійснює вибірку кортежів потоку для 1/10 всіх значень певного ключа.

## Лабораторна робота №3

Написати програмну реалізацію алгоритму резервуарної вибірки (Reservoir Sampling).
Продемонструвати її роботу на прикладі потоку даних, що генерується продюсером,
створеним в рамках Лабораторної роботи №1.

## Лабораторна робота №4

Згенерувати 100 нескінчених потоків випадкових 0 та 1.
Написати програму, що в кожен момент часу визначає потік з найбільшою кількістю нещодавніх одиниць,
використовуючи зважену суму (кількість одиниць у затухаючому вікні).
Зберігати в пам’яті зважену суму потоку тільки в тому випадку, якщо вона перевищує певне порогове значення (1/2).
